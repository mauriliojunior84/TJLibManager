package com.tjlibmanager.api.service;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.dto.LivroResponseDTO;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.model.Assunto;
import com.tjlibmanager.api.model.Autor;
import com.tjlibmanager.api.model.Livro;
import com.tjlibmanager.api.repository.AssuntoRepository;
import com.tjlibmanager.api.repository.AutorRepository;
import com.tjlibmanager.api.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final AssuntoRepository assuntoRepository;
    private final AutorService autorService;
    private final AssuntoService assuntoService;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository,
                        AssuntoRepository assuntoRepository, AutorService autorService,
                        AssuntoService assuntoService) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.assuntoRepository = assuntoRepository;
        this.autorService = autorService;
        this.assuntoService = assuntoService;
    }

    public List<LivroResponseDTO> findAll() {
        return livroRepository.findAllWithRelations().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public LivroResponseDTO findById(Integer id) {
        Livro livro = livroRepository.findByIdWithRelations(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));
        return toResponseDTO(livro);
    }

    @Transactional
    public LivroResponseDTO create(LivroDTO dto) {
        Livro livro = new Livro();
        applyDTO(livro, dto);
        return toResponseDTO(livroRepository.save(livro));
    }

    @Transactional
    public LivroResponseDTO update(Integer id, LivroDTO dto) {
        Livro livro = livroRepository.findByIdWithRelations(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado: " + id));
        applyDTO(livro, dto);
        return toResponseDTO(livroRepository.save(livro));
    }

    @Transactional
    public void delete(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livro não encontrado: " + id);
        }
        livroRepository.deleteById(id);
    }

    private void applyDTO(Livro livro, LivroDTO dto) {
        livro.setTitulo(dto.getTitulo());
        livro.setEditora(dto.getEditora());
        livro.setEdicao(dto.getEdicao());
        livro.setAnoPublicacao(dto.getAnoPublicacao());

        Set<Autor> autores = new HashSet<>();
        if (dto.getAutoresIds() != null) {
            for (Integer aid : dto.getAutoresIds()) {
                Autor a = autorRepository.findById(aid)
                    .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado: " + aid));
                autores.add(a);
            }
        }
        livro.setAutores(autores);

        Set<Assunto> assuntos = new HashSet<>();
        if (dto.getAssuntosIds() != null) {
            for (Integer sid : dto.getAssuntosIds()) {
                Assunto s = assuntoRepository.findById(sid)
                    .orElseThrow(() -> new ResourceNotFoundException("Assunto não encontrado: " + sid));
                assuntos.add(s);
            }
        }
        livro.setAssuntos(assuntos);
    }

    private LivroResponseDTO toResponseDTO(Livro l) {
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setCodl(l.getCodl());
        dto.setTitulo(l.getTitulo());
        dto.setEditora(l.getEditora());
        dto.setEdicao(l.getEdicao());
        dto.setAnoPublicacao(l.getAnoPublicacao());
        dto.setAutores(l.getAutores().stream().map(autorService::toDTO).collect(Collectors.toSet()));
        dto.setAssuntos(l.getAssuntos().stream().map(assuntoService::toDTO).collect(Collectors.toSet()));
        return dto;
    }
}
