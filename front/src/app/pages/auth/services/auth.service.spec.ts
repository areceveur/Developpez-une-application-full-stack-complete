import {AuthService} from "./auth.service";
import {TestBed} from "@angular/core/testing";
import {Router} from "@angular/router";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {LoginRequest} from "../interfaces/LoginRequest.interface";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {TokenResponse} from "../../../interfaces/tokenResponse.interface";
import {User} from "../../../interfaces/user.interface";

describe('AuthService', () => {
  let routerMock: Router;
  let service: AuthService;
  let httpMock: HttpTestingController;

  routerMock = {
    navigate: jest.fn()
  } as any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService,
        {provide: Router, useValue: routerMock}]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  const mockTokenResponse: TokenResponse = {
    token: 'false-token'
  }

  it('should login correctly and store a token', () => {
    const loginRequest: LoginRequest = {
      email: "user@test.com",
      password: "password"
    };

    service.login(loginRequest).subscribe((tokenResponse: TokenResponse) => {
      expect(tokenResponse).toEqual(mockTokenResponse);
    });

    const req = httpMock.expectOne(`${service['pathService']}/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockTokenResponse);

    expect(localStorage.getItem('token')).toBe(mockTokenResponse.token);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/articles']);
  });

  it('should register correctly', () => {
    const registerRequest: RegisterRequest = {
      email: "user@test.com",
      password: "password",
      username: "UserTest"
    };

    service.register(registerRequest).subscribe(() => {
      expect(routerMock.navigate).toHaveBeenCalledWith(['/articles']);
    });
    const req = httpMock.expectOne(`${service['pathService']}/register`);
    expect(req.request.method).toBe('POST');
    req.flush(mockTokenResponse);

    expect(localStorage.getItem('token')).toBe(mockTokenResponse.token);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/articles']);
  });

  it('should get user data (me)', () => {
    const mockUser: User = {
      id: 1,
      email: 'user@test.com',
      username: 'userTest',
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };

    service.me().subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(`${service['pathService']}/me`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('Should logout correctly', () => {
    service.logout();
    expect(routerMock.navigate).toHaveBeenCalledWith(['']);
  })

  it('Should update the user correctly', () => {
    const mockUserRequest = {
      currentEmail: 'user@test.com',
      username: 'userTest',
      newEmail: 'newUser@test.com'
    };
    service.updateUser(mockUserRequest).subscribe(user => {
      expect(user).toEqual(mockUserRequest);
      });

    const req = httpMock.expectOne(`${service['pathService']}/me`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockUserRequest);
    req.flush(mockUserRequest);
  })

  it('Should change the password', () => {
    const passwords = {
      currentPassword: 'oldPassword',
      newPassword: 'newPassword'
    };

    service.changePassword(passwords).subscribe(password => {
      expect(password).toBeTruthy();
    });
    const req = httpMock.expectOne(`${service['pathService']}/me`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(passwords);
    req.flush({});
  })
})
