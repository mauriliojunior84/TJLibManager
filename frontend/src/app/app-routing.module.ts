import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LivrosComponent } from './features/livros/livros.component';
import { AutoresComponent } from './features/autores/autores.component';
import { AssuntosComponent } from './features/assuntos/assuntos.component';
import { RelatorioComponent } from './features/relatorio/relatorio.component';

const routes: Routes = [
  { path: '', redirectTo: 'livros', pathMatch: 'full' },
  { path: 'livros', component: LivrosComponent },
  { path: 'autores', component: AutoresComponent },
  { path: 'assuntos', component: AssuntosComponent },
  { path: 'relatorio', component: RelatorioComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
