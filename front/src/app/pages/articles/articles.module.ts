import {NgModule} from "@angular/core";
import {ArticlesComponent} from "./components/list/articles.component";
import {ArticlesRoutingModule} from "./articles-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {AsyncPipe, DatePipe, NgForOf, NgIf, SlicePipe} from "@angular/common";
import {MatInputModule} from "@angular/material/input";
import {FormComponent} from "./components/form/form.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSelectModule} from "@angular/material/select";
import {DetailComponent} from "./components/detail/detail.component";

@NgModule({
  declarations: [
    ArticlesComponent,
    FormComponent,
    DetailComponent
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
        MatSelectModule,
        SlicePipe,
        DatePipe
    ]
})

export class ArticlesModule { }
