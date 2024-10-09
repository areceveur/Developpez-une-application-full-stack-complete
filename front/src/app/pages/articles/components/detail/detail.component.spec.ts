import {ActivatedRoute, Router} from "@angular/router";
import {ComponentFixture, fakeAsync, TestBed} from "@angular/core/testing";
import {DetailComponent} from "./detail.component";
import {ReactiveFormsModule} from "@angular/forms";
import {ArticlesService} from "../../services/articles.service";
import {Article} from "../../interfaces/article.interface";
import {of, throwError} from "rxjs";

describe('DetailsComponent', () => {

  let mockRouter: any;
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let articleServiceMock: any;

  beforeEach(async () => {
    articleServiceMock = {
      detail: jest.fn(),
      getComments: jest.fn(),
      addComments: jest.fn(),
    };

    mockRouter = {
      snapshot: {
        paramMap: {
          get: jest.fn().mockReturnValue(1)
        }
      }
    };

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule
      ],
      declarations: [DetailComponent],
      providers: [
        {provide: ArticlesService, useValue: articleServiceMock},
        {provide: ActivatedRoute, useValue: mockRouter}]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
  })

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should take back the user', () => {
    jest.spyOn(window.history, 'back');
    component.back();
    expect(window.history.back).toHaveBeenCalled();
  });

  it('Should fetch the article', () => {
    const mockArticle: Article = {id: 1, titre: "Titre", contenu: "Contenu"} as any;
    const mockComments: Comment[] = [{id: 1, content: "Content", articleId: 1}] as any;

    articleServiceMock.detail.mockReturnValue(of(mockArticle));
    articleServiceMock.getComments.mockReturnValue(of(mockComments));

    component.ngOnInit();

    expect(articleServiceMock.detail).toHaveBeenCalledWith(1);
    expect(articleServiceMock.getComments).toHaveBeenCalledWith(1);
    expect(component.article).toEqual(mockArticle);
    expect(component.comments).toEqual(mockComments);
  });

  it('should handle error when fetching article', () => {
    articleServiceMock.detail.mockReturnValue(throwError(() => new Error('Erreur')));

    component.fetchArticle();

    expect(component.errorMessage).toBe("Erreur lors du chargement de l'article");
    expect(component.isLoading).toBe(false);
  });

  it('Should submit the comments', () => {
    component.commentForm.controls['content'].setValue('Test comment');
    const mockComment = { id: 2, content: 'Test comment', articleId: 1 } as any;
    const mockComments: Comment[] = [{ id: 1, content: 'Test comment', articleId: 1 }] as any;

    articleServiceMock.addComments.mockReturnValue(of(mockComment));
    articleServiceMock.getComments.mockReturnValue(of(mockComments));

    component.submitComment();

    expect(articleServiceMock.addComments).toHaveBeenCalledWith(1, { content: 'Test comment' });
    expect(articleServiceMock.getComments).toHaveBeenCalledWith(1);
    expect(component.commentForm.get('content')?.value).toBe('');
  })
})
