package com.tjlibmanager.api.fixture;

import java.math.BigDecimal;
import java.time.Year;

import com.tjlibmanager.api.dto.LivroDTO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class LivroDTOTemplate implements TemplateLoader {

    public static final String VALIDO = "valido";
    public static final String SEM_VALOR = "semValor";
    public static final String VALOR_NEGATIVO = "valorNegativo";
    public static final String ANO_FUTURO = "anoFuturo";

    @Override
    public void load() {
        Fixture.of(LivroDTO.class).addTemplate(VALIDO, new Rule() {{
            add("titulo", "Dom Casmurro");
            add("editora", "Garnier");
            add("edicao", 1);
            add("anoPublicacao", "1899");
            add("valor", new BigDecimal("59.90"));
        }});

        Fixture.of(LivroDTO.class).addTemplate(SEM_VALOR).inherits(VALIDO, new Rule() {{
            add("valor", null);
        }});

        Fixture.of(LivroDTO.class).addTemplate(VALOR_NEGATIVO).inherits(VALIDO, new Rule() {{
            add("valor", new BigDecimal("-1.00"));
        }});

        Fixture.of(LivroDTO.class).addTemplate(ANO_FUTURO).inherits(VALIDO, new Rule() {{
            add("anoPublicacao", String.valueOf(Year.now().getValue() + 1));
        }});
    }
}
