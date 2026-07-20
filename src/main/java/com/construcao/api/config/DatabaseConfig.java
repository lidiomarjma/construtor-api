package com.construcao.api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

  private static final String URL = "jdbc:mariadb://localhost:3306/construtora_db";
  private static final String USER = "dev_construtora";
  private static final String PASSWORD = "senha_dev_123";

  public static Connection getConnection() throws SQLException {
    try {
      Class.forName("org.mariadb.jdbc.Driver");
      return DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new SQLException("Driver do MariaDB não foi encontrado no projeto!", e);
    }
  }
}
