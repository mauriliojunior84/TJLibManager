package com.tjlibmanager.api.service;

import com.tjlibmanager.api.dto.AssuntoDTO;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.model.Assunto;
import com.tjlibmanager.api.repository.AssuntoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public List<AssuntoDTO> findAll() {
        return assuntoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AssuntoDTO findById(Integer id) {
        return assuntoRepository.findById(id).map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado: " + id));
    }

    @Transactional
    public AssuntoDTO create(AssuntoDTO dto) {
        Assunto assunto = toEntity(dto);
        return toDTO(assuntoRepository.save(assunto));
    }

    @Transactional
    public AssuntoDTO update(Integer id, AssuntoDTO dto) {
        Assunto assunto = assuntoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado: " + id));
        assunto.setDescricao(dto.getDescricao());
        return toDTO(assuntoRepository.save(assunto));
    }

    @Transactional
    public void delete(Integer id) {
        if (!assuntoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assunto não encontrado: " + id);
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
