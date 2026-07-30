package com.construcao.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.mariadb.jdbc.Driver");

    // Pega de variáveis de ambiente se existirem, senão usa os padrões do MariaDB
    String url = System.getenv("DB_URL") != null ? System.getenv("DB_URL")
        : "jdbc:mariadb://localhost:3306/construtora_db";
    String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "dev_projetos";
    String pass = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "87482452";

    dataSource.setUrl(url);
    dataSource.setUsername(user);
    dataSource.setPassword(pass);

    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
