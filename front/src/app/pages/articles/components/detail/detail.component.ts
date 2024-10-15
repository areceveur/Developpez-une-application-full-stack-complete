import {Component, OnInit, OnDestroy} from "@angular/core";
import {ArticlesService} from "../../services/articles.service";
import {ActivatedRoute} from "@angular/router";
import {Article} from "../../interfaces/article.interface";
import {catchError, of, Subject, takeUntil} from "rxjs";
import {Comment} from "../../interfaces/comment.interface";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss']
})
export class DetailComponent implements OnInit, OnDestroy {

  public articleId: string;
  public article?: Article;
  public isLoading = false;
  public errorMessage: string | null = null;
  public comments: Comment[] = [];
  private destroy$ = new Subject<boolean>();

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService,
    private fb: FormBuilder,
  ) {
    this.articleId = this.route.snapshot.paramMap.get('id')!;
  }

  public ngOnInit(): void {
    this.fetchArticle();
    this.fetchComments();
  }

  public back(): void {
    window.history.back();
  }

  public fetchArticle(): void {
    this.isLoading = true;
    this.errorMessage = null;

    this.articleService
      .detail(this.articleId).pipe(
        catchError(error => {
          this.errorMessage = "Erreur lors du chargement de l'article";
          this.isLoading = false;
          return of(null);
        }),
      takeUntil(this.destroy$)
    ).subscribe((article: Article | null) => {
      this.isLoading = false;
      if (article) {
        this.article = article;
        console.log(this.article);
      }
    });
  }

  public fetchComments(): void {
    this.articleService.getComments(this.articleId).pipe(takeUntil(this.destroy$))
      .subscribe((comments: Comment[]) => {
      this.comments = comments;
    })
  }

  public commentForm: FormGroup = this.fb.group({
    content: ['', Validators.required]
    });

  public submitComment(): void {
    if (this.commentForm.valid) {
      const commentData = {
        content: this.commentForm.get('content')?.value
      };

      this.articleService.addComments(this.articleId, commentData).pipe(takeUntil(this.destroy$))
        .subscribe(() => {
        this.fetchComments();
        this.commentForm.reset({content: ''});
      })
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    this.destroy$.complete();
  }
}

