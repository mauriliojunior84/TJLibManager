import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Livro, LivroRequest } from '../models/livro.model';

@Injectable({ providedIn: 'root' })
export class LivroService {
  private url = `${environment.apiUrl}/livros`;

  constructor(private http: HttpClient) {}

  findAll(): Observable<Livro[]> {
    return this.http.get<Livro[]>(this.url);
  }

  findById(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.url}/${id}`);
  }

  create(livro: LivroRequest): Observable<Livro> {
    return this.http.post<Livro>(this.url, livro);
  }

  update(id: number, livro: LivroRequest): Observable<Livro> {
    return this.http.put<Livro>(`${this.url}/${id}`, livro);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
