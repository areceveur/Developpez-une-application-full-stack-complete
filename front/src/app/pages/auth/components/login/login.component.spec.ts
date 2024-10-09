import {LoginComponent} from "./login.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {of, throwError} from "rxjs";
import {AuthService} from "../../services/auth.service";
import {SessionService} from "../../../../services/session.service";
import {Router} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";
import {TokenResponse} from "../../../../interfaces/tokenResponse.interface";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {User} from "../../../../interfaces/user.interface";
import {expect} from '@jest/globals'


describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: any;
  let sessionServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    authServiceMock = {
      login: jest.fn(),
      me: jest.fn(),
    };

    sessionServiceMock = {
      logIn: jest.fn()
    };

    routerMock = {
      navigate: jest.fn()
    };

    const mockLocalStorage = {
      setItem: jest.fn(),
      getItem: jest.fn(),
      removeItem: jest.fn(),
      clear: jest.fn()
    };

    Object.defineProperty(window, 'localStorage', { value: mockLocalStorage });

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock},
        { provide: AuthService, useValue: authServiceMock }
      ],
      imports: [
        RouterTestingModule,
        MatButtonModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        BrowserAnimationsModule
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('should login correctly', () => {
    const user = {
      email: "user@test.com",
      password: "userTest@1234"
    };
    component.form.setValue(user);

    const tokenResponse: TokenResponse = {
      token: "false-token",
    };

    authServiceMock.login.mockReturnValue(of(tokenResponse));

    const userResponse: User = {
      id: 3,
      email: "user@test.com",
      username: "User Test",
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };

    authServiceMock.me = jest.fn().mockReturnValue(of(userResponse));

    component.submit();

    expect(authServiceMock.login).toHaveBeenCalledWith(user);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/articles']);
  })

  it('Should send an error when bad login', () => {
    const user = {
      email: "user@test.com",
      password: "userTest@1234"
    };

    component.form.setValue(user);

    authServiceMock.login.mockReturnValue(throwError(() => new Error("Erreur de connexion")));

    component.submit();

    expect(authServiceMock.login).toHaveBeenCalledWith(user);
    expect(component.onError).toBe(true);
  });

  it('should store token in localStorage after successful login', () => {
    const user = {
      email: "user@test.com",
      password: "userTest@1234"
    };
    component.form.setValue(user);

    const tokenResponse: TokenResponse = { token: "false-token" };
    const userResponse: User = { id: 1, username: "testuser", email: "user@test.com", created_at: new Date(), updated_at: new Date(), subscriptions: [] };

    authServiceMock.login.mockReturnValue(of(tokenResponse));
    authServiceMock.me.mockReturnValue(of(userResponse));

    const localStorageSpy = window.localStorage.setItem;

    component.submit();

    expect(localStorageSpy).toHaveBeenCalledWith('token', 'false-token');
  });

  it('should call sessionService.logIn after successful login', () => {
    const user = {
      email: "user@test.com",
      password: "userTest@1234"
    };
    component.form.setValue(user);

    const tokenResponse: TokenResponse = { token: "mock-token" };
    const userResponse: User = { id: 1, username: "testuser", email: "user@test.com", created_at: new Date(), updated_at: new Date(), subscriptions: [] };

    authServiceMock.login.mockReturnValue(of(tokenResponse));
    authServiceMock.me.mockReturnValue(of(userResponse));

    component.submit();

    expect(sessionServiceMock.logIn).toHaveBeenCalledWith(userResponse);
  });

  it('should call window.history.back when back is called', () => {
    const originalHistoryBack = window.history.back;
    window.history.back = jest.fn();
    component.back();
    expect(window.history.back).toHaveBeenCalled();
    window.history.back = originalHistoryBack;
  });

})
