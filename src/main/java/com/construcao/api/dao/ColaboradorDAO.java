package com.construcao.api.dao;

import com.construcao.api.model.Colaborador;
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
public class ColaboradorDAO {

  private final JdbcTemplate jdbcTemplate;

  public ColaboradorDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Colaborador> colaboradorRowMapper = (rs, rowNum) -> new Colaborador(
      rs.getLong("id"),
      rs.getString("nome"),
      rs.getString("cpf"),
      rs.getString("funcao"));

  public Colaborador salvar(Colaborador colaborador) {
    String sql = "INSERT INTO colaboradores (nome, cpf, funcao) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, colaborador.getNome());
      ps.setString(2, colaborador.getCpf());
      ps.setString(3, colaborador.getFuncao());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() != null) {
      colaborador.setId(keyHolder.getKey().longValue());
    }
    return colaborador;
  }

  public List<Colaborador> listarTodos() {
    String sql = "SELECT * FROM colaboradores";
    return jdbcTemplate.query(sql, colaboradorRowMapper);
  }

  public Optional<Colaborador> buscarPorId(Long id) {
    String sql = "SELECT * FROM colaboradores WHERE id = ?";
    List<Colaborador> resultados = jdbcTemplate.query(sql, colaboradorRowMapper, id);
    return resultados.stream().findFirst();
  }

  public boolean atualizar(Long id, Colaborador colaborador) {
    String sql = "UPDATE colaboradores SET nome = ?, cpf = ?, funcao = ? WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, colaborador.getNome(), colaborador.getCpf(), colaborador.getFuncao(),
        id);
    return linhasAfetadas > 0;
  }

  public boolean deletar(Long id) {
    String sql = "DELETE FROM colaboradores WHERE id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, id);
    return linhasAfetadas > 0;
  }
}
