package com.tjlibmanager.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AssuntoDTO {

    private Integer codAs;

    @NotBlank
    @Size(max = 20)
    private String descricao;

    public Integer getCodAs() { return codAs; }
    public void setCodAs(Integer codAs) { this.codAs = codAs; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
