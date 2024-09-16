import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {AuthRoutingModule} from "./auth-routing.module";
import {RegisterComponent} from "./components/register/register.component";

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    AuthRoutingModule,
  ]

})

export class AuthModule { }
