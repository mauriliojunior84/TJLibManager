package com.tjlibmanager.api.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjlibmanager.api.dto.RelatorioAutorDTO;
import com.tjlibmanager.api.service.RelatorioService;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/livros-por-autor")
    public List<RelatorioAutorDTO> livrosPorAutor() {
        return relatorioService.livrosPorAutor();
    }

    @GetMapping("/livros-por-autor/pdf")
    public ResponseEntity<byte[]> livrosPorAutorPdf() {
        byte[] pdf = relatorioService.livrosPorAutorPdf();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "livros-por-autor.pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
