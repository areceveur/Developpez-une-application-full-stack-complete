import {SessionService} from "./session.service";
import {fakeAsync, TestBed, tick} from "@angular/core/testing";
import {User} from "../interfaces/user.interface";

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
    localStorage.clear();
  });

  it('should be created', () => {
    service = TestBed.inject(SessionService);
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
    service = TestBed.inject(SessionService);
    service.logIn(mockUser);
    service.logOut();

    expect(service.user).toBeUndefined();
    expect(service.isLogged).toBe(false);
  });

  it('should update $isLogged observable when logging in and out', () => {
    let loggedStates: boolean[] = [];

    service = TestBed.inject(SessionService);
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
    service = TestBed.inject(SessionService);
    service.logIn(mockUser);

    expect(service.isUserLoggedIn()).toBe(true);
  });

  it('should return false when no user is logged in', () => {
    service = TestBed.inject(SessionService);
    service.logOut();
    expect(service.isUserLoggedIn()).toBe(false);
  });

})
