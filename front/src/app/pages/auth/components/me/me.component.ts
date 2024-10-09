import {Component} from "@angular/core";
import {User} from "../../../../interfaces/user.interface";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {ThemeService} from "../../../themes/services/theme.service";
import {Theme} from "../../../themes/interfaces/theme.interface";
import {catchError, of, switchMap} from "rxjs";
import {SessionService} from "../../../../services/session.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PasswordValidator} from "../../services/password.validator";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"]
})
export class MeComponent {
  public user: User | undefined;
  public subscribedThemes: Theme[] = [];
  public profileForm: FormGroup;
  public passwordForm: FormGroup;

  constructor(private authService: AuthService,
              private router: Router,
              private themeService: ThemeService,
              private sessionService: SessionService,
              private snackBar: MatSnackBar,
              private fb: FormBuilder) {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      newEmail: ['', [Validators.required, Validators.email]]
    });
    this.passwordForm = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, PasswordValidator.strong]]
    });
  }

  public ngOnInit(): void {
    this.loadUserInfoAndThemes();
  }

  private loadUserInfoAndThemes(): void {
    this.authService.me().pipe(
      switchMap((user: User | null) => {
        if (user) {
          console.log("Récupération de l'utilisateur", user)
          this.user = user;

          this.profileForm.patchValue({
            newEmail: user.email,
            username: user.username
          });

          return this.themeService.getThemesById();
        }
        return of([]);
      }),
      catchError((error) => {
        console.error('Erreur lors de la récupération des abonnements ou de l\'utilisateur', error);
        return(of(null))
      })
    ).subscribe((themes: Theme[] | null) => {
      if (themes) {
        this.subscribedThemes = themes;
        console.log(this.subscribedThemes);
      }
    });
  }

  public onSubmit(): void {
    if (this.profileForm.valid) {

      const updatedUser = this.profileForm.value;

      console.log("Profile form : ", updatedUser.newEmail);

      const currentEmail = this.user?.email;

      const updatedRequest = {
        currentEmail: currentEmail,
        username: updatedUser.username,
        newEmail: updatedUser.newEmail
      };

      console.log("Profile form updated : ", updatedRequest);

      this.authService.updateUser(updatedRequest).pipe(
        switchMap(() => {
        this.snackBar.open("Le profil a été mis à jour", "Fermer", {duration: 3000});
        return of(null);
      }),
          catchError(error => {
        console.error("Erreur lors de la mise à jour du profil", error);
        this.snackBar.open("Erreur lors de la mise à jour du profil", "Fermer", {duration: 3000})
        return of(null);
          })
      ).subscribe();
    }
  }

  public unsubscribe(themeId: number, userId: number): void {
    this.themeService.unSubscribe(themeId, userId).pipe(
      switchMap(() => {
      this.subscribedThemes = this.subscribedThemes.filter(theme => theme.id !== themeId);
      this.snackBar.open("Désabonnement réussi !", "Fermer", {duration: 3000})
        return of(null);
    }),
        catchError(error => {
      console.error('Erreur lors du désabonnement', error);
      this.snackBar.open("Erreur lors du désabonnement", 'Fermer', {duration: 3000});
      return of(null);
    })
    ).subscribe();
  }

  public changePassword(): void {
    if (this.passwordForm.valid) {
      const passwords = this.passwordForm.value;
      this.authService.changePassword(passwords).pipe(
        switchMap(() => {
          this.snackBar.open("Mot de passe mis à jour", "Fermer", {duration: 3000});
          return of(null)
        }),
        catchError(error => {
          console.error('Erreur lors de la mise à jour du mot de passe', error);
          this.snackBar.open("Erreur lors de la mise à jour du mot de passe","Fermer", {duration: 3000})
          return of(null);
        })
      ).subscribe();
    }
  }

  public back(): void {
    window.history.back();
  }

  public logout(): void {
    this.authService.logout();
    this.router.navigate(['']);
  }
}
