package com.tjlibmanager.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Assunto")
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codAs")
    private Integer codAs;

    @NotBlank
    @Size(max = 20)
    @Column(name = "Descricao", nullable = false, length = 20)
    private String descricao;

    @ManyToMany(mappedBy = "assuntos")
    private Set<Livro> livros = new HashSet<>();

    public Integer getCodAs() { return codAs; }
    public void setCodAs(Integer codAs) { this.codAs = codAs; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Set<Livro> getLivros() { return livros; }
    public void setLivros(Set<Livro> livros) { this.livros = livros; }
}
