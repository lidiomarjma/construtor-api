package com.construcao.api.dao;

import com.construcao.api.model.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteDAO {

  private final JdbcTemplate jdbcTemplate;

  public ClienteDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Cliente> clienteRowMapper = (rs, rowNum) -> new Cliente(
      rs.getLong("id"),
      rs.getString("nome"),
      rs.getString("telefone"),
      rs.getString("tipo_servico"));

  public Cliente salvar(Cliente cliente) {
    String sql = "INSERT INTO clientes (nome, telefone, tipo_servico) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, cliente.getNome());
      ps.setString(2, cliente.getTelefone());
      ps.setString(3, cliente.getTipoServico());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() != null) {
      cliente.setId(keyHolder.getKey().longValue());
    }
    return cliente;
  }

  public List<Cliente> listarTodos() {
    String sql = "SELECT * FROM clientes";
    return jdbcTemplate.query(sql, clienteRowMapper);
  }

  public Optional<Cliente> buscarPorId(Long id) {
    String sql = "SELECT * FROM clientes WHERE id = ?";
    List<Cliente> resultados = jdbcTemplate.query(sql, clienteRowMapper, id);
    return resultados.stream().findFirst();
  }

  public boolean atualizar(Long id, Cliente cliente) {
    String sql = "UPDATE clientes SET nome = ?, telefone = ?, tipo_servico = ? WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, cliente.getNome(), cliente.getTelefone(), cliente.getTipoServico(),
        id);
    return linhasAfetadas > 0;
  }

  public boolean deletar(Long id) {
    String sql = "DELETE FROM clientes WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, id);
    return linhasAfetadas > 0;
  }
}
