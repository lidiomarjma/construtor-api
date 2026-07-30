package com.construcao.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ObraRequestDTO {

  @NotBlank(message = "O nome da obra não pode ser vazio")
  private String nome;

  @NotBlank(message = "O endereço não pode ser vazio")
  private String endereco;

  @NotNull(message = "O orçamento é obrigatório")
  @Positive(message = "O orçamento deve ser maior que zero")
  private BigDecimal orcamento;

  @NotBlank(message = "O status não pode ser vazio")
  private String status;

  @NotNull(message = "O ID do cliente é obrigatório")
  private Long clienteId;

  public ObraRequestDTO() {
  }

  public ObraRequestDTO(String nome, String endereco, BigDecimal orcamento, String status, Long clienteId) {
    this.nome = nome;
    this.endereco = endereco;
    this.orcamento = orcamento;
    this.status = status;
    this.clienteId = clienteId;
  }

  public String getNome() {
    return nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public BigDecimal getOrcamento() {
    return orcamento;
  }

  public String getStatus() {
    return status;
  }

  public Long getClienteId() {
    return clienteId;
  }
}
