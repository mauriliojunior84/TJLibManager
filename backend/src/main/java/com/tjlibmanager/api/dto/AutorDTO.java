package com.tjlibmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AutorDTO {

    private Integer codAu;

    @NotBlank
    @Size(max = 40)
    private String nome;

    public Integer getCodAu() { return codAu; }
    public void setCodAu(Integer codAu) { this.codAu = codAu; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
