import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
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

  public login(loginRequest: LoginRequest): Observable<TokenResponse> {
    const params = new HttpParams()
      .set('email', loginRequest.email)
      .set('password', loginRequest.password);

    return this.httpClient.post<TokenResponse>(`${this.pathService}/login`, null, { params });
  }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    const params = new HttpParams()
    .set('email', registerRequest.email)
    .set('password', registerRequest.password)
      .set('username', registerRequest.username);
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/register`, null, { params });
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`)
  }

}
