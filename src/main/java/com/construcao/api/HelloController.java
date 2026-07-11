package com.construcao.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/")
  public String responderOla() {
    // Quando alguém acessar a raiz da nossa API, vamos responder isso:
    return "Bem-vindo a API da Construtora! Servidor rodando com sucesso.";
  }
}
