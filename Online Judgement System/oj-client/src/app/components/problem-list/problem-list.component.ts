import {Component, OnInit, Inject} from '@angular/core';
import {Problem} from "../../models/problems.model";
import {Subscription} from "rxjs/Subscription";
/*
 * 定义一个常量PROBLEMS
 * 用JavaScript定义直接可以是PROBLEMS = [],但typescript多一步声明类型——是Problem类型，因为ts是"强类型"。
 * */
/*
 const PROBLEMS: Problem[] = [{
 ..
 }]
 * */
@Component({
  selector: 'app-problem-list',
  templateUrl: "./problem-list.component.html",
  styleUrls: ["./problem-list.component.css"],
})
export class ProblemListComponent implements OnInit {
  problems: Problem[] = []; //定义一个成员变量，声明类型（ts的"强类型"属性）
  subscriptionProblems: Subscription;

  constructor(@Inject("data") private data) {
  }

  /*
   * component被建立的时候，它的problems被赋值PROBLEMS常量
   * */
  ngOnInit() {
    // this.problems = PROBLEMS;
    /**
     * 通过service，调用service中的function得到problems
     */
    this.getProblems();
  }

  getProblems(): void {
    // this.problems = this.data.getProblems();
    //getProblems()返回来的值是一个observable，想要得到observable的值的话就对它进行subscribe。每当getProblems（）变化的时候函数problems => this.problems = problems都能被触发，this.problems被赋值为problems
    this.subscriptionProblems = this.data.getProblems()
      .subscribe(problems => this.problems = problems);

  }

  getSearchTerm(): void {

  }

  //目的：防止memory niche。每当新建一个problem，都会新建一个subscription。这样subscription就会越来越多，memory中有会产生大量垃圾
  ngOnDestroy() {

  }

}
