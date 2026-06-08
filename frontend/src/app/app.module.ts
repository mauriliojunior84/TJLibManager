import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LivrosComponent } from './features/livros/livros.component';
import { AutoresComponent } from './features/autores/autores.component';
import { AssuntosComponent } from './features/assuntos/assuntos.component';

@NgModule({
  declarations: [
    AppComponent,
    LivrosComponent,
    AutoresComponent,
    AssuntosComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
