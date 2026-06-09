import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Assunto } from '../../core/models/assunto.model';
import { AssuntoService } from '../../core/services/assunto.service';

@Component({
  selector: 'app-assuntos',
  templateUrl: './assuntos.component.html'
})
export class AssuntosComponent implements OnInit {
  @ViewChild('f', { static: false }) formRef: NgForm;
  assuntos: Assunto[] = [];
  form: Assunto = { descricao: '' };
  editingId: number | null = null;
  loading = false;
  error = '';

  constructor(private assuntoService: AssuntoService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.assuntoService.findAll().subscribe(
      data => this.assuntos = data,
      () => this.error = 'Erro ao carregar assuntos.'
    );
  }

  save() {
    this.loading = true;
    const op = this.editingId
      ? this.assuntoService.update(this.editingId, this.form)
      : this.assuntoService.create(this.form);

    op.subscribe(
      () => { this.reset(); this.load(); },
      () => { this.error = 'Erro ao salvar assunto.'; this.loading = false; }
    );
  }

  edit(assunto: Assunto) {
    this.editingId = assunto.codAs;
    this.form = { ...assunto };
  }

  delete(id: number) {
    if (!confirm('Confirmar exclusão?')) { return; }
    this.assuntoService.delete(id).subscribe(
      () => this.load(),
      err => this.error = (err.error && err.error.error) || 'Erro ao excluir assunto.'
    );
  }

  reset() {
    this.form = { descricao: '' };
    this.editingId = null;
    this.loading = false;
    this.error = '';
    if (this.formRef) {
      this.formRef.resetForm();
    }
  }
}
