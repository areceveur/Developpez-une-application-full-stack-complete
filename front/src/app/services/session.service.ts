import {Injectable, OnDestroy} from "@angular/core";
import {User} from "../interfaces/user.interface";
import {BehaviorSubject, catchError, EMPTY, Observable, Subject, takeUntil, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class SessionService implements OnDestroy {
  private destroy$ = new Subject<boolean>();

  constructor(private httpClient: HttpClient) {
    const token = localStorage.getItem('token');
    if (token) {
      this.httpClient.get<User>('api/auth/me').pipe(
        tap(user => {
          this.user = user;
          this.isLogged = true;
          this.next();
        }),
        catchError(() => {
          this.logOut();
          return EMPTY;
        }),
        takeUntil(this.destroy$)
      ).subscribe();
    } else {
      this.isLogged = false;
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
    return this.isLogged;
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
