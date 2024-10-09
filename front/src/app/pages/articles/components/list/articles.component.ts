import {Component, OnInit} from "@angular/core";
import {catchError, Observable, of, switchMap} from "rxjs";
import {ArticlesResponse} from "../../interfaces/articlesResponse.interface";
import {ArticlesService} from "../../services/articles.service";
import {Article} from "../../interfaces/article.interface";

@Component({
  selector: "app-articles",
  templateUrl: "./articles.component.html",
  styleUrls: ["./articles.component.scss"],
})
export class ArticlesComponent implements OnInit {
  public articles$: Observable<ArticlesResponse>;
  public articles: Article[] = [];
  public sortOrder: 'asc' | 'desc' = 'asc';
  public isLoading = true;
  public errorMessage: string = "";

  constructor(
    private articlesService: ArticlesService
  ) {
    this.articles$ = this.articlesService.all();
  }

  ngOnInit(): void {
    this.fetchArticle()
  }

  private fetchArticle(): void {
    this.articlesService.all().pipe(
      catchError(error => {
        this.errorMessage = "Erreur lors du chargement de la page";
        this.isLoading = false;
        return(of([]));
      })
    ).subscribe((articles: Article[]) => {
      this.isLoading = false;
      this.articles = articles;
      this.sortArticles();
    })
  }

  public toggleSortOrder(): void {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.sortArticles();
  }

  private sortArticles(): void {
    this.articles.sort((a,b) => {
      const dateA = new Date(a.created_at).getTime();
      const dateB = new Date(b.created_at).getTime();
      return this.sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
    });
  }
}
