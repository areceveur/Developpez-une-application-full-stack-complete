import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Article} from "../../interfaces/article.interface";
import {SessionService} from "../../../../services/session.service";
import {ArticleResponse} from "../../interfaces/articleResponse.interface";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ArticlesService} from "../../services/articles.service";
import {Theme} from "../../../themes/interfaces/theme.interface";
import {ThemeService} from "../../../themes/services/theme.service";

@Component({
  selector: "app-form",
  templateUrl: "./form.component.html",
  styleUrls: ['./form.component.scss']
})

export class FormComponent implements OnInit {
  public articleForm: FormGroup | undefined;
  public theme: Theme[] = [];

  constructor(
              private router: Router,
              private sessionService: SessionService,
              private fb: FormBuilder,
              private matSnackBar: MatSnackBar,
              private articlesService: ArticlesService,
              private themeService: ThemeService) {
  }

  ngOnInit(): void {
    this.initForm();
    this.themeService.getAllThemes().subscribe((themes) => {
      this.theme = themes;
    })
  }

  public submit(): void {
    const articleData = {
      titre: this.articleForm!.get("titre")?.value,
      contenu: this.articleForm!.get("contenu")?.value,
      themeId: this.articleForm!.get("themeId")?.value,
    };

    this.articlesService.create(articleData).subscribe((response) => {
      this.exitPage(response)
    });
  }

  private initForm(article?:Article): void {
    if((article !== undefined) && (article?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/articles'])
    }
    this.articleForm = this.fb.group({
      titre: [article ? article.titre : '', [Validators.required]],
      contenu: [article ? article.contenu : '', [Validators.required]],
      themeId: [article ? article.themeId: '', [Validators.required]]
    });
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.message, "Fermer", { duration: 3000 });
    this.router.navigate(['/articles']);
  }

}
