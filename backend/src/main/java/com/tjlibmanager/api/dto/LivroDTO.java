package com.tjlibmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class LivroDTO {

    private Integer codl;

    @NotBlank
    @Size(max = 40)
    private String titulo;

    @Size(max = 40)
    private String editora;

    private Integer edicao;

    @Pattern(regexp = "\\d{4}", message = "Ano deve conter 4 dígitos")
    private String anoPublicacao;

    private Set<Integer> autoresIds;
    private Set<Integer> assuntosIds;

    public Integer getCodl() { return codl; }
    public void setCodl(Integer codl) { this.codl = codl; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Integer getEdicao() { return edicao; }
    public void setEdicao(Integer edicao) { this.edicao = edicao; }

    public String getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public Set<Integer> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(Set<Integer> autoresIds) { this.autoresIds = autoresIds; }

    public Set<Integer> getAssuntosIds() { return assuntosIds; }
    public void setAssuntosIds(Set<Integer> assuntosIds) { this.assuntosIds = assuntosIds; }
}
