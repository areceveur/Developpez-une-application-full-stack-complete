import {Component, OnDestroy, OnInit} from "@angular/core";
import {User} from "../../../../interfaces/user.interface";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {ThemeService} from "../../../themes/services/theme.service";
import {Theme} from "../../../themes/interfaces/theme.interface";
import {catchError, of, Subject, switchMap, takeUntil} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PasswordValidator} from "../../services/password.validator";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"]
})
export class MeComponent implements OnInit, OnDestroy {
  public user: User | undefined;
  public subscribedThemes: Theme[] = [];
  public profileForm: FormGroup;
  public passwordForm: FormGroup;
  private destroy$ = new Subject<boolean>();

  constructor(private authService: AuthService,
              private router: Router,
              private themeService: ThemeService,
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
      }),
      takeUntil(this.destroy$)
    ).subscribe((themes: Theme[] | null) => {
      if (themes) {
        this.subscribedThemes = themes;
      }
    });
  }

  public onSubmit(): void {
    if (this.profileForm.valid) {

      const updatedUser = this.profileForm.value;

      const currentEmail = this.user?.email;

      const updatedRequest = {
        currentEmail: currentEmail,
        username: updatedUser.username,
        newEmail: updatedUser.newEmail
      };

      this.authService.updateUser(updatedRequest).pipe(
        switchMap(() => {
        this.snackBar.open("Le profil a été mis à jour", "Fermer", {duration: 3000});
        return of(null);
      }),
        catchError(error => {
          console.error("Erreur lors de la mise à jour du profil", error);
          this.snackBar.open("Erreur lors de la mise à jour du profil", "Fermer", {duration: 3000})
          return of(null);
        }),
        takeUntil(this.destroy$)
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
    }),
      takeUntil(this.destroy$)
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
        }),
        takeUntil(this.destroy$)
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

  ngOnDestroy() {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}
