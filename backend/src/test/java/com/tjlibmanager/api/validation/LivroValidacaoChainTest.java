package com.tjlibmanager.api.validation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.inOrder;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;

class LivroValidacaoChainTest {

    @Test
    void deveExecutarHandlersNaOrdemEInterromperNoPrimeiroErro() {
        LivroValidationHandler primeiro = Mockito.mock(LivroValidationHandler.class);
        LivroValidationHandler segundo = Mockito.mock(LivroValidationHandler.class);
        LivroDTO dto = new LivroDTO();

        Mockito.doThrow(new ValidacaoException("falhou")).when(segundo).validar(dto);

        LivroValidacaoChain chain = new LivroValidacaoChain(Arrays.asList(primeiro, segundo));

        assertThatThrownBy(() -> chain.validar(dto))
            .isInstanceOf(ValidacaoException.class)
            .hasMessage("falhou");

        InOrder ordem = inOrder(primeiro, segundo);
        ordem.verify(primeiro).validar(dto);
        ordem.verify(segundo).validar(dto);
    }

    @Test
    void naoDeveChamarHandlerSeguinteAposFalha() {
        LivroValidationHandler primeiro = Mockito.mock(LivroValidationHandler.class);
        LivroValidationHandler segundo = Mockito.mock(LivroValidationHandler.class);
        LivroDTO dto = new LivroDTO();

        Mockito.doThrow(new ValidacaoException("falhou")).when(primeiro).validar(dto);

        LivroValidacaoChain chain = new LivroValidacaoChain(Arrays.asList(primeiro, segundo));

        assertThatThrownBy(() -> chain.validar(dto)).isInstanceOf(ValidacaoException.class);
        Mockito.verify(segundo, Mockito.never()).validar(dto);
    }
}
