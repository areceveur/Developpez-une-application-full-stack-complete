import {Injectable} from "@angular/core";
import {User} from "../interfaces/user.interface";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class SessionService {

  constructor() {
    const token = localStorage.getItem('token');
    if (token) {
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
