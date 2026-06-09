package com.tjlibmanager.api.validation;

import java.time.Year;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;

@Component
@Order(2)
public class AnoPublicacaoHandler implements LivroValidationHandler {

    @Override
    public void validar(LivroDTO dto) {
        String ano = dto.getAnoPublicacao();
        if (ano == null || ano.trim().isEmpty()) {
            return;
        }
        int anoInformado = Integer.parseInt(ano);
        if (anoInformado > Year.now().getValue()) {
            throw new ValidacaoException("Ano de publicação não pode ser futuro.");
        }
    }
}
