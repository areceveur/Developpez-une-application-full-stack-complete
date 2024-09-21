import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {AuthRoutingModule} from "./auth-routing.module";
import {RegisterComponent} from "./components/register/register.component";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {CommonModule} from "@angular/common";
import {MatIconModule} from "@angular/material/icon";
import {MeComponent} from "./components/me/me.component";

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    MeComponent
  ],
  imports: [
    AuthRoutingModule,
    MatCardModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    CommonModule,
    FormsModule,
    MatIconModule,
  ]

})

export class AuthModule { }
