import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { AssuntosComponent } from './assuntos.component';
import { AssuntoService } from '../../core/services/assunto.service';

describe('AssuntosComponent', () => {
  let component: AssuntosComponent;
  let fixture: ComponentFixture<AssuntosComponent>;
  let serviceSpy: jasmine.SpyObj<AssuntoService>;

  beforeEach(() => {
    serviceSpy = jasmine.createSpyObj('AssuntoService', ['findAll', 'create', 'update', 'delete']);
    serviceSpy.findAll.and.returnValue(of([{ codAs: 1, descricao: 'Drama' }]));
    serviceSpy.create.and.returnValue(of({ codAs: 2, descricao: 'Romance' }));
    serviceSpy.delete.and.returnValue(of(undefined));

    TestBed.configureTestingModule({
      imports: [FormsModule],
      declarations: [AssuntosComponent],
      providers: [{ provide: AssuntoService, useValue: serviceSpy }]
    });

    fixture = TestBed.createComponent(AssuntosComponent);
    component = fixture.componentInstance;
  });

  it('deve carregar os assuntos na inicialização', () => {
    fixture.detectChanges();
    expect(serviceSpy.findAll).toHaveBeenCalled();
    expect(component.assuntos.length).toBe(1);
    expect(component.assuntos[0].descricao).toBe('Drama');
  });

  it('deve criar um novo assunto e recarregar a lista', () => {
    fixture.detectChanges();
    component.form = { descricao: 'Romance' };

    component.save();

    expect(serviceSpy.create).toHaveBeenCalledWith({ descricao: 'Romance' });
    expect(serviceSpy.findAll).toHaveBeenCalledTimes(2);
    expect(component.editingId).toBeNull();
  });

  it('deve atualizar quando estiver editando', () => {
    serviceSpy.update.and.returnValue(of({ codAs: 1, descricao: 'Drama Editado' }));
    fixture.detectChanges();

    component.edit({ codAs: 1, descricao: 'Drama' });
    expect(component.editingId).toBe(1);

    component.save();
    expect(serviceSpy.update).toHaveBeenCalledWith(1, { codAs: 1, descricao: 'Drama' });
  });

  it('deve exibir a mensagem do backend ao falhar a exclusão', () => {
    serviceSpy.delete.and.returnValue(throwError({ error: { error: 'Assunto vinculado a livros.' } }));
    spyOn(window, 'confirm').and.returnValue(true);
    fixture.detectChanges();

    component.delete(1);

    expect(component.error).toBe('Assunto vinculado a livros.');
  });
});
