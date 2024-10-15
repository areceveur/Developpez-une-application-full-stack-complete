import {Component, OnDestroy} from "@angular/core";
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginRequest} from "../../interfaces/LoginRequest.interface";
import {User} from "../../../../interfaces/user.interface";
import {SessionService} from "../../../../services/session.service";
import {AuthService} from "../../services/auth.service";
import {PasswordValidator} from "../../services/password.validator";
import {catchError, of, Subject, switchMap, takeUntil} from "rxjs";
import {TokenResponse} from "../../../../interfaces/tokenResponse.interface";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnDestroy {
  public onError = false;
  public hide = true;
  private destroy$ = new Subject<boolean>();

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
  }

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, PasswordValidator.strong]]
  });

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).pipe(
    switchMap((response: TokenResponse) => {
      localStorage.setItem('token', response.token);
      return this.authService.me();
      }),
    catchError(error => {
      console.error('Erreur de connexion :', error);
      this.onError = true;
      return of(null);
    }),
      takeUntil(this.destroy$)
    ).subscribe((user: User | null) => {
      if(user) {
        this.sessionService.logIn(user);
        this.router.navigate(["/articles"]);
      }
    });
  }

  public back(): void {
    window.history.back();
  }

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
