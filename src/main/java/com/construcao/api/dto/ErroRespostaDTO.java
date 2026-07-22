package com.construcao.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErroRespostaDTO {

  private LocalDateTime timestamp;
  private int status;
  private String erro;
  private List<String> detalhes;

  public ErroRespostaDTO() {

  }

  public ErroRespostaDTO(int status, String erro, List<String> detalhes) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.erro = erro;
    this.detalhes = detalhes;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getErro() {
    return erro;
  }

  public List<String> getDetalhes() {
    return detalhes;
  }
}
