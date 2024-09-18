import {NgModule} from "@angular/core";
import {ArticlesComponent} from "./components/articles.component";
import {ArticlesRoutingModule} from "./articles-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";

@NgModule({
  declarations: [
    ArticlesComponent
  ],
  imports: [
    ArticlesRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    AsyncPipe,
    NgForOf,
    NgIf
  ]
})

export class ArticlesModule { }
