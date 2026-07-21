package com.construcao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

  @NotBlank(message = "O nome é obrigatório")
  @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
  private String nome;

  @NotBlank(message = "O telefone é obrigatório")
  @Size(min = 10, max = 15, message = "O telefone deve ter um formato válido (DDD + Número")
  private String telefone;

  @NotBlank(message = "O tipo de serviço é obrigatório")
  private String tipoServico;

  public ClienteRequestDTO(String nome, String telefone, String tipoServico) {
    this.nome = nome;
    this.telefone = telefone;
    this.tipoServico = tipoServico;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getTipoServico() {
    return tipoServico;
  }

  public void setTipoServico(String tipoServico) {
    this.tipoServico = tipoServico;
  }

}
