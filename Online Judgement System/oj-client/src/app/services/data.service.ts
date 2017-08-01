import {Injectable} from '@angular/core';
import {Problem} from "../models/problems.model";
import {Http, Response, Headers} from "@angular/http";
import {BehaviorSubject} from "rxjs/BehaviorSubject"
import {Observable} from "rxjs/Rx";
import "rxjs/add/operator/toPromise";

@Injectable()
export class DataService {
  //problems: Problem[] = PROBLEMS;//为什么这里又没有private、public什么的了
  //BehaviorSubject有一个功能，就是定义对象的next的值
  private problemSource = new BehaviorSubject<Problem[]>([]);

  constructor(private http: Http) {
  }

  //普通的promise只会被trigger一次，当调用了一次promise，then或者catch就会被trigger一下；如果我们不再次调用promise，那它也不再会发生变化；observable则可以一直观察这个http请求，每当变化一次，then或catch都会被trigger一次。所以getProblems()返回了一个Observable对象
  getProblems(): Observable<Problem[]> {
    // return this.problems;
    this.http.get("api/v1/problems")//这里会返回一个observable对象
      .toPromise()//得到Promise对象
      .then((res: Response) => {
        this.problemSource.next(res.json());
      })
      .catch(this.handleError);
    //是observable对象就可被订阅，一旦发生改变，订阅它的对象就可以被通知
    return this.problemSource.asObservable();
  }

  //这个是异步的操作，整个request返回的是一个promise，它的内容是problem
  getProblem(id: number): Promise<Problem> {
    //return this.problems.find((problem) => problem.id === id);
    return this.http.get(`api/v1/problems/${id}`)//这里会返回一个observable对象。
      .toPromise()//转换成promise，得到Promise对象。
      .then((res: Response) => res.json())
      .catch(this.handleError);
  }

  addProblem(problem: Problem): Promise<Problem> {
    //   problem.id = this.problems.length + 1;
    // this.problems.push(problem);
    let headers = new Headers({'content-type': 'application/json'});
    return this.http.post("api/v1/problems", problem, headers)
      .toPromise()
      .then((res: Response) => {
        //为了页面的及时刷新，添加完新的problems后要调用一下getProblems显示全部的题目，不然页面上
        //还是会只显示原来的题目【这时候call一下backend的getProblems（）函数，就会得到最新发起的http.get请求的content，而得到最新的问题列表】
        this.getProblems();
        //如果成功了，要把新添加的东西以json形式返回回来
        return res.json();
      })
      .catch(this.handleError);
  }

  //error handler
  private handleError(error: any): Promise<any> {
    console.error('An error occur', error);//for demo purpose only
    return Promise.reject(error.body || error);
  }

  buildAndRun(data: JSON): Promise<Problem> {
    let headers = new Headers({'content-type': 'application/json'});
    return this.http.post("api/v1/build_and_run", data, headers)
      .toPromise()
      .then((res: Response) => {
        console.log(res);
        return res.json();
      })
      .catch(this.handleError);
  }
}
