import {RouterModule, Routes} from "@angular/router";
import {ArticlesComponent} from "./components/list/articles.component";
import {NgModule} from "@angular/core";
import {FormComponent} from "./components/form/form.component";

const routes: Routes = [
  { title: 'Articles', path: '', component: ArticlesComponent },
  { title: 'Create', path: 'create', component: FormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule { }
