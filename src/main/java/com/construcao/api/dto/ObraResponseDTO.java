package com.construcao.api.dto;

import com.construcao.api.model.Obra;

import java.math.BigDecimal;

public class ObraResponseDTO {

  private Long id;
  private String nome;
  private String endereco;
  private BigDecimal orcamento;
  private String status;

  public ObraResponseDTO(Obra obra) {
    this.id = obra.getId();
    this.nome = obra.getNome();
    this.endereco = obra.getEndereco();
    this.orcamento = obra.getOrcamento();
    this.status = obra.getStatus();
  }

  public Long getId() {
    return id;
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
}
