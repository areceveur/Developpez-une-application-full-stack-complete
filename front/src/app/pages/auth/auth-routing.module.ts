import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {MeComponent} from "./components/me/me.component";

const routes: Routes = [
  { title: 'Login', path: 'auth/login', component: LoginComponent },
  { title: 'Register', path: 'auth/register', component: RegisterComponent },
  { title: 'Me', path: 'auth/me', component: MeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
