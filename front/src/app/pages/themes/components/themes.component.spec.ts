import {of, throwError} from "rxjs";
import {ThemesComponent} from "./themes.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {Router} from "@angular/router";
import {SessionService} from "../../../services/session.service";
import {ThemeService} from "../services/theme.service";

describe('ThemesComponent', () => {

  let component: ThemesComponent;
  let fixture: ComponentFixture<ThemesComponent>;
  let themeServiceMock: any;
  let sessionServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    themeServiceMock = {
      getThemesById: jest.fn().mockReturnValue(of([{ id: 1 }, { id: 2 }])),
      getAllThemes: jest.fn().mockReturnValue(of([{ id: 1, name: 'Theme 1' }, { id: 2, name: 'Theme 2' }])),
      subscription: jest.fn().mockReturnValue(of({}))
    };
    sessionServiceMock = {
      user: { id: 1 },
      isUserLoggedIn: jest.fn().mockReturnValue(true)
    };
    routerMock = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [ThemesComponent],
      providers: [
        { provide: ThemeService, useValue: themeServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ThemesComponent);
    component = fixture.componentInstance;
  });

  it('Should create', () => {
    expect(component).toBeTruthy()
  });

  it('should load themes on init if user is logged in', () => {
    component.ngOnInit();

    expect(sessionServiceMock.user).toBeDefined();
    expect(themeServiceMock.getThemesById).toHaveBeenCalled();
    expect(themeServiceMock.getAllThemes).toHaveBeenCalled();
    expect(component.subscribedThemes).toEqual([1, 2]);
  });

  it('should navigate to login if user is not logged in', () => {
    sessionServiceMock.user = null;
    component.ngOnInit();

    expect(routerMock.navigate).toHaveBeenCalledWith(['/auth/login']);
  });

  it('Should subscribe to a theme', () => {
    component.userId = 1;
    component.subscribe(1);

    expect(component.subscribedThemes).toContain(1);
    expect(themeServiceMock.subscription).toHaveBeenCalledWith(1,1)
  });

  it('should handle errors on subscription', () => {
    themeServiceMock.subscription.mockReturnValue(throwError(() => new Error('Subscription failed')));
    component.userId = 1;
    component.subscribe(1);

    expect(themeServiceMock.subscription).toHaveBeenCalledWith(1, 1);
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });

  it('should return true if the user is subscribed to a theme', () => {
    component.subscribedThemes = [1, 2];
    expect(component.isSubscribed(1)).toBe(true);
    expect(component.isSubscribed(3)).toBe(false);
  });

  it('should navigate to login when subscribe is called and user is not logged in', () => {
    sessionServiceMock.isUserLoggedIn.mockReturnValue(false);

    component.subscribe(1);

    expect(routerMock.navigate).toHaveBeenCalledWith(['/auth/login']);
  });

})
