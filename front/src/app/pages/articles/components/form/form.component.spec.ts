import {FormComponent} from "./form.component";
import {ComponentFixture, TestBed} from "@angular/core/testing";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatCardModule} from "@angular/material/card";
import {SessionService} from "../../../../services/session.service";
import {of} from "rxjs";
import {ThemeService} from "../../../themes/services/theme.service";
import {ArticlesService} from "../../services/articles.service";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSelectModule} from "@angular/material/select";
import {Article} from "../../interfaces/article.interface";

describe('FormComponent', () => {

  let fixture: ComponentFixture<FormComponent>;
  let component: FormComponent;
  let mockRouter: any;
  let sessionServiceMock: {user: {id: 1}};
  let matSnackBarMock = { open: jest.fn() };
  let articlesServiceMock = { create: jest.fn(() => of({message: "Article créé !"})) };
  let themeServiceMock = { getAllThemes: jest.fn(() => of([{ id: 1, name: 'Développement web' }])) };


  beforeEach(async () => {

    mockRouter = {
      navigate: jest.fn()
    };

    sessionServiceMock = {
      user: { id: 1 }
    };

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      declarations: [FormComponent],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: MatSnackBar, useValue: matSnackBarMock },
        { provide: ArticlesService, useValue: articlesServiceMock },
        { provide: ThemeService, useValue: themeServiceMock },
        FormBuilder
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  })

  it('Should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should submit the article', async () => {
    component.articleForm?.controls["titre"].setValue("titre");
    component.articleForm?.controls["contenu"].setValue("contenu");
    component.submit();

    fixture.whenStable().then(() => {
      expect(articlesServiceMock.create).toHaveBeenCalledWith(component.articleForm?.value);
    });

    expect(matSnackBarMock.open).toHaveBeenCalledWith('Article créé !', 'Fermer', { duration : 3000});
  })

  it('Should initialize the form correctly and navigate if the article owner is different', () => {
    const mockArticle: Article = { id: 1, titre: 'Test', contenu: 'Contenu', owner_id: 2, themeId: 1, created_at: new Date,  updated_at: new Date, auteur: "UserTest", themeName: "Langages de programmation"};

    jest.spyOn(mockRouter, 'navigate');

    component['initForm'](mockArticle);

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/articles']);
  });
})
