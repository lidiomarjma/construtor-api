package com.construcao.api.model;

public class Colaborador {

  private Long id;
  private String nome;
  private String cpf;
  private String funcao;

  // Construtor sem argumentos
  public Colaborador() {
  }

  // Construtor completo (com ID)
  public Colaborador(Long id, String nome, String cpf, String funcao) {
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.funcao = funcao;
  }

  // Construtor para novos cadastros (sem ID)
  public Colaborador(String nome, String cpf, String funcao) {
    this.nome = nome;
    this.cpf = cpf;
    this.funcao = funcao;
  }

  // Getters e Setters
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
