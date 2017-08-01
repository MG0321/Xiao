import {Injectable} from '@angular/core';
import {COLORS} from '../../assets/colors';
//因为socket.io是第三方的library所以要用declare外部引用一下
declare var io: any;
declare var ace: any;
@Injectable()
export class CollaborationService {
  collaborationSocket: any

  clientsInfo: Object = {}

  clientNum: number = 0;

  constructor() {
  }

  init(editor: any, sessionId: string): void {
    /**
     * 使用io()函数发送message，并在console中打印message用于检查 -Client端
     */
    this.collaborationSocket = io(window.location.origin, {query: 'sessionId=' + sessionId});//表示当前正在使用的网页的地址
    //client监听来自服务器端的change
    this.collaborationSocket.on('change', (delta: string) => {//delta表示变化量，如第几行变化了什么
      console.log('collaboration: editor chaned by' + delta);
      delta = JSON.parse(delta);
      editor.lastAppliedChange = delta;
      editor.getSession().getDocument().applyDeltas([delta])
    });
    this.collaborationSocket.on("cursorMove", (cursor) => {
      console.log("cursor move: " + cursor);
      let session = editor.getSession();
      //cursor是一个object，有行号，列号，等等
      cursor = JSON.parse(cursor);
      let x = cursor['row'];
      let y = cursor['column'];
      //多人操作的时候要知道是谁的cursor在移动
      let changeClientId = cursor['socketId'];
      console.log(x + ' ' + y + ' ' + changeClientId);
      if (changeClientId in this.clientsInfo) {
        session.removeMarker(this.clientsInfo[changeClientId]['marker']);
      } else {
        this.clientsInfo[changeClientId] = {};
        let css = document.createElement('style');
        css.type = 'text/css';
        css.innerHTML = '.editor_cursor_' + changeClientId
          + ' {position:absolute; background:' + COLORS[this.clientNum] + ";"
          + "z-index:100; width:2px !important; }";

        document.body.appendChild(css);
        this.clientNum++;
      }
      let Range = ace.require('ace/range').Range;
      let newMarker = session.addMarker(new Range(x, y, x, y + 1), 'editor_cursor_' + changeClientId, true);
      this.clientsInfo[changeClientId]['marker'] = newMarker;
    })
    this.collaborationSocket.on("message", (message) => {
      console.log("received: " + message);
    })
  }

  change(delta: string): void {
    this.collaborationSocket.emit('change', delta);
  }

  cursorMove(cursor: string): void {
    //socket的发送端
    this.collaborationSocket.emit("cursorMove", cursor);
  }

  restoreBuffer(): void {
    this.collaborationSocket.emit("restoreBuffer");
  }
}
