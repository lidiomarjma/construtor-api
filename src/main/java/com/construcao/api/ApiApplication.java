package com.construcao.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ApiApplication {
  public static void main(String[] args) {
    // Esse comando diz para o Java ligar o Spring Boot e iniciar o servidor
    SpringApplication.run(ApiApplication.class, args);
  }
}
