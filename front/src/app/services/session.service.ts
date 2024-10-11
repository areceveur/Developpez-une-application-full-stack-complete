import {Injectable} from "@angular/core";
import {User} from "../interfaces/user.interface";
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class SessionService {

  constructor(private httpClient: HttpClient) {
    const token = localStorage.getItem('token');
    if (token) {
      this.httpClient.get<User>('api/auth/me').subscribe(user => {
        this.user = user;
        this.isLogged = true;
        this.next();
        }, () => {
        this.logOut();
      })
      this.isLogged = true;
      this.next();
    }
  }

  public isLogged = false;
  public user: User | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: User): void {
    this.user = user;
    this.isLogged = true;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.user = undefined;
    this.isLogged = false;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  public isUserLoggedIn(): boolean {
    return !!this.user;
  }
}
