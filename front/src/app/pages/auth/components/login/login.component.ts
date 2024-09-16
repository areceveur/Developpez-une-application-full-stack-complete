import {Component} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import * as passwordValidator from 'password-validator';
import {Router} from "@angular/router";
import {LoginRequest} from "../../interfaces/LoginRequest.interface";
import {AuthSuccess} from "../../interfaces/AuthSuccess.interface";
import {User} from "../../../../interfaces/user.interface";
import {SessionService} from "../../../../services/session.service";
import {AuthService} from "../../services/auth.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {
  schema = new passwordValidator();
  public onError = false;

  constructor(private authService: AuthService,
              private fb: FormBuilder,
              private router: Router,
              private sessionService: SessionService) {
    this.schema
      .is().min(8)
      .is().max(100)
      .has().uppercase()
      .has().lowercase()
      .has().digits()
      .has().symbols();
  }

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  public submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/rentals'])
        });
        this.router.navigate(['/rentals'])
      },
      error => {
        console.error('Erreur de connexion :', error);
        this.onError = true
      }
    );
  }
}
