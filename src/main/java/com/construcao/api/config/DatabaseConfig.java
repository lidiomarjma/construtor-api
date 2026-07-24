package com.construcao.api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

  private static final String URL = System.getenv("DB_URL") != null
      ? System.getenv("DB_URL")
      : "jdbc:mariadb://localhost:3306/construtora_db";

  private static final String USER = System.getenv("DB_USER") != null
      ? System.getenv("DB_USER")
      : "root";

  private static final String PASS = System.getenv("DB_PASS") != null
      ? System.getenv("DB_PASS")
      : "";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }
}
