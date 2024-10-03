import {RegisterComponent} from "./register.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {expect} from '@jest/globals'
import {of, throwError} from "rxjs";
import {User} from "../../../../interfaces/user.interface";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: any;
  let routerMock: any;

  beforeEach(async() => {

    authServiceMock = {
      register: jest.fn().mockReturnValue(of(true))
    };

    routerMock = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        MatCardHeader,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        BrowserAnimationsModule
      ]
    })
      .compileComponents();
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  })

  it('should create a session', () => {
    expect(component).toBeTruthy();
  })

  it('should register correctly', () => {
    component.form.controls['email'].setValue('user@test.com');
    component.form.controls['username'].setValue('User Test');
    component.form.controls['password'].setValue('UserTest1234');

    const userResponse: User = {
      id: 3,
      email: "user@test.com",
      username: "User Test",
      created_at: new Date(),
      updated_at: new Date()
    };

    authServiceMock.me = jest.fn().mockReturnValue(of(userResponse));

    component.submit();

    expect(authServiceMock.register).toHaveBeenCalled();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/articles']);
  });

  it('Should send an error when bad register', () => {
    const user = {
      email: "user.test.com",
      username: "User Test",
      password: "fakepassword"
    };
    authServiceMock.register.mockReturnValue(throwError(() => new Error('Registration error')));

    component.form.setValue(user);

    component.submit();

    expect(authServiceMock.register).toHaveBeenCalledWith(user);
    expect(component.onError).toBe(true);
  })

  it('should call window.history.back when back is called', () => {
    const originalHistoryBack = window.history.back;
    window.history.back = jest.fn();
    component.back();
    expect(window.history.back).toHaveBeenCalled();
    window.history.back = originalHistoryBack;
  });
})
