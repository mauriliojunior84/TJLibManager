import { Component, OnInit } from '@angular/core';
import { RelatorioAutor } from '../../core/models/relatorio.model';
import { RelatorioService } from '../../core/services/relatorio.service';

@Component({
  selector: 'app-relatorio',
  templateUrl: './relatorio.component.html'
})
export class RelatorioComponent implements OnInit {
  autores: RelatorioAutor[] = [];
  loading = false;
  gerandoPdf = false;
  error = '';

  constructor(private relatorioService: RelatorioService) {}

  ngOnInit() {
    this.load();
  }

  load() {
    this.loading = true;
    this.relatorioService.livrosPorAutor().subscribe(
      data => { this.autores = data; this.loading = false; },
      () => { this.error = 'Erro ao carregar o relatório.'; this.loading = false; }
    );
  }

  exportarPdf() {
    this.gerandoPdf = true;
    this.relatorioService.livrosPorAutorPdf().subscribe(
      blob => {
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
        this.gerandoPdf = false;
      },
      () => { this.error = 'Erro ao gerar o PDF.'; this.gerandoPdf = false; }
    );
  }
}
