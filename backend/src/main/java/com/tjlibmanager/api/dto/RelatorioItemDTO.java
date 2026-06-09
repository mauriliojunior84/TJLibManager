package com.tjlibmanager.api.dto;

import java.math.BigDecimal;

public class RelatorioItemDTO {

    private Integer livroCodigo;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private BigDecimal valor;
    private String assuntos;

    public Integer getLivroCodigo() { return livroCodigo; }
    public void setLivroCodigo(Integer livroCodigo) { this.livroCodigo = livroCodigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEditora() { return editora; }
    public void setEditora(String editora) { this.editora = editora; }

    public Integer getEdicao() { return edicao; }
    public void setEdicao(Integer edicao) { this.edicao = edicao; }

    public String getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getAssuntos() { return assuntos; }
    public void setAssuntos(String assuntos) { this.assuntos = assuntos; }
}
