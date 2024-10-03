import {Component, OnInit} from "@angular/core";
import {ArticlesService} from "../../services/articles.service";
import {ActivatedRoute} from "@angular/router";
import {Article} from "../../interfaces/article.interface";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit {

  public articleId: string;
  public article?: Article;
  public isLoading = false;
  public errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService,
  ) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  public ngOnInit(): void {
    this.fetchArticle();
  }

  public back() {
    window.history.back();
  }

  public fetchArticle(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.articleService
      .detail(this.articleId).pipe(
        catchError(error => {
          this.errorMessage = "Erreur lors du chargement de l'article";
          this.isLoading = false;
          return of(null);
        })
    ).subscribe((article: Article | null) => {
      this.isLoading = false;
      if (article) {
        this.article = article;
      }
      });
  }
}
