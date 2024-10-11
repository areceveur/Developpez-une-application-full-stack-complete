import {Injectable} from "@angular/core";
import { HttpClient } from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Theme} from "../interfaces/theme.interface";
import {AuthService} from "../../auth/services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private apiUrl = 'api/themes'

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  public getAllThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.apiUrl);
  }

  public subscription(themeId: number, userId: number): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrl}/subscribe/${themeId}`, {});
  }

  public unSubscribe(themeId: number, userId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/unsubscribe/${themeId}`, {});
  }

  public getThemesById(): Observable<Theme[]> {
    return this.httpClient.get<{themes : Theme[]}>('api/auth/me').pipe(
      map(response => response.themes)
    );
  }
}
