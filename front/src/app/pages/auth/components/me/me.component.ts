import {Component} from "@angular/core";
import {User} from "../../../../interfaces/user.interface";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: "app-me",
  templateUrl: "./me.component.html",
  styleUrls: ["./me.component.scss"]
})
export class MeComponent {
  public user: User | undefined;

  constructor(private authService: AuthService,
              private router: Router) {}

  public ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => this.user = user)
  }

  public back(): void {
    window.history.back();
  }
}
