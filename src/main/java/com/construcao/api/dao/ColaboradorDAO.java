package com.construcao.api.dao;

import com.construcao.api.config.DatabaseConfig;
import com.construcao.api.model.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

  // 1. SALVAR (INSERT)
  public Colaborador salvar(Colaborador colaborador) {
    String sql = "INSERT INTO colaboradores (nome, cpf, funcao) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setString(1, colaborador.getNome());
      stmt.setString(2, colaborador.getCpf());
      stmt.setString(3, colaborador.getFuncao());

      stmt.executeUpdate();

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          colaborador.setId(rs.getLong(1));
        }
      }

      return colaborador;

    } catch (SQLException e) {
      System.err.println("Erro ao salvar colaborador: " + e.getMessage());
      throw new RuntimeException("Erro SQL ao salvar no MariaDB: " + e.getMessage(), e);
    }
  }

  // 2. LISTAR TODOS (SELECT)
  public List<Colaborador> listarTodos() {
    List<Colaborador> colaboradores = new ArrayList<>();
    String sql = "SELECT * FROM colaboradores";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Colaborador c = new Colaborador(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("funcao"));
        colaboradores.add(c);
      }

    } catch (SQLException e) {
      System.err.println("Erro ao listar colaboradores: " + e.getMessage());
    }

    return colaboradores;
  }

  // 3. BUSCAR POR ID (SELECT WHERE ID)
  public Colaborador buscarPorId(Long id) {
    String sql = "SELECT * FROM colaboradores WHERE id = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return new Colaborador(
              rs.getLong("id"),
              rs.getString("nome"),
              rs.getString("cpf"),
              rs.getString("funcao"));
        }
      }

    } catch (SQLException e) {
      System.err.println("Erro ao buscar colaborador por ID: " + e.getMessage());
    }

    return null;
  }

  // 4. ATUALIZAR (UPDATE)
  public boolean atualizar(Colaborador colaborador) {
    String sql = "UPDATE colaboradores SET nome = ?, cpf = ?, funcao = ? WHERE id = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, colaborador.getNome());
      stmt.setString(2, colaborador.getCpf());
      stmt.setString(3, colaborador.getFuncao());
      stmt.setLong(4, colaborador.getId());

      int linhasAfetadas = stmt.executeUpdate();
      return linhasAfetadas > 0;

    } catch (SQLException e) {
      System.err.println("Erro ao atualizar colaborador: " + e.getMessage());
      return false;
    }
  }

  // 5. DELETAR (DELETE)
  public boolean deletar(Long id) {
    String sql = "DELETE FROM colaboradores WHERE id = ?";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);

      int linhasAfetadas = stmt.executeUpdate();
      return linhasAfetadas > 0;

    } catch (SQLException e) {
      System.err.println("Erro ao deletar colaborador: " + e.getMessage());
      return false;
    }
  }
}
