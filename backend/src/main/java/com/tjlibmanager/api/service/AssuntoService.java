package com.tjlibmanager.api.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjlibmanager.api.dto.AssuntoDTO;
import com.tjlibmanager.api.exception.ResourceInUseException;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.model.Assunto;
import com.tjlibmanager.api.repository.AssuntoRepository;
import com.tjlibmanager.api.repository.LivroRepository;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;
    private final LivroRepository livroRepository;

    public AssuntoService(AssuntoRepository assuntoRepository, LivroRepository livroRepository) {
        this.assuntoRepository = assuntoRepository;
        this.livroRepository = livroRepository;
    }

    public List<AssuntoDTO> findAll() {
        return assuntoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AssuntoDTO findById(int id) {
        return assuntoRepository.findById(id).map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado: " + id));
    }

    @Transactional
    public AssuntoDTO create(AssuntoDTO dto) {
        Assunto assunto = toEntity(dto);
        Assunto saved = assuntoRepository.save(assunto);
        return toDTO(Objects.requireNonNull(saved));
    }

    @Transactional
    public AssuntoDTO update(int id, AssuntoDTO dto) {
        Assunto assunto = assuntoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado: " + id));
        assunto.setDescricao(dto.getDescricao());
        Assunto saved = assuntoRepository.save(assunto);
        return toDTO(Objects.requireNonNull(saved));
    }

    @Transactional
    public void delete(int id) {
        if (!assuntoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assunto não encontrado: " + id);
        }
        long vinculos = livroRepository.countByAssuntos_CodAs(id);
        if (vinculos > 0) {
            throw new ResourceInUseException(
                "Assunto não pode ser excluído pois está vinculado a " + vinculos + " livro(s).");
        }
        assuntoRepository.deleteById(id);
    }

    public AssuntoDTO toDTO(Assunto a) {
        AssuntoDTO dto = new AssuntoDTO();
        dto.setCodAs(a.getCodAs());
        dto.setDescricao(a.getDescricao());
        return dto;
    }

    private Assunto toEntity(AssuntoDTO dto) {
        Assunto a = new Assunto();
        a.setDescricao(dto.getDescricao());
        return a;
    }
}
