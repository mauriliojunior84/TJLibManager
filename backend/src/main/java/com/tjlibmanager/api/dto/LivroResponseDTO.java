package com.tjlibmanager.api.dto;

import java.util.Set;

public class LivroResponseDTO {

    private Integer codl;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private Set<AutorDTO> autores;
    private Set<AssuntoDTO> assuntos;

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

    public Set<AutorDTO> getAutores() { return autores; }
    public void setAutores(Set<AutorDTO> autores) { this.autores = autores; }

    public Set<AssuntoDTO> getAssuntos() { return assuntos; }
    public void setAssuntos(Set<AssuntoDTO> assuntos) { this.assuntos = assuntos; }
}
