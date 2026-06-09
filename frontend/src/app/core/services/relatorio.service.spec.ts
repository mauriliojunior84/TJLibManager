import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RelatorioService } from './relatorio.service';
import { environment } from '../../../environments/environment';

describe('RelatorioService', () => {
  let service: RelatorioService;
  let httpMock: HttpTestingController;
  const base = `${environment.apiUrl}/relatorios`;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RelatorioService]
    });
    service = TestBed.get(RelatorioService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('deve buscar livros agrupados por autor', () => {
    const mock = [
      { autorCodigo: 1, autorNome: 'Machado de Assis', livros: [], quantidadeLivros: 0, valorTotal: 0 }
    ];

    service.livrosPorAutor().subscribe(res => {
      expect(res.length).toBe(1);
      expect(res[0].autorNome).toBe('Machado de Assis');
    });

    const req = httpMock.expectOne(`${base}/livros-por-autor`);
    expect(req.request.method).toBe('GET');
    req.flush(mock);
  });

  it('deve solicitar o PDF como blob', () => {
    service.livrosPorAutorPdf().subscribe(blob => {
      expect(blob instanceof Blob).toBeTruthy();
    });

    const req = httpMock.expectOne(`${base}/livros-por-autor/pdf`);
    expect(req.request.method).toBe('GET');
    expect(req.request.responseType).toBe('blob');
    req.flush(new Blob(['%PDF-'], { type: 'application/pdf' }));
  });
});
