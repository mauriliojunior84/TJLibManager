import { Component, OnInit } from '@angular/core';
import { Livro, LivroRequest } from '../../core/models/livro.model';
import { Autor } from '../../core/models/autor.model';
import { Assunto } from '../../core/models/assunto.model';
import { LivroService } from '../../core/services/livro.service';
import { AutorService } from '../../core/services/autor.service';
import { AssuntoService } from '../../core/services/assunto.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-livros',
  templateUrl: './livros.component.html'
})
export class LivrosComponent implements OnInit {
  livros: Livro[] = [];
  todosAutores: Autor[] = [];
  todosAssuntos: Assunto[] = [];

  form: LivroRequest = { titulo: '', autoresIds: [], assuntosIds: [] };
  editingId: number | null = null;
  loading = false;
  error = '';

  constructor(
    private livroService: LivroService,
    private autorService: AutorService,
    private assuntoService: AssuntoService
  ) {}

  ngOnInit() {
    forkJoin([
      this.autorService.findAll(),
      this.assuntoService.findAll()
    ]).subscribe(([autores, assuntos]) => {
      this.todosAutores = autores;
      this.todosAssuntos = assuntos;
      this.load();
    });
  }

  load() {
    this.livroService.findAll().subscribe(
      data => this.livros = data,
      () => this.error = 'Erro ao carregar livros.'
    );
  }

  save() {
    this.loading = true;
    const op = this.editingId
      ? this.livroService.update(this.editingId, this.form)
      : this.livroService.create(this.form);

    op.subscribe(
      () => { this.reset(); this.load(); },
      () => { this.error = 'Erro ao salvar livro.'; this.loading = false; }
    );
  }

  edit(livro: Livro) {
    this.editingId = livro.codl;
    this.form = {
      titulo: livro.titulo,
      editora: livro.editora,
      edicao: livro.edicao,
      anoPublicacao: livro.anoPublicacao,
      autoresIds: (livro.autores || []).map(a => a.codAu),
      assuntosIds: (livro.assuntos || []).map(a => a.codAs)
    };
  }

  delete(id: number) {
    if (!confirm('Confirmar exclusão?')) { return; }
    this.livroService.delete(id).subscribe(
      () => this.load(),
      () => this.error = 'Erro ao excluir livro.'
    );
  }

  toggleAutor(id: number) {
    const idx = this.form.autoresIds.indexOf(id);
    idx >= 0 ? this.form.autoresIds.splice(idx, 1) : this.form.autoresIds.push(id);
  }

  toggleAssunto(id: number) {
    const idx = this.form.assuntosIds.indexOf(id);
    idx >= 0 ? this.form.assuntosIds.splice(idx, 1) : this.form.assuntosIds.push(id);
  }

  isAutorSelected(id: number): boolean {
    return this.form.autoresIds.includes(id);
  }

  isAssuntoSelected(id: number): boolean {
    return this.form.assuntosIds.includes(id);
  }

  nomeAutores(livro: Livro): string {
    return (livro.autores || []).map(a => a.nome).join(', ') || '—';
  }

  nomeAssuntos(livro: Livro): string {
    return (livro.assuntos || []).map(a => a.descricao).join(', ') || '—';
  }

  reset() {
    this.form = { titulo: '', autoresIds: [], assuntosIds: [] };
    this.editingId = null;
    this.loading = false;
    this.error = '';
  }
}
