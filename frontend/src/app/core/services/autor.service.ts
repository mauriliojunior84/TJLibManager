import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Autor } from '../models/autor.model';

@Injectable({ providedIn: 'root' })
export class AutorService {
  private url = `${environment.apiUrl}/autores`;

  constructor(private http: HttpClient) {}

  findAll(): Observable<Autor[]> {
    return this.http.get<Autor[]>(this.url);
  }

  findById(id: number): Observable<Autor> {
    return this.http.get<Autor>(`${this.url}/${id}`);
  }

  create(autor: Autor): Observable<Autor> {
    return this.http.post<Autor>(this.url, autor);
  }

  update(id: number, autor: Autor): Observable<Autor> {
    return this.http.put<Autor>(`${this.url}/${id}`, autor);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
