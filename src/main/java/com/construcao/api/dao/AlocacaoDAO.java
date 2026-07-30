package com.construcao.api.dao;

import com.construcao.api.model.Colaborador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlocacaoDAO {

  private final JdbcTemplate jdbcTemplate;

  public AlocacaoDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private final RowMapper<Colaborador> colaboradorRowMapper = (rs, rowNum) -> new Colaborador(
      rs.getLong("id"),
      rs.getString("nome"),
      rs.getString("cpf"),
      rs.getString("funcao"));

  // Salva o vínculo entre Obra e Colaborador
  public boolean alocar(Long obraId, Long colaboradorId) {
    String sql = "INSERT INTO obras_colaboradores (obra_id, colaborador_id) VALUES (?, ?)";
    int linhasAfetadas = jdbcTemplate.update(sql, obraId, colaboradorId);
    return linhasAfetadas > 0;
  }

  // Removendo o vínculo
  public boolean desalocar(Long obraId, Long colaboradorId) {
    String sql = "DELETE FROM obras_colaboradores WHERE obra_id = ? AND colaborador_id = ?";
    int linhasAfetadas = jdbcTemplate.update(sql, obraId, colaboradorId);
    return linhasAfetadas > 0;
  }

  // Verifica se já existe essa alocação
  public boolean isAlocado(Long obraId, Long colaboradorId) {
    String sql = "SELECT COUNT(*) FROM obras_colaboradores WHERE obra_id = ? AND colaborador_id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, obraId, colaboradorId);
    return count != null && count > 0;
  }

  // Busca todos os colaboradores vinculados a uma obra (usando JOIN)
  public List<Colaborador> listarColaboradoresDaObra(Long obraId) {
    String sql = """
            SELECT c.* FROM colaboradores c
            INNER JOIN obras_colaboradores oc ON c.id = oc.colaborador_id
            WHERE oc.obra_id = ?
        """;
    return jdbcTemplate.query(sql, colaboradorRowMapper, obraId);
  }
}
