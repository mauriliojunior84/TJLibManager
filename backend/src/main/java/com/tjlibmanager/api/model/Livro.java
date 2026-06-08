package com.tjlibmanager.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Codl")
    private Integer codl;

    @NotBlank
    @Size(max = 40)
    @Column(name = "Titulo", nullable = false, length = 40)
    private String titulo;

    @Size(max = 40)
    @Column(name = "Editora", length = 40)
    private String editora;

    @Column(name = "Edicao")
    private Integer edicao;

    @Pattern(regexp = "\\d{4}")
    @Size(max = 4)
    @Column(name = "AnoPublicacao", length = 4)
    private String anoPublicacao;

    @NotNull
    @PositiveOrZero
    @Column(name = "Valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Livro_Autor",
        joinColumns        = @JoinColumn(name = "Livro_Codl"),
        inverseJoinColumns = @JoinColumn(name = "Autor_CodAu")
    )
    private Set<Autor> autores = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Livro_Assunto",
        joinColumns        = @JoinColumn(name = "Livro_Codl"),
        inverseJoinColumns = @JoinColumn(name = "Assunto_codAs")
    )
    private Set<Assunto> assuntos = new HashSet<>();

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

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Set<Autor> getAutores() { return autores; }
    public void setAutores(Set<Autor> autores) { this.autores = autores; }

    public Set<Assunto> getAssuntos() { return assuntos; }
    public void setAssuntos(Set<Assunto> assuntos) { this.assuntos = assuntos; }
}
