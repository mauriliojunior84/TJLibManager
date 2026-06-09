package com.tjlibmanager.api.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.fixture.LivroDTOTemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

class ValorHandlerTest {

    private final ValorHandler handler = new ValorHandler();

    @BeforeAll
    static void carregarTemplates() {
        FixtureFactoryLoader.loadTemplates("com.tjlibmanager.api.fixture");
    }

    @Test
    void deveLancarQuandoValorNulo() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.SEM_VALOR);

        assertThatThrownBy(() -> handler.validar(dto))
            .isInstanceOf(ValidacaoException.class)
            .hasMessageContaining("obrigatório");
    }

    @Test
    void deveLancarQuandoValorNegativo() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALOR_NEGATIVO);

        assertThatThrownBy(() -> handler.validar(dto))
            .isInstanceOf(ValidacaoException.class)
            .hasMessageContaining("negativo");
    }

    @Test
    void deveAceitarValorZeroOuPositivo() {
        LivroDTO positivo = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);

        LivroDTO zero = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        zero.setValor(BigDecimal.ZERO);

        assertThatCode(() -> handler.validar(positivo)).doesNotThrowAnyException();
        assertThatCode(() -> handler.validar(zero)).doesNotThrowAnyException();
    }
}
