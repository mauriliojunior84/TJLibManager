package com.tjlibmanager.api.validation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.repository.AutorRepository;

@Component
@Order(3)
public class AutoresExistemHandler implements LivroValidationHandler {

    private final AutorRepository autorRepository;

    public AutoresExistemHandler(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Override
    public void validar(LivroDTO dto) {
        if (dto.getAutoresIds() == null) {
            return;
        }
        for (Integer id : dto.getAutoresIds()) {
            if (!autorRepository.existsById(id)) {
                throw new ValidacaoException("Autor não encontrado: " + id);
            }
        }
    }
}
