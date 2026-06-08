import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Autor } from '../../core/models/autor.model';
import { AutorService } from '../../core/services/autor.service';

@Component({
  selector: 'app-autores',
  templateUrl: './autores.component.html'
})
export class AutoresComponent implements OnInit {
  @ViewChild('f', { static: false }) formRef: NgForm;
  autores: Autor[] = [];
  form: Autor = { nome: '' };
  editingId: number | null = null;
  loading = false;
  error = '';

  constructor(private autorService: AutorService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.autorService.findAll().subscribe(
      data => this.autores = data,
      () => this.error = 'Erro ao carregar autores.'
    );
  }

  save() {
    this.loading = true;
    const op = this.editingId
      ? this.autorService.update(this.editingId, this.form)
      : this.autorService.create(this.form);

    op.subscribe(
      () => { this.reset(); this.load(); },
      () => { this.error = 'Erro ao salvar autor.'; this.loading = false; }
    );
  }

  edit(autor: Autor) {
    this.editingId = autor.codAu;
    this.form = { ...autor };
  }

  delete(id: number) {
    if (!confirm('Confirmar exclusão?')) { return; }
    this.autorService.delete(id).subscribe(
      () => this.load(),
      () => this.error = 'Erro ao excluir autor.'
    );
  }

  reset() {
    this.form = { nome: '' };
    this.editingId = null;
    this.loading = false;
    this.error = '';
    if (this.formRef) {
      this.formRef.resetForm();
    }
  }
}
