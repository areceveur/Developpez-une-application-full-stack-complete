import {Component} from "@angular/core";
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginRequest} from "../../interfaces/LoginRequest.interface";
import {AuthSuccess} from "../../interfaces/AuthSuccess.interface";
import {User} from "../../../../interfaces/user.interface";
import {SessionService} from "../../../../services/session.service";
import {AuthService} from "../../services/auth.service";
import {PasswordValidator} from "../../services/password.validator";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {
  public onError = false;
  public hide = true;

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
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/articles'])
        });
      },
      error => {
        console.error('Erreur de connexion :', error);
        this.onError = true
      }
    );
  }

  public back(): void {
    window.history.back();
  }
}
