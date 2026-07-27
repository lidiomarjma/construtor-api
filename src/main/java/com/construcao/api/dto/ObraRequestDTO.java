package com.construcao.api.dto;

import java.math.BigDecimal;

public class ObraRequestDTO {

  private String nome;
  private String endereco;
  private BigDecimal orcamento;
  private String status;

  public ObraRequestDTO() {

  }

  public ObraRequestDTO(String nome, String endereco, BigDecimal orcamento, String status) {
    this.nome = nome;
    this.endereco = endereco;
    this.orcamento = orcamento;
    this.status = status;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public BigDecimal getOrcamento() {
    return orcamento;
  }

  public void setOrcamento(BigDecimal orcamento) {
    this.orcamento = orcamento;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
