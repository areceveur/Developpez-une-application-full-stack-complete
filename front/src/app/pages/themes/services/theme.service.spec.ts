import {ThemeService} from "./theme.service";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {TestBed} from "@angular/core/testing";
import {Theme} from "../interfaces/theme.interface";

describe('ThemeService', () => {
  let service: ThemeService;
  let httpMock: HttpTestingController;

  const mockThemeService: Theme[] = [
    {id: 1, name: "Développement web", description: "Description", subscribedUsers: []},
    {id: 2, name: "Langages de programmation", description: "Description", subscribedUsers: []}
    ];


  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ThemeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('Should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Should show all the themes', () => {
    service.getAllThemes().subscribe((themes: Theme[]) => {
      expect(themes.length).toBe(2);
      expect(themes).toEqual(mockThemeService);

      const req = httpMock.expectOne(service['apiUrl']);
      expect(req.request.method).toBe('GET');

      req.flush(mockThemeService);
    })
  });

  it('Should subscribe to a theme', () => {
    const themeId = 1;
    const userId = 1;

    service.subscription(themeId, userId).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${service['apiUrl']}/subscribe/${themeId}`);
    expect(req.request.method).toBe('POST');

    req.flush(null);
  });

  it('Should unsubscribe from a theme', () => {
    const themeId = 1;
    const userId = 1;

    service.unSubscribe(themeId, userId).subscribe((response) => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${service['apiUrl']}/unsubscribe/${themeId}`);
    expect(req.request.method).toBe('DELETE');

    req.flush(null);
  });

  it('Should get themes by user ID', () => {
    service.getThemesById().subscribe((themes: Theme[]) => {
      expect(themes.length).toBe(2);
      expect(themes).toEqual(mockThemeService);
    });

    const req = httpMock.expectOne('api/auth/me');
    expect(req.request.method).toBe('GET');

    req.flush({ themes: mockThemeService });
  });
})
