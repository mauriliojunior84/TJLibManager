import { Autor } from './autor.model';
import { Assunto } from './assunto.model';

export interface Livro {
  codl?: number;
  titulo: string;
  editora?: string;
  edicao?: number;
  anoPublicacao?: string;
  valor?: number;
  autores?: Autor[];
  assuntos?: Assunto[];
}

export interface LivroRequest {
  titulo: string;
  editora?: string;
  edicao?: number;
  anoPublicacao?: string;
  valor?: number;
  autoresIds: number[];
  assuntosIds: number[];
}
