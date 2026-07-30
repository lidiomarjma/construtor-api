package com.construcao.api.service;

import com.construcao.api.dao.AlocacaoDAO;
import com.construcao.api.dao.ColaboradorDAO;
import com.construcao.api.dao.ObraDAO;
import com.construcao.api.dto.AlocacaoRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlocacaoService {

  private final AlocacaoDAO alocacaoDAO;
  private final ObraDAO obraDAO;
  private final ColaboradorDAO colaboradorDAO;

  public AlocacaoService(AlocacaoDAO alocacaoDAO, ObraDAO obraDAO, ColaboradorDAO colaboradorDAO) {
    this.alocacaoDAO = alocacaoDAO;
    this.obraDAO = obraDAO;
    this.colaboradorDAO = colaboradorDAO;
  }

  public void alocarColaborador(AlocacaoRequestDTO dto) {
    // 1. Validar se a obra existe
    obraDAO.buscarPorId(dto.getObraId())
        .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada para o ID: " + dto.getObraId()));

    // 2. Validar se o colaborador existe
    colaboradorDAO.buscarPorId(dto.getColaboradorId())
        .orElseThrow(
            () -> new IllegalArgumentException("Colaborador não encontrado para o ID: " + dto.getColaboradorId()));

    // 3. Validar se já não está alocado
    if (alocacaoDAO.isAlocado(dto.getObraId(), dto.getColaboradorId())) {
      throw new IllegalArgumentException("Este colaborador já está alocado nesta obra.");
    }

    alocacaoDAO.alocar(dto.getObraId(), dto.getColaboradorId());
  }

  public void desalocarColaborador(Long obraId, Long colaboradorId) {
    boolean desalocou = alocacaoDAO.desalocar(obraId, colaboradorId);
    if (!desalocou) {
      throw new IllegalArgumentException(
          "Vínculo não encontrado entre a obra " + obraId + " e o colaborador " + colaboradorId);
    }
  }

  public List<ColaboradorResponseDTO> listarColaboradoresPorObra(Long obraId) {
    // Validar se a obra existe
    obraDAO.buscarPorId(obraId)
        .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada para o ID: " + obraId));

    return alocacaoDAO.listarColaboradoresDaObra(obraId).stream()
        .map(ColaboradorResponseDTO::new)
        .collect(Collectors.toList());
  }
}
