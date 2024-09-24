import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import {of} from "rxjs";
import { HttpClientModule} from "@angular/common/http";
import {SessionService} from "./services/session.service";
import {Router} from "@angular/router";

describe('AppComponent', () => {
  let sessionServiceMock: any;
  let fixture;
  let app: AppComponent;
  let routerMock: any;

  beforeEach(async () => {
    sessionServiceMock = {
      $isLogged: jest.fn(),
      logOut: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock}
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'front'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('front');
  });

  it('should update $isLogged observable', () => {
    const mockIsLogged = true;

    sessionServiceMock.$isLogged.mockReturnValue(of(mockIsLogged));

    app.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(mockIsLogged);
    })
  });

  it('should log out the user', () => {
    app.logout();
    expect(sessionServiceMock.logOut).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith([''])
  })

});
