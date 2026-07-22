package com.construcao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ColaboradorRequestDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String nome;

  @NotBlank(message = "O CPF é obrigatório")
  private String cpf;

  @NotBlank(message = "A função é obrigatória")
  private String funcao;

  public ColaboradorRequestDTO() {

  }

  public ColaboradorRequestDTO(String nome, String cpf, String funcao) {
    this.nome = nome;
    this.cpf = cpf;
    this.funcao = funcao;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getFuncao() {
    return funcao;
  }

  public void setFuncao(String funcao) {
    this.funcao = funcao;
  }
}
