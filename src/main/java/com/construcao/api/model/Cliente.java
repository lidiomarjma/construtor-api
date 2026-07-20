package com.construcao.api.model;

/* 
 * REFERÊNCIA FUTURA PARA USO DO LOMBOK:
 * Se fôssemos usar a biblioteca Lombok para economizar código, 
 * os imports e anotações seriam estes:
 *
 * import lombok.Getter;
 * import lombok.Setter;
 * import lombok.NoArgsConstructor;
 * import lombok.AllArgsConstructor;
 *
 * @Getter
 * @Setter
 * @NoArgsConstructor
 * @AllArgsConstructor
 */
public class Cliente {

  private Long id;
  private String nome;
  private String telefone;
  private String tipoServico;

  public Cliente() {
  }

  public Cliente(Long id, String nome, String telefone, String tipoServico) {

    this.id = id;
    this.nome = nome;
    this.telefone = telefone;
    this.tipoServico = tipoServico;

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
