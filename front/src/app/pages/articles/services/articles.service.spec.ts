import {TestBed} from "@angular/core/testing";
import {ArticlesService} from "./articles.service";
import {HttpClientModule} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Article} from "../interfaces/article.interface";
import {Comment} from "../interfaces/comment.interface";

describe('ArticleService', () => {

  let service: ArticlesService;
  let httpMock: HttpTestingController;

  const mockArticleService: Article[] = [
    {
      id: 1,
      titre: 'Titre',
      contenu: 'Contenu',
      created_at: new Date("2024-10-05"),
      updated_at: new Date("2024-10-06"),
      owner_id: 1,
      auteur: "UserTest",
      themeId: "1",
    },
    {
      id: 2,
      titre: 'Deuxième titre',
      contenu: 'Deuxième contenu',
      created_at: new Date("2024-08-02"),
      updated_at: new Date("2024-08-04"),
      owner_id: 1,
      auteur: "UserTest",
      themeId: "2",
    }
  ];

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        HttpClientModule
      ],
    });
    service = TestBed.inject(ArticlesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('Should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Should show all the articles', () => {
    service.all().subscribe((articles: Article[]) => {
      expect(articles.length).toBe(2);
      expect(articles).toEqual(mockArticleService);

      const req = httpMock.expectOne(service['pathService']);
      expect(req.request.method).toBe('GET');

      req.flush(mockArticleService);
      })
  });

  it('Should show one article', () => {
    const articleId = '1';

    service.detail(articleId).subscribe((article: Article) => {
      expect(article).toEqual(mockArticleService[0]);
    });
    const req = httpMock.expectOne(`${service['pathService']}/${articleId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockArticleService[0]);
  });

  it('Should create an article', () => {
    service.create(mockArticleService[0]).subscribe((article) => {
      expect(article).toEqual(mockArticleService[0]);
    });
    const req = httpMock.expectOne(`${service['pathService']}/create`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockArticleService[0]);
    req.flush(mockArticleService[0]);
  });

  it('should get comments for an article', () => {
    const articleId = '123';
    const mockComments: Comment[] = [
      { id: 1, comment: 'Commentaire 1', article_id: 123, created_at: new Date, username: "UserTest" },
      { id: 2, comment: 'Commentaire 2', article_id: 123, created_at: new Date, username: "UserTest" }
    ];

    service.getComments(articleId).subscribe(comments => {
      expect(comments.length).toBe(2);
      expect(comments).toEqual(mockComments);
    });

    const req = httpMock.expectOne(`${service['pathService']}/${articleId}/comments`);
    expect(req.request.method).toBe('GET');

    req.flush(mockComments);
  });

  it('should add a comment to an article', () => {
    const articleId = '123';
    const commentData = { content: 'Nouveau commentaire' };
    const mockComment: Comment = { id: 1, comment: 'Nouveau commentaire', article_id: 123, username: "UserTest", created_at: new Date };

    service.addComments(articleId, commentData).subscribe(comment => {
      expect(comment).toEqual(mockComment);
    });

    const req = httpMock.expectOne(`${service['pathService']}/${articleId}/comments`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(commentData);

    req.flush(mockComment);
  });
})
