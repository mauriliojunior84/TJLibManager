package com.tjlibmanager.api.controller;

import com.tjlibmanager.api.dto.AutorDTO;
import com.tjlibmanager.api.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<AutorDTO> findAll() {
        return autorService.findAll();
    }

    @GetMapping("/{id}")
    public AutorDTO findById(@PathVariable Integer id) {
        return autorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> create(@Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.create(dto));
    }

    @PutMapping("/{id}")
    public AutorDTO update(@PathVariable Integer id, @Valid @RequestBody AutorDTO dto) {
        return autorService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
