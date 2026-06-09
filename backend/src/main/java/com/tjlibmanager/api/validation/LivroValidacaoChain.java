package com.tjlibmanager.api.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tjlibmanager.api.dto.LivroDTO;

@Component
public class LivroValidacaoChain {

    private final List<LivroValidationHandler> handlers;

    public LivroValidacaoChain(List<LivroValidationHandler> handlers) {
        this.handlers = handlers;
    }

    public void validar(LivroDTO dto) {
        for (LivroValidationHandler handler : handlers) {
            handler.validar(dto);
        }
    }
}
