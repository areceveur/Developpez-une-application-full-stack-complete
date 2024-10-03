import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {expect} from "@jest/globals";
import {Router} from "@angular/router";

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let routerMock: any;

  beforeEach(async () => {
    routerMock = {
      navigate: jest.fn()
    }

    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      providers: [
        { provide: Router, useValue: routerMock}
      ],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return the login path', () => {
    component.login();
    expect(routerMock.navigate).toHaveBeenCalledWith(['auth/login']);
  });

  it('should return the register path', () => {
    component.register();
    expect(routerMock.navigate).toHaveBeenCalledWith(['auth/register']);
  })
});
