package com.construcao.api.dto;

import com.construcao.api.model.Cliente;

public class ClienteResponseDTO {

  private Long id;
  private String nome;
  private String telefone;
  private String tipoServico;

  public ClienteResponseDTO() {

  }

  public ClienteResponseDTO(Long id, String nome, String telefone, String tipoServico) {
    this.id = id;
    this.nome = nome;
    this.telefone = telefone;
    this.tipoServico = tipoServico;
  }

  public ClienteResponseDTO(Cliente cliente) {
    this.id = cliente.getId();
    this.nome = cliente.getNome();
    this.telefone = cliente.getTelefone();
    this.tipoServico = cliente.getTipoServico();
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public String getTipoServico() {
    return tipoServico;
  }
}
