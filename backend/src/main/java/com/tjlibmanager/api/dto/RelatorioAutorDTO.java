package com.tjlibmanager.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioAutorDTO {

    private Integer autorCodigo;
    private String autorNome;
    private List<RelatorioItemDTO> livros = new ArrayList<>();

    public Integer getAutorCodigo() { return autorCodigo; }
    public void setAutorCodigo(Integer autorCodigo) { this.autorCodigo = autorCodigo; }

    public String getAutorNome() { return autorNome; }
    public void setAutorNome(String autorNome) { this.autorNome = autorNome; }

    public List<RelatorioItemDTO> getLivros() { return livros; }
    public void setLivros(List<RelatorioItemDTO> livros) { this.livros = livros; }

    public int getQuantidadeLivros() {
        return livros.size();
    }

    public BigDecimal getValorTotal() {
        return livros.stream()
            .map(RelatorioItemDTO::getValor)
            .filter(v -> v != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
