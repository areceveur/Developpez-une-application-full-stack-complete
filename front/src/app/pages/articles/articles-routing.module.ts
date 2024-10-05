import {RouterModule, Routes} from "@angular/router";
import {ArticlesComponent} from "./components/list/articles.component";
import {NgModule} from "@angular/core";
import {FormComponent} from "./components/form/form.component";
import {DetailComponent} from "./components/detail/detail.component";

const routes: Routes = [
  { title: 'Articles', path: '', component: ArticlesComponent },
  { title: 'Create', path: 'create', component: FormComponent },
  { title: 'Detail', path: 'article/:id', component: DetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule { }
