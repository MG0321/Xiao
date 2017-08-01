import {Component, OnInit, Inject} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
//ace的固定用法，要把ace声明成一个全局变量，从而和安装的ace-builds联动
declare var ace: any;

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {
  editor: any;
  public languages: string[] = ['Java', 'C++', 'Python'];
  language: string = 'Java';//default
  sessionId: string;
  output: string;
  defaultContent = {
    'Java': `public class Example {
      public static void main(String[] args) {
          // Type your Java code here
      }
}`,
    'C++': `#include <iostream>
    using namespace std;
    ​
    int main() {
       // Type your C++ code here
       return 0;
}`,
    'Python': `class Solution:
        def example():
            # Write your Python code here`
  };
//在provide中命名的是collaboration【用名称来match的annotation】
  constructor(@Inject('collaboration') private collaboration, @Inject('data') private data, private route: ActivatedRoute) {
  }

  ngOnInit() {
    //是通过url上的id号知道现在的问题号的，要把题号发给服务端
    this.route.params.subscribe(params => {
      this.sessionId = params['id'];
      this.initEditor();
    });
  }

  initEditor() {
    this.editor = ace.edit('editor');
    this.editor.setTheme('ace/theme/eclipse');
    //启动component的时候要reset editor
    this.resetEditor();
    this.editor.$blockScrolling = Infinity;
    //初始化的时候把焦点设置在这个document的editor上
    document.getElementsByTagName('textarea')[0].focus();
    this.collaboration.init(this.editor, this.sessionId);
    //收到来自服务器转发的change们，实则是来自别的clients的代码的变化量，用lastAppliedChange(ACE自带的属性)记录
    this.editor.lastAppliedChange = null;
    //on表示把editor和ACE绑定，用‘change’表示收到一个event。每次敲击键盘导致代码改变，都会触发一个callback
    this.editor.on('change', (e) => {
      console.log("editor changes: " + JSON.stringify(e));//使用json的形式发送
      if (this.editor.lastAppliedChange != e) {
        this.collaboration.change(JSON.stringify(e));
      }
    })
    //在editor中绑定了changeCursor事件，把光标的移动发送给collaboration
    //getSession()是ACE的api提供的方法.获得光标的位置是getSelection()方法中的getCursor()
    this.editor.getSession().getSelection().on("changeCursor", () => {
      let cursor = this.editor.getSession().getSelection().getCursor();
      console.log('cursor moves:' + JSON.stringify(cursor));
      this.collaboration.cursorMove(JSON.stringify(cursor));
    });
    // Restore changes that made by other users
    this.collaboration.restoreBuffer();
  }

  setLanguage(language: string): void {
    this.language = language;
    //改变语言的时候要reset editor
    this.resetEditor();
  }

  resetEditor(): void {
    if (this.language === 'C++') {
      this.editor.getSession().setMode("ace/mode/c_cpp");
    } else {
      this.editor.getSession().setMode('ace/mode/' + this.language.toLowerCase());//toLowerCase()是因为ace包里给的语言名字都是小写的
    }
    this.editor.setValue(this.defaultContent[this.language]);
    this.output = '';
  }

  /**
   *Ace提供了一个函数getValue(),可以得到editor里面所有的代码
   */
  submit(): void {
    let userCode = this.editor.getValue();
    let data = {
      user_code: userCode,
      lang: this.language.toLowerCase()
    };
    this.data.buildAndRun(data).then(res => this.output = res.text);
    console.log(userCode);
  }
}
