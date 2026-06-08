package com.tjlibmanager.api.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjlibmanager.api.dto.AutorDTO;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.model.Autor;
import com.tjlibmanager.api.repository.AutorRepository;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<AutorDTO> findAll() {
        return autorRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AutorDTO findById(int id) {
        return autorRepository.findById(id).map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + id));
    }

    @Transactional
    public AutorDTO create(AutorDTO dto) {
        Autor autor = toEntity(dto);
        return toDTO(autorRepository.save(autor));
    }

    @Transactional
    public AutorDTO update(int id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + id));
        autor.setNome(dto.getNome());
        Autor saved = autorRepository.save(autor);
        return toDTO(Objects.requireNonNull(saved));
    }

    @Transactional
    public void delete(int id) {
        if (!autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor não encontrado: " + id);
        }
        autorRepository.deleteById(id);
    }

    public AutorDTO toDTO(Autor a) {
        AutorDTO dto = new AutorDTO();
        dto.setCodAu(a.getCodAu());
        dto.setNome(a.getNome());
        return dto;
    }

    private Autor toEntity(AutorDTO dto) {
        Autor a = new Autor();
        a.setNome(dto.getNome());
        return a;
    }
}
