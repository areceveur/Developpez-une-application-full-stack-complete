import {MeComponent} from "./me.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {SessionService} from "../../../../services/session.service";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientModule} from "@angular/common/http";
import {of, throwError} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {ThemeService} from "../../../themes/services/theme.service";

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let authServiceMock: any;
  let sessionServiceMock: any;
  let routerMock: any;
  let themeServiceMock: any;
  let snackBarMock: any;

  beforeEach(async () => {
    authServiceMock = {
      me: jest.fn().mockReturnValue(of(({id: 1, username: "TestUser", email: "user@test.com"}))),
      updateUser: jest.fn().mockReturnValue(of({})),
      changePassword: jest.fn().mockReturnValue(of({})),
      logout: jest.fn()
    };

    themeServiceMock = {
      getThemesById: jest.fn().mockReturnValue(of([{id: 1, name: "Développement web"}])),
      unSubscribe: jest.fn().mockReturnValue(of({}))
    }

    sessionServiceMock = {
      logIn: jest.fn()
    };

    routerMock = {
      navigate: jest.fn()
    };

    snackBarMock = {
      open: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      providers: [
        {provide: SessionService, useValue: sessionServiceMock},
        {provide: Router, useValue: routerMock},
        {provide: AuthService, useValue: authServiceMock},
        {provide: ThemeService, useValue: themeServiceMock},
        {provide: MatSnackBar, useValue: snackBarMock},
        FormBuilder
      ],
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  it('Should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load user info and themes on init', () => {
    const userSpy = jest.spyOn(authServiceMock, 'me');
    const themeSpy = jest.spyOn(themeServiceMock, 'getThemesById');

    component.ngOnInit();

    expect(userSpy).toHaveBeenCalled();
    expect(themeSpy).toHaveBeenCalled();
    expect(component.user?.username).toEqual('TestUser');
    expect(component.subscribedThemes.length).toBe(1);
  });

  it('should handle error during loadUserInfoAndThemes', () => {
    jest.spyOn(authServiceMock, 'me').mockReturnValue(throwError(() => new Error('Error')));
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    component.ngOnInit();

    expect(consoleErrorSpy).toHaveBeenCalledWith('Erreur lors de la récupération des abonnements ou de l\'utilisateur', expect.anything());
  });

  it('should update user profile on valid form submission', () => {
    component.profileForm.setValue({ username: 'newUser', newEmail: 'new@example.com' });
    component.onSubmit();

    expect(authServiceMock.updateUser).toHaveBeenCalledWith({
      username: 'newUser',
      newEmail: 'new@example.com'
    });
    expect(snackBarMock.open).toHaveBeenCalledWith("Le profil a été mis à jour", "Fermer", {duration: 3000});
  });

  it('should unsubscribe from a theme', () => {
    component.subscribedThemes = [{ id: 1, name: 'Développement web', description: "Thème 1" }, { id: 2, name: 'Langage de programmation', description: "Thème 2" }];
    jest.spyOn(themeServiceMock, 'unSubscribe').mockReturnValue(of({}));

    component.unsubscribe(1, 1);

    expect(themeServiceMock.unSubscribe).toHaveBeenCalledWith(1, 1);
    expect(component.subscribedThemes.length).toBe(1);
    expect(snackBarMock.open).toHaveBeenCalledWith("Désabonnement réussi !", "Fermer", { duration: 3000 });
  });

  it('should change password if form is valid', () => {
    component.passwordForm.setValue({
      currentPassword: 'oldPassword@1234',
      newPassword: 'newPassword@1234'
    });

    jest.spyOn(authServiceMock, 'changePassword').mockReturnValue(of({}));

    component.changePassword();

    expect(authServiceMock.changePassword).toHaveBeenCalledWith({
      currentPassword: 'oldPassword@1234',
      newPassword: 'newPassword@1234'
    });
    expect(snackBarMock.open).toHaveBeenCalledWith("Mot de passe mis à jour", "Fermer", {duration: 3000});
  });


  it('should use the button back', () => {
    jest.spyOn(window.history, 'back');
    component.back();
    expect(window.history.back).toHaveBeenCalled();
  });

  it('should call logout and navigate to root on logout', () => {
    component.logout();
    expect(authServiceMock.logout).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['']);
  });

  it('should handle error during profile update', () => {
    component.profileForm.setValue({ username: 'newUser', newEmail: 'new@example.com' });
    jest.spyOn(authServiceMock, 'updateUser').mockReturnValue(throwError(() => new Error('Erreur de mise à jour')));
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    component.onSubmit();

    expect(consoleErrorSpy).toHaveBeenCalledWith("Erreur lors de la mise à jour du profil", expect.anything());
    expect(snackBarMock.open).toHaveBeenCalledWith("Erreur lors de la mise à jour du profil", "Fermer", { duration: 3000 });
  });

  it('should handle error during unsubscribe', () => {
    jest.spyOn(themeServiceMock, 'unSubscribe').mockReturnValue(throwError(() => new Error('Erreur de désabonnement')));
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    component.unsubscribe(1, 1);

    expect(consoleErrorSpy).toHaveBeenCalledWith('Erreur lors du désabonnement', expect.anything());
    expect(snackBarMock.open).toHaveBeenCalledWith("Erreur lors du désabonnement", "Fermer", { duration: 3000 });
  });

  it('should handle error during changePassword', () => {
    component.passwordForm.setValue({
      currentPassword: 'oldPassword@1234',
      newPassword: 'newPassword@1234'
    });

    jest.spyOn(authServiceMock, 'changePassword').mockReturnValue(throwError(() => new Error('Erreur de changement de mot de passe')));
    const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    component.changePassword();

    expect(consoleErrorSpy).toHaveBeenCalledWith('Erreur lors de la mise à jour du mot de passe', expect.anything());
    expect(snackBarMock.open).toHaveBeenCalledWith("Erreur lors de la mise à jour du mot de passe", "Fermer", { duration: 3000 });
  });



})
