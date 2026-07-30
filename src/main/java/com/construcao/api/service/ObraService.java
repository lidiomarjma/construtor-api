package com.construcao.api.service;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.dao.ObraDAO;
import com.construcao.api.dto.ObraRequestDTO;
import com.construcao.api.dto.ObraResponseDTO;
import com.construcao.api.model.Obra;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObraService {

  private final ObraDAO obraDAO;
  private final ClienteDAO clienteDAO;

  public ObraService(ObraDAO obraDAO, ClienteDAO clienteDAO) {
    this.obraDAO = obraDAO;
    this.clienteDAO = clienteDAO;
  }

  public ObraResponseDTO salvar(ObraRequestDTO dto) {
    // Regra de Negócio: Verificar se o cliente realmente existe antes de vincular à
    // obra
    clienteDAO.buscarPorId(dto.getClienteId())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID: " + dto.getClienteId()));

    Obra novaObra = new Obra(null, dto.getNome(), dto.getEndereco(), dto.getOrcamento(), dto.getStatus(),
        dto.getClienteId());
    Obra obraSalva = obraDAO.salvar(novaObra);
    return new ObraResponseDTO(obraSalva);
  }

  public List<ObraResponseDTO> listarTodas() {
    return obraDAO.listarTodas().stream()
        .map(ObraResponseDTO::new)
        .collect(Collectors.toList());
  }

  public ObraResponseDTO buscarPorId(Long id) {
    Obra obra = obraDAO.buscarPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Obra não encontrada para o ID: " + id));

    return new ObraResponseDTO(obra);
  }

  public ObraResponseDTO atualizar(Long id, ObraRequestDTO dto) {
    // Regra de Negócio: Verificar se o novo cliente existe antes de atualizar
    clienteDAO.buscarPorId(dto.getClienteId())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID: " + dto.getClienteId()));

    Obra obraParaAtualizar = new Obra(id, dto.getNome(), dto.getEndereco(), dto.getOrcamento(), dto.getStatus(),
        dto.getClienteId());
    boolean atualizou = obraDAO.atualizar(id, obraParaAtualizar);

    if (!atualizou) {
      throw new IllegalArgumentException("Não foi possível atualizar. Obra não encontrada para o ID: " + id);
    }

    return new ObraResponseDTO(obraParaAtualizar);
  }

  public void deletar(Long id) {
    boolean deletou = obraDAO.deletar(id);
    if (!deletou) {
      throw new IllegalArgumentException("Não foi possível deletar. Obra não encontrada para o ID: " + id);
    }
  }
}
