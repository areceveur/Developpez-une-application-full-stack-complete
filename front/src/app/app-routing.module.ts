import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/auth/components/login/login.component";
import {RegisterComponent} from "./pages/auth/components/register/register.component";
import {MeComponent} from "./pages/auth/components/me/me.component";
import {AuthGuard} from  "./guards/auth.gard";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'auth/me', component: MeComponent },
  {
    path: 'articles',
    loadChildren: () => import('./pages/articles/articles.module').then(m => m.ArticlesModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'themes',
    loadChildren: () => import('./pages/themes/themes.module').then(m => m.ThemesModule),
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
