import {Component, OnInit, Inject} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

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

  constructor(@Inject('collaboration') private collaboration, @Inject('data') private data, private route: ActivatedRoute) {
  }

  ngOnInit() {
    //send id of the problem to the server to locate the specific problem
    this.route.params.subscribe(params => {
      this.sessionId = params['id'];
      this.initEditor();
    });
  }

  initEditor() {
    this.editor = ace.edit('editor');
    this.editor.setTheme('ace/theme/eclipse');
    //reset editor
    this.resetEditor();
    this.editor.$blockScrolling = Infinity;
    //fosus on document editor
    document.getElementsByTagName('textarea')[0].focus();
    this.collaboration.init(this.editor, this.sessionId);
    this.editor.lastAppliedChange = null;
    //apply the change when keyboard changes
    this.editor.on('change', (e) => {
      console.log("editor changes: " + JSON.stringify(e));
      if (this.editor.lastAppliedChange != e) {
        this.collaboration.change(JSON.stringify(e));
      }
    })
    //apply the change when cursor changes
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
   *Ace native method getValue()
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
