package com.tjlibmanager.api.validation;

import java.math.BigDecimal;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;

@Component
@Order(1)
public class ValorHandler implements LivroValidationHandler {

    @Override
    public void validar(LivroDTO dto) {
        if (dto.getValor() == null) {
            throw new ValidacaoException("Valor é obrigatório.");
        }
        if (dto.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidacaoException("Valor não pode ser negativo.");
        }
    }
}
