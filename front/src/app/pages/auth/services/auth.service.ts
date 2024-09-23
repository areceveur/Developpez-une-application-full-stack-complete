import {Injectable} from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable, tap} from "rxjs";
import {LoginRequest} from "../interfaces/LoginRequest.interface";
import {TokenResponse} from "../../../interfaces/tokenResponse.interface";
import {User} from "../../../interfaces/user.interface";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {AuthSuccess} from "../interfaces/AuthSuccess.interface";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = '/api/auth';

  constructor(private httpClient: HttpClient,
              private router: Router) { }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({'Authorization': `Bearer ${token}`});
  }

  public login(loginRequest: LoginRequest): Observable<TokenResponse> {
    return this.httpClient.post<TokenResponse>(`${this.pathService}/login`, loginRequest)
      .pipe(
        tap((response: TokenResponse) => {
          localStorage.setItem('token', response.token);
          this.router.navigate(['/articles']);
        })
      );
  }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/register`, registerRequest)
      .pipe(
        tap((response: TokenResponse) => {
          localStorage.setItem('token', response.token);
          this.router.navigate(['/articles']);
        })
      );
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`, {headers: this.getAuthHeaders()});
  }

}
