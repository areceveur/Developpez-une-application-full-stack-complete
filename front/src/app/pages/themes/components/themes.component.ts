import {Component, OnInit} from "@angular/core";
import {catchError, Observable, of, tap} from "rxjs";
import {ThemesResponse} from "../interfaces/themesResponse.interface";
import {ThemeService} from "../services/theme.service";
import {SessionService} from "../../../services/session.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {
  public themes$!: Observable<ThemesResponse>;
  public userId!: number;
  public subscribedThemes: number[] = [];

  constructor(
    private themeService: ThemeService,
    private sessionService: SessionService,
    private router: Router,
  ) {}

  ngOnInit() {
    if (this.sessionService.user) {
      this.userId = this.sessionService.user.id;

      this.themeService.getThemesById().subscribe((themes) => {
        this.subscribedThemes = themes.map(theme => theme.id);
      });

      this.themes$ = this.themeService.getAllThemes();
      this.themes$.subscribe((themes) => {
      })
    } else {
      console.error("User is not logged in.");
      this.router.navigate(['/login'])
    }
  }

  public subscribe(themeId: number): void {
    if (!this.sessionService.isUserLoggedIn()) {
      console.error("User is not logged in.");
      this.router.navigate(['/login']);
      return;
    }
    if (this.userId) {
      this.themeService.subscription(themeId, this.userId).pipe(
        tap(() => {
          this.subscribedThemes.push(themeId);
        }),
        catchError((error) => {
          console.error("Subscription failed", error);
          return of(null);
        })
      ).subscribe();
    } else {
      console.error("UserId is undefined")
    }
  }

  public isSubscribed(theme: number): boolean {
    return this.subscribedThemes.includes(theme);
  }
}
