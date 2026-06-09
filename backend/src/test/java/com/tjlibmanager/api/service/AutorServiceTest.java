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
import com.tjlibmanager.api.repository.AutorRepository;
import com.tjlibmanager.api.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private AutorService autorService;

    @Test
    void deveLancarNotFoundAoExcluirAutorInexistente() {
        when(autorRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> autorService.delete(99))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(autorRepository, never()).deleteById(99);
    }

    @Test
    void deveLancarInUseAoExcluirAutorVinculado() {
        when(autorRepository.existsById(1)).thenReturn(true);
        when(livroRepository.countByAutores_CodAu(1)).thenReturn(2L);

        assertThatThrownBy(() -> autorService.delete(1))
            .isInstanceOf(ResourceInUseException.class)
            .hasMessageContaining("2 livro");

        verify(autorRepository, never()).deleteById(1);
    }

    @Test
    void deveExcluirAutorSemVinculo() {
        when(autorRepository.existsById(1)).thenReturn(true);
        when(livroRepository.countByAutores_CodAu(1)).thenReturn(0L);

        assertThatCode(() -> autorService.delete(1)).doesNotThrowAnyException();

        verify(autorRepository).deleteById(1);
    }
}
