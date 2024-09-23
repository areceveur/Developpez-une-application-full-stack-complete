import { NgModule } from '@angular/core';
import { MatLegacyButtonModule as MatButtonModule } from '@angular/material/legacy-button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import {MatLegacyCardModule as MatCardModule} from "@angular/material/legacy-card";
import {AuthModule} from "./pages/auth/auth.module";
import {ArticlesModule} from "./pages/articles/articles.module";
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import {FormComponent} from "./pages/articles/components/form/form.component";

@NgModule({ declarations: [AppComponent, HomeComponent],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatCardModule,
        AuthModule,
        ArticlesModule], providers: [
        { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule {}
