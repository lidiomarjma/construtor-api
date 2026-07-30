package com.construcao.api.service;

import com.construcao.api.dao.ColaboradorDAO;
import com.construcao.api.dto.ColaboradorRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.model.Colaborador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColaboradorService {

  private final ColaboradorDAO colaboradorDAO;

  public ColaboradorService(ColaboradorDAO colaboradorDAO) {
    this.colaboradorDAO = colaboradorDAO;
  }

  public ColaboradorResponseDTO criar(ColaboradorRequestDTO dto) {
    // Regra de Negócio: Verificar se o CPF já está cadastrado
    boolean cpfExiste = colaboradorDAO.listarTodos().stream()
        .anyMatch(c -> c.getCpf().equals(dto.getCpf()));

    if (cpfExiste) {
      throw new IllegalArgumentException("Já existe um colaborador cadastrado com este CPF.");
    }

    Colaborador novoColaborador = new Colaborador(null, dto.getNome(), dto.getCpf(), dto.getFuncao());
    Colaborador colaboradorSalvo = colaboradorDAO.salvar(novoColaborador);

    return new ColaboradorResponseDTO(colaboradorSalvo);
  }

  public List<ColaboradorResponseDTO> listarTodos() {
    return colaboradorDAO.listarTodos().stream()
        .map(ColaboradorResponseDTO::new)
        .collect(Collectors.toList());
  }

  public ColaboradorResponseDTO buscarPorId(Long id) {
    Colaborador colaborador = colaboradorDAO.buscarPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Colaborador não encontrado para o ID: " + id));

    return new ColaboradorResponseDTO(colaborador);
  }

  public ColaboradorResponseDTO atualizar(Long id, ColaboradorRequestDTO dto) {
    Colaborador colaboradorParaAtualizar = new Colaborador(id, dto.getNome(), dto.getCpf(), dto.getFuncao());
    boolean atualizou = colaboradorDAO.atualizar(id, colaboradorParaAtualizar);

    if (!atualizou) {
      throw new IllegalArgumentException("Não foi possível atualizar. Colaborador não encontrado para o ID: " + id);
    }

    return new ColaboradorResponseDTO(colaboradorParaAtualizar);
  }

  public void deletar(Long id) {
    boolean deletou = colaboradorDAO.deletar(id);
    if (!deletou) {
      throw new IllegalArgumentException("Não foi possível deletar. Colaborador não encontrado para o ID: " + id);
    }
  }
}
