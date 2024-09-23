import {NgModule} from "@angular/core";
import {ArticlesComponent} from "./components/list/articles.component";
import {ArticlesRoutingModule} from "./articles-routing.module";
import {MatLegacyCardModule as MatCardModule} from "@angular/material/legacy-card";
import {MatLegacyButtonModule as MatButtonModule} from "@angular/material/legacy-button";
import {MatIconModule} from "@angular/material/icon";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {MatLegacyInputModule as MatInputModule} from "@angular/material/legacy-input";
import {FormComponent} from "./components/form/form.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatLegacySnackBarModule as MatSnackBarModule} from "@angular/material/legacy-snack-bar";
import {MatLegacySelectModule as MatSelectModule} from "@angular/material/legacy-select";

@NgModule({
  declarations: [
    ArticlesComponent,
    FormComponent
  ],
  imports: [
    ArticlesRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    AsyncPipe,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    MatSnackBarModule,
    FormsModule,
    MatSelectModule
  ]
})

export class ArticlesModule { }
