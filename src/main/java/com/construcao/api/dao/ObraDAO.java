package com.construcao.api.dao;

import com.construcao.api.model.Obra;
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
public class ObraDAO {

  private final JdbcTemplate jdbcTemplate;

  public ObraDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Obra> obraRowMapper = (rs, rowNum) -> new Obra(
      rs.getLong("id"),
      rs.getString("nome"),
      rs.getString("endereco"),
      rs.getBigDecimal("orcamento"),
      rs.getString("status"),
      rs.getObject("cliente_id") != null ? rs.getLong("cliente_id") : null);

  public Obra salvar(Obra obra) {
    String sql = "INSERT INTO obras (nome, endereco, orcamento, status, cliente_id) VALUES (?, ?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, obra.getNome());
      ps.setString(2, obra.getEndereco());
      ps.setBigDecimal(3, obra.getOrcamento());
      ps.setString(4, obra.getStatus());
      if (obra.getClienteId() != null) {
        ps.setLong(5, obra.getClienteId());
      } else {
        ps.setNull(5, java.sql.Types.BIGINT);
      }
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() != null) {
      obra.setId(keyHolder.getKey().longValue());
    }
    return obra;
  }

  public List<Obra> listarTodas() {
    String sql = "SELECT * FROM obras";
    return jdbcTemplate.query(sql, obraRowMapper);
  }

  public Optional<Obra> buscarPorId(Long id) {
    String sql = "SELECT * FROM obras WHERE id = ?";
    return jdbcTemplate.query(sql, obraRowMapper, id)
        .stream()
        .findFirst();
  }

  public boolean atualizar(Long id, Obra obra) {
    String sql = "UPDATE obras SET nome = ?, endereco = ?, orcamento = ?, status = ?, cliente_id = ? WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql,
        obra.getNome(),
        obra.getEndereco(),
        obra.getOrcamento(),
        obra.getStatus(),
        obra.getClienteId(),
        id);
    return linhasAfetadas > 0;
  }

  public boolean deletar(Long id) {
    String sql = "DELETE FROM obras WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, id);
    return linhasAfetadas > 0;
  }
}
