package com.tjlibmanager.api.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutoresExistemHandlerTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutoresExistemHandler handler;

    @Test
    void deveLancarQuandoAutorNaoExiste() {
        LivroDTO dto = new LivroDTO();
        dto.setAutoresIds(new java.util.HashSet<>(Arrays.asList(1, 2)));
        when(autorRepository.existsById(1)).thenReturn(true);
        when(autorRepository.existsById(2)).thenReturn(false);

        assertThatThrownBy(() -> handler.validar(dto))
            .isInstanceOf(ValidacaoException.class)
            .hasMessageContaining("Autor não encontrado: 2");
    }

    @Test
    void deveAceitarQuandoTodosExistem() {
        LivroDTO dto = new LivroDTO();
        dto.setAutoresIds(new java.util.HashSet<>(Arrays.asList(1)));
        when(autorRepository.existsById(1)).thenReturn(true);

        assertThatCode(() -> handler.validar(dto)).doesNotThrowAnyException();
    }

    @Test
    void deveIgnorarQuandoSemAutores() {
        LivroDTO dto = new LivroDTO();
        dto.setAutoresIds(null);

        assertThatCode(() -> handler.validar(dto)).doesNotThrowAnyException();
    }
}
