import {Injectable} from "@angular/core";
import {User} from "../interfaces/user.interface";
import {BehaviorSubject, Observable} from "rxjs";
import {Article} from "../pages/articles/interfaces/article.interface";

@Injectable({
  providedIn: 'root'
})

export class SessionService {

  constructor() {
    const token = localStorage.getItem('token');
    if (token) {
      this.user = this.getUserFromToken(token);
      this.isLogged = true;
      this.next();
    }
  }

  public isLogged = false;
  public user: User | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  private getUserFromToken(token: string): User {
    const payload = token.split('.')[1];
    const decoded = JSON.parse(atob(payload));

    console.log('Decoded Token Payload:', decoded);

    const user: User = {
      id: decoded.userId,
      email: decoded.sub,
      username: decoded.username,
      created_at: decoded.created_at,
      updated_at: decoded.updated_at,
      subscriptions: decoded.subscriptions
    }
    console.log(user);
    return user;
  }

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
