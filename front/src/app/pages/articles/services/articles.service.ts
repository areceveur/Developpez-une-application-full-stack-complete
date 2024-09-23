import {Injectable} from "@angular/core";
import { HttpClient } from "@angular/common/http";
import {Observable} from "rxjs";
import {ArticlesResponse} from "../interfaces/articlesResponse.interface";
import {ArticleResponse} from "../interfaces/articleResponse.interface";

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
}
