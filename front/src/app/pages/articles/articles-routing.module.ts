import {RouterModule, Routes} from "@angular/router";
import {ArticlesComponent} from "./components/articles.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
  { title: 'Articles', path: '', component: ArticlesComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule { }
