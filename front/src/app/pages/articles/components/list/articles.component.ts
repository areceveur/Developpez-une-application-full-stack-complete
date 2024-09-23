import {Component} from "@angular/core";
import {Observable} from "rxjs";
import {ArticlesResponse} from "../../interfaces/articlesResponse.interface";
import {SessionService} from "../../../../services/session.service";
import {ArticlesService} from "../../services/articles.service";

@Component({
  selector: "app-articles",
  templateUrl: "./articles.component.html",
  styleUrls: ["./articles.component.scss"],
})
export class ArticlesComponent {
  public articles$: Observable<ArticlesResponse>;

  constructor(
    private articlesService: ArticlesService
  ) {
    this.articles$ = this.articlesService.all();
    this.articles$.subscribe((articles) => {
      console.log('Articles:', articles);
    });

  }
}
