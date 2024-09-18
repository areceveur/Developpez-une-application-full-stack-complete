import {Component} from "@angular/core";
import {User} from "../../../../interfaces/user.interface";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, Validators} from "@angular/forms";
import {SessionService} from "../../../../services/session.service";
import {RegisterRequest} from "../../interfaces/registerRequest.interface";
import {AuthSuccess} from "../../interfaces/AuthSuccess.interface";
import {PasswordValidator} from "../../services/password.validator";

@Component({
  selector: "register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.scss"]
})
export class RegisterComponent{
  onError = false;

  constructor(private authService: AuthService,
              private router: Router,
              private fb: FormBuilder,
              private sessionService: SessionService) {}

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, PasswordValidator.strong]],
    username: ['', [Validators.required]]
  });

  public submit(): void {
    const registerRequest = this.form.value as RegisterRequest;
    this.authService.register(registerRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/articles'])
        });
      },
      error => {
        console.error('Erreur de connexion :', error);
        this.onError = true;
      }
    );
  }

    public back(): void {
      window.history.back();
  }
}
