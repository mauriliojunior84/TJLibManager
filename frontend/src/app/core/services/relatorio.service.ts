import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { RelatorioAutor } from '../models/relatorio.model';

@Injectable({ providedIn: 'root' })
export class RelatorioService {
  private url = `${environment.apiUrl}/relatorios`;

  constructor(private http: HttpClient) {}

  livrosPorAutor(): Observable<RelatorioAutor[]> {
    return this.http.get<RelatorioAutor[]>(`${this.url}/livros-por-autor`);
  }

  livrosPorAutorPdf(): Observable<Blob> {
    return this.http.get(`${this.url}/livros-por-autor/pdf`, { responseType: 'blob' });
  }
}
