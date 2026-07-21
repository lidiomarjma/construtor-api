package com.construcao.api.dao;

import com.construcao.api.config.DatabaseConfig;
import com.construcao.api.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
  public List<Cliente> listarTodos() {
    List<Cliente> clientes = new ArrayList<>();
    String sql = "SELECT id, nome, telefone, tipo_servico FROM clientes";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setTipoServico(rs.getString("tipo_servico"));

        clientes.add(cliente);
      }
    } catch (SQLException e) {
      System.err.println("Erro ao listar clientes: " + e.getMessage());
    }

    return clientes;

  }

  public void salvar(Cliente cliente) {
    String sql = "INSERT INTO clientes (nome, telefone, tipo_servico) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getTelefone());
      stmt.setString(3, cliente.getTipoServico());

      stmt.executeUpdate();
      System.out.println("Cliente salvo com sucesso!");

    } catch (SQLException e) {
      System.err.println("Erro ao salvar cliente: " + e.getMessage());
    }
  }

  // Metodo de busca (GET)
  public Cliente buscarPorId(Long id) {
    String sql = "SELECT * FROM clientes WHERE id = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return new Cliente(
            rs.getLong("id"),
            rs.getString("nome"),
            rs.getString("telefone"),
            rs.getString("tipo_servico"));
      }
    } catch (SQLException e) {
      System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
    }
    return null; // Retorna null se não encontrar o ID no banco.
  }

  // Método para atualizar, cliente existente (PUT)
  public boolean atualizar(Cliente cliente) {
    String sql = "UPDATE clientes SET nome = ?, telefone = ?, tipo_servico = ? WHERE id =?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, cliente.getNome());
      stmt.setString(2, cliente.getTelefone());
      stmt.setString(3, cliente.getTipoServico());
      stmt.setLong(4, cliente.getId());

      int linhasAfetadas = stmt.executeUpdate();
      return linhasAfetadas > 0;
    } catch (SQLException e) {
      System.err.println("Erro ao atualizar cliente: " + e.getMessage());
      return false;
    }
  }

  // Método para deletar um cliente por ID (DELETE)
  public boolean deletar(Long id) {
    String sql = "DELETE FROM clientes WHERE id = ?";
    try (Connection conn = DatabaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);

      int linhasAfetadas = stmt.executeUpdate();
      return linhasAfetadas > 0;
    } catch (SQLException e) {
      System.err.println("Erro ao deletar cliente: " + e.getMessage());
      return false;
    }
  }
}
