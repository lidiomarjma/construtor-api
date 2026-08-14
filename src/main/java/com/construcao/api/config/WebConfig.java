package com.construcao.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // Aplica para TODAS as rotas (/obras, /clientes, etc.)
        .allowedOrigins("*") // Permite chamadas de qualquer Frontend
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Libera os métodos HTTP
  }
}
