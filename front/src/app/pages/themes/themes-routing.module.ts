import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {ThemesComponent} from "./components/themes.component";

const routes: Routes = [
  { title: 'Themes', path: '', component: ThemesComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ThemesRoutingModule {}
