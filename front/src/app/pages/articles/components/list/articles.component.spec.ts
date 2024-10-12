import {ArticlesComponent} from "./articles.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {HttpClientModule} from "@angular/common/http";
import {MatIconModule} from "@angular/material/icon";
import {of, throwError} from "rxjs";
import {Article} from "../../interfaces/article.interface";
import {ArticlesService} from "../../services/articles.service";
import {MatCardModule} from "@angular/material/card";

describe('articlesComponent', () => {
  let component: ArticlesComponent;
  let fixture: ComponentFixture<ArticlesComponent>;
  let sessionServiceMock: any;
  let articlesServiceMock: any;

  const mockArticles: Article[] = [
    { id: 1, titre: 'Article 1', contenu: 'Contenu 1', created_at: new Date, updated_at: new Date, auteur: "UserTest", themeName: "Développement web", owner_id: 1, themeId: 3 },
    { id: 2, titre: 'Article 2', contenu: 'Contenu 2', created_at: new Date, updated_at: new Date, auteur: "TestUser", themeName: "Langages de programmation", owner_id: 2, themeId: 1 }
  ];

  beforeEach(async () => {
    articlesServiceMock = {
      all: jest.fn().mockReturnValue(of(mockArticles))
    };
    await TestBed.configureTestingModule({
      declarations: [ArticlesComponent],
      imports: [
        HttpClientModule,
        MatIconModule,
        MatCardModule
      ],
      providers: [{provide: ArticlesService, useValue: articlesServiceMock}]
    }).compileComponents();
    fixture = TestBed.createComponent(ArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('Should call of the articles', () => {
    expect(articlesServiceMock.all).toHaveBeenCalled();
    expect(component.articles).toEqual(mockArticles);
    expect(component.isLoading).toBe(false);
  });

  it('should handle errors and set errorMessage when fetchArticle fails', () => {
    articlesServiceMock.all.mockReturnValue(throwError(() => new Error('Erreur')));

    component.ngOnInit();

    expect(component.errorMessage).toBe("Erreur lors du chargement de la page");
    expect(component.isLoading).toBe(false);
  });

  it('should toggle the sort order and call sortArticles', () => {

    expect(component.sortOrder).toBe('asc');

    component.toggleSortOrder();

    expect(component.sortOrder).toBe('desc');

    const sortDesc = [...mockArticles].sort((a,b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime());
    expect(component.articles).toEqual(sortDesc);

    component.toggleSortOrder();
    expect(component.sortOrder).toBe('asc');

    const sortAsc = [...mockArticles].sort((a,b) => new Date(a.created_at).getTime() - new Date(b.created_at).getTime());
    expect(component.articles).toEqual(sortAsc);
  });

})
