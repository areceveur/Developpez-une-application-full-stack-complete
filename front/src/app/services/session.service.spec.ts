import {SessionService} from "./session.service";
import {TestBed} from "@angular/core/testing";
import {User} from "../interfaces/user.interface";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('SessionService', () => {
  let service: SessionService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionService]
    });
    service = TestBed.inject(SessionService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.setItem('token', 'false-token');

  });

  afterEach(() => {
    httpMock.verify();
    localStorage.removeItem('token');
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log out the user', () => {
    const mockUser: User = {
      id: 1,
      username: "UserTest",
      email: "user@test.com",
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };
    service.logIn(mockUser);
    service.logOut();

    expect(service.user).toBeUndefined();
    expect(service.isLogged).toBe(false);
  });

  it('should update $isLogged observable when logging in and out', () => {
    let loggedStates: boolean[] = [];

    service.$isLogged().subscribe((isLogged) => {
      loggedStates.push(isLogged);

      if (loggedStates.length === 3) {
        expect(loggedStates).toEqual([false, true, false]);
      }
    });

    const mockUser: User = {
      id: 1,
      username: "UserTest",
      email: "user@test.com",
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };

    service.logIn(mockUser);
    service.logOut();
  });

  it('should return true when user is logged in', () => {
    const mockUser: User = {
      id: 1,
      username: "UserTest",
      email: "user@test.com",
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };
    service.logIn(mockUser);

    expect(service.isUserLoggedIn()).toBe(true);
  });

  it('should return false when no user is logged in', () => {
    service.logOut();
    expect(service.isUserLoggedIn()).toBe(false);
  });

  it('should retrieve user on initialization if token is present', () => {
    const mockUser: User = {
      id: 1,
      username: "UserTest",
      email: "user@test.com",
      created_at: new Date(),
      updated_at: new Date(),
      subscriptions: []
    };

    service['httpClient'].get<User>('api/auth/me').subscribe(user => {
      expect(user).toEqual(mockUser);
      expect(service.isUserLoggedIn()).toBe(true);
      expect(service.user).toEqual(mockUser);
    });

    const req = httpMock.expectOne('api/auth/me');
    req.flush(mockUser);
  });

})
