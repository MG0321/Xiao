import {Routes, RouterModule} from "@angular/router";
import {ProblemListComponent} from './components/problem-list/problem-list.component';
import {ProfileComponent} from './components/profile/profile.component';
import {ProblemDetailComponent} from './components/problem-detail/problem-detail.component';
/**
 * 定义一个object
 * @type {[{path: string; redirectTo: string; pathMatch: string},{path: string; component: ProblemDetailComponent},{path: string; component: ProblemListComponent},{path: string; redirectTo: string}]}
 */
const routes: Routes = [{
  /**
   * 如果是根目录，转到problems，如果是其他目录，下面的代码都涵盖了所有情况
   */
  path: "",
  redirectTo: "problems",
  pathMatch: "full"
},
  {
    path: "problems",
    component: ProblemListComponent
  },
  {
    path: "problems/:id",
    component: ProblemDetailComponent
  },
  {
    path: "profile",
    component: ProfileComponent,
    canActivate: ["authGuard"]
  },
  {
    /**
     *如果是其他，也转到problems
     */
    path: "**",
    redirectTo: "problems"
  }
]
/**
 * 调用forRoot方法，返回一个RouterModule
 * @type {ModuleWithProviders}
 */
export const routing = RouterModule.forRoot(routes)
