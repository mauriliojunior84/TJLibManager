package com.tjlibmanager.api.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Year;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.fixture.LivroDTOTemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

class AnoPublicacaoHandlerTest {

    private final AnoPublicacaoHandler handler = new AnoPublicacaoHandler();

    @BeforeAll
    static void carregarTemplates() {
        FixtureFactoryLoader.loadTemplates("com.tjlibmanager.api.fixture");
    }

    @Test
    void deveLancarQuandoAnoFuturo() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.ANO_FUTURO);

        assertThatThrownBy(() -> handler.validar(dto))
            .isInstanceOf(ValidacaoException.class)
            .hasMessageContaining("futuro");
    }

    @Test
    void deveAceitarAnoAtualOuPassado() {
        LivroDTO atual = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        atual.setAnoPublicacao(String.valueOf(Year.now().getValue()));

        LivroDTO passado = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);

        assertThatCode(() -> handler.validar(atual)).doesNotThrowAnyException();
        assertThatCode(() -> handler.validar(passado)).doesNotThrowAnyException();
    }

    @Test
    void deveIgnorarQuandoAnoVazio() {
        LivroDTO nulo = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        nulo.setAnoPublicacao(null);

        assertThatCode(() -> handler.validar(nulo)).doesNotThrowAnyException();
    }
}
