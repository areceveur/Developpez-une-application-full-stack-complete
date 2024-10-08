import {Injectable} from "@angular/core";
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticlesResponse} from "../interfaces/articlesResponse.interface";
import {ArticleResponse} from "../interfaces/articleResponse.interface";
import {Article} from "../interfaces/article.interface";
import {Comment} from "../interfaces/comment.interface";

@Injectable({
  providedIn: 'root'
})
export class ArticlesService {
  private pathService = '/api/articles';

  constructor(private httpClient: HttpClient) {}

  public all(): Observable<ArticlesResponse> {
    return this.httpClient.get<ArticlesResponse>(`${this.pathService}`);
  }

  public create(article: {titre: string, contenu: string}): Observable<ArticleResponse> {
    return this.httpClient.post<ArticleResponse>(`${this.pathService}/create`, article);
  }

  public detail(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`);
  }

  public getComments(id: string): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/${id}/comments`);
  }

  public addComments(articleId: string, comment: {content: string}): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}/${articleId}/comments`, comment)
  }
}
