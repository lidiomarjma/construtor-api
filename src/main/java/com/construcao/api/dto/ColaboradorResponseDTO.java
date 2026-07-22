package com.construcao.api.dto;

import com.construcao.api.model.Colaborador;

public class ColaboradorResponseDTO {

  private Long id;
  private String nome;
  private String cpf;
  private String funcao;

  public ColaboradorResponseDTO(Colaborador colaborador) {
    this.id = colaborador.getId();
    this.nome = colaborador.getNome();
    this.cpf = colaborador.getCpf();
    this.funcao = colaborador.getFuncao();
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getCpf() {
    return cpf;
  }

  public String getFuncso() {
    return funcao;
  }
}
