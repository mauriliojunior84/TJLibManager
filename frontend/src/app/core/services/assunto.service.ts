import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Assunto } from '../models/assunto.model';

@Injectable({ providedIn: 'root' })
export class AssuntoService {
  private url = `${environment.apiUrl}/assuntos`;

  constructor(private http: HttpClient) {}

  findAll(): Observable<Assunto[]> {
    return this.http.get<Assunto[]>(this.url);
  }

  findById(id: number): Observable<Assunto> {
    return this.http.get<Assunto>(`${this.url}/${id}`);
  }

  create(assunto: Assunto): Observable<Assunto> {
    return this.http.post<Assunto>(this.url, assunto);
  }

  update(id: number, assunto: Assunto): Observable<Assunto> {
    return this.http.put<Assunto>(`${this.url}/${id}`, assunto);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
