package com.construcao.api.model;

import java.math.BigDecimal;

public class Obra {

  private Long id;
  private String nome;
  private String endereco;
  private BigDecimal orcamento;
  private String status;

  public Obra() {

  }

  public Obra(Long id, String nome, String endereco, BigDecimal orcamento, String status) {
    this.id = id;
    this.nome = nome;
    this.endereco = endereco;
    this.orcamento = orcamento;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
