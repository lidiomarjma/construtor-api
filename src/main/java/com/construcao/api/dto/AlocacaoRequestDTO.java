package com.construcao.api.dto;

import jakarta.validation.constraints.NotNull;

public class AlocacaoRequestDTO {

  @NotNull(message = "O ID da obra é obrigatório")
  private Long obraId;

  @NotNull(message = "O ID do colaborador é obrigatório")
  private Long colaboradorId;

  public AlocacaoRequestDTO() {
  }

  public AlocacaoRequestDTO(Long obraId, Long colaboradorId) {
    this.obraId = obraId;
    this.colaboradorId = colaboradorId;
  }

  public Long getObraId() {
    return obraId;
  }

  public Long getColaboradorId() {
    return colaboradorId;
  }
}
