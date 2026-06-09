package com.tjlibmanager.api.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tjlibmanager.api.exception.ResourceInUseException;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.repository.AssuntoRepository;
import com.tjlibmanager.api.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceTest {

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private AssuntoService assuntoService;

    @Test
    void deveLancarNotFoundAoExcluirAssuntoInexistente() {
        when(assuntoRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> assuntoService.delete(99))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(assuntoRepository, never()).deleteById(99);
    }

    @Test
    void deveLancarInUseAoExcluirAssuntoVinculado() {
        when(assuntoRepository.existsById(1)).thenReturn(true);
        when(livroRepository.countByAssuntos_CodAs(1)).thenReturn(3L);

        assertThatThrownBy(() -> assuntoService.delete(1))
            .isInstanceOf(ResourceInUseException.class)
            .hasMessageContaining("3 livro");

        verify(assuntoRepository, never()).deleteById(1);
    }

    @Test
    void deveExcluirAssuntoSemVinculo() {
        when(assuntoRepository.existsById(1)).thenReturn(true);
        when(livroRepository.countByAssuntos_CodAs(1)).thenReturn(0L);

        assertThatCode(() -> assuntoService.delete(1)).doesNotThrowAnyException();

        verify(assuntoRepository).deleteById(1);
    }
}
