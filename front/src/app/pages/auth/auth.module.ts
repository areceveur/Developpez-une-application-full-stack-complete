import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {AuthRoutingModule} from "./auth-routing.module";
import {RegisterComponent} from "./components/register/register.component";
import {MatLegacyCardModule as MatCardModule} from "@angular/material/legacy-card";
import {MatLegacyButtonModule as MatButtonModule} from "@angular/material/legacy-button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatLegacyFormFieldModule as MatFormFieldModule} from "@angular/material/legacy-form-field";
import {MatLegacyInputModule as MatInputModule} from "@angular/material/legacy-input";
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
