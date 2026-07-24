package com.construcao.api.dto;

import com.construcao.api.model.Colaborador;

public class ColaboradorResponseDTO {

  private Long id;
  private String nome;
  private String cpf;
  private String funcao;

  // Construtor padrão necessário para bibliotecas de serialização
  public ColaboradorResponseDTO() {
  }

  public ColaboradorResponseDTO(Colaborador colaborador) {
    if (colaborador != null) {
      this.id = colaborador.getId();
      this.nome = colaborador.getNome();
      this.cpf = colaborador.getCpf();
      this.funcao = colaborador.getFuncao();
    }
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

  // Corrigido de getFuncso() para getFuncao()
  public String getFuncao() {
    return funcao;
  }
}
