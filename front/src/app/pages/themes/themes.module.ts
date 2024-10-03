import {NgModule} from "@angular/core";
import {ThemesComponent} from "./components/themes.component";
import {ThemesRoutingModule} from "./themes-routing.module";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [ThemesComponent],
  imports: [
    ThemesRoutingModule,
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

export class ThemesModule { }
