import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Article} from "../../interfaces/article.interface";
import {SessionService} from "../../../../services/session.service";
import {ArticleResponse} from "../../interfaces/articleResponse.interface";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ArticlesService} from "../../services/articles.service";

@Component({
  selector: "app-form",
  templateUrl: "./form.component.html",
  styleUrls: ['./form.component.scss']
})

export class FormComponent implements OnInit {
  public articleForm: FormGroup | undefined;

  constructor(
              private router: Router,
              private sessionService: SessionService,
              private fb: FormBuilder,
              private matSnackBar: MatSnackBar,
              private articlesService: ArticlesService) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  public submit(): void {
    const formData = new FormData();
    formData.append('titre', this.articleForm!.get("titre")?.value);
    formData.append('contenu', this.articleForm!.get("contenu")?.value);
    this.articlesService.create(formData).subscribe((response) => {
      this.exitPage(response)
    });
  }

  private initForm(article?:Article): void {
    if((article !== undefined) && (article?.owner_id !== this.sessionService.user!.id)) {
      this.router.navigate(['/articles'])
    }
    this.articleForm = this.fb.group({
      titre: [article ? article.titre : '', [Validators.required]],
      contenu: [article ? article.contenu : '', [Validators.required]]
    });
  }

  private exitPage(articleResponse: ArticleResponse): void {
    this.matSnackBar.open(articleResponse.message, "Close", { duration: 3000 });
    this.router.navigate(['/articles']);
  }

}
