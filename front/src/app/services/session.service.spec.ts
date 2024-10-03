import {SessionService} from "./session.service";
import {TestBed} from "@angular/core/testing";
import {User} from "../interfaces/user.interface";

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
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
      updated_at: new Date()
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
  })
})
