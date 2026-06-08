package com.tjlibmanager.api.controller;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.dto.LivroResponseDTO;
import com.tjlibmanager.api.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<LivroResponseDTO> findAll() {
        return livroService.findAll();
    }

    @GetMapping("/{id}")
    public LivroResponseDTO findById(@PathVariable Integer id) {
        return livroService.findById(id);
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> create(@Valid @RequestBody LivroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.create(dto));
    }

    @PutMapping("/{id}")
    public LivroResponseDTO update(@PathVariable Integer id, @Valid @RequestBody LivroDTO dto) {
        return livroService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
