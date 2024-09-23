import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Theme} from "../interfaces/theme.interface";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private apiUrl = 'api/themes'

  constructor(private httpClient: HttpClient) {}

  public getAllThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.apiUrl);
  }
}
