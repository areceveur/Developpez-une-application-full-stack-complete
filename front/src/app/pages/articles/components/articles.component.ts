import {Component} from "@angular/core";
import {AuthService} from "../../auth/services/auth.service";
import {Observable} from "rxjs";
import {ArticlesResponse} from "../interfaces/articlesResponse.interface";
import {User} from "../../../interfaces/user.interface";
import {SessionService} from "../../../services/session.service";
import {ArticlesService} from "../services/articles.service";

@Component({
  selector: "app-articles",
  templateUrl: "./articles.component.html",
  styleUrls: ["./articles.component.css"],
})
export class ArticlesComponent {
  public articles$: Observable<ArticlesResponse>;

  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
    private articlesService: ArticlesService
  ) {
    this.articles$ = this.articlesService.all();
  }
  get user(): User | undefined {
    return this.sessionService.user;
  }
}
