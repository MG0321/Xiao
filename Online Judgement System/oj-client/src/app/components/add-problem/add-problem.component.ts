import { Component, OnInit, Inject } from '@angular/core';
import { Problem } from "../../models/problems.model";
//freeze的意思就是不能修改
const DEFAULT_PROBLEM: Problem = Object.freeze({
    id: 0,
    name: "",
    desc: "",
    difficulty: "Default"
})
@Component({
    selector: 'app-add-problem',
    templateUrl: './add-problem.component.html',
    styleUrls: ['./add-problem.component.css']
})
export class AddProblemComponent implements OnInit {
    public difficulties = ["Super", "Hard", "Medium", "Easy"];
    newProblem: Problem = Object.assign({}, DEFAULT_PROBLEM);
    constructor( @Inject("data") private data, @Inject("authGuard") private authGuard) {
    }


    ngOnInit() {
    }

    addProblem(): void {
        this.data.addProblem(this.newProblem)
        .catch(error=>console.log(error._body));
        this.newProblem = Object.assign({}, DEFAULT_PROBLEM);
    }
}
//constructor里的变量一定都是private的吗？
//为什么有的就要强调访问权限，有的就不用？public difficulties = ["Super", "Hard", "Medium", "Easy"];
