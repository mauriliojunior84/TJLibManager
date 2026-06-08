package com.tjlibmanager.api.controller;

import com.tjlibmanager.api.dto.AssuntoDTO;
import com.tjlibmanager.api.service.AssuntoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assuntos")
@CrossOrigin(origins = "*")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @GetMapping
    public List<AssuntoDTO> findAll() {
        return assuntoService.findAll();
    }

    @GetMapping("/{id}")
    public AssuntoDTO findById(@PathVariable Integer id) {
        return assuntoService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AssuntoDTO> create(@Valid @RequestBody AssuntoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assuntoService.create(dto));
    }

    @PutMapping("/{id}")
    public AssuntoDTO update(@PathVariable Integer id, @Valid @RequestBody AssuntoDTO dto) {
        return assuntoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        assuntoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
