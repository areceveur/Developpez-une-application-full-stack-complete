import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {MeComponent} from "./components/me/me.component";
import {HomeComponent} from "../home/home.component";

const routes: Routes = [
  { title: 'Home', path: '', component: HomeComponent },
  { title: 'Login', path: 'auth/login', component: LoginComponent },
  { title: 'Register', path: 'auth/register', component: RegisterComponent },
  { title: 'Me', path: 'auth/me', component: MeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
