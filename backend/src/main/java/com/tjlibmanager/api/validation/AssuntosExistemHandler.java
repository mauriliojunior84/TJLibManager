package com.tjlibmanager.api.validation;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.repository.AssuntoRepository;

@Component
@Order(4)
public class AssuntosExistemHandler implements LivroValidationHandler {

    private final AssuntoRepository assuntoRepository;

    public AssuntosExistemHandler(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    @Override
    public void validar(LivroDTO dto) {
        if (dto.getAssuntosIds() == null) {
            return;
        }
        for (Integer id : dto.getAssuntosIds()) {
            if (!assuntoRepository.existsById(id)) {
                throw new ValidacaoException("Assunto não encontrado: " + id);
            }
        }
    }
}
