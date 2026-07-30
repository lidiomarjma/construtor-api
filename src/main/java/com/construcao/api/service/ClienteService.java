package com.construcao.api.service;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.dto.ClienteRequestDTO;
import com.construcao.api.dto.ClienteResponseDTO;
import com.construcao.api.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

  private final ClienteDAO clienteDAO;

  public ClienteService(ClienteDAO clienteDAO) {
    this.clienteDAO = clienteDAO;
  }

  public ClienteResponseDTO salvar(ClienteRequestDTO dto) {
    Cliente novoCliente = new Cliente(null, dto.getNome(), dto.getTelefone(), dto.getTipoServico());
    Cliente clienteSalvo = clienteDAO.salvar(novoCliente);
    return new ClienteResponseDTO(clienteSalvo);
  }

  public List<ClienteResponseDTO> listarTodos() {
    return clienteDAO.listarTodos().stream()
        .map(ClienteResponseDTO::new)
        .collect(Collectors.toList());
  }

  public ClienteResponseDTO buscarPorId(Long id) {
    Cliente cliente = clienteDAO.buscarPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o ID: " + id));

    return new ClienteResponseDTO(cliente);
  }

  public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
    Cliente clienteParaAtualizar = new Cliente(id, dto.getNome(), dto.getTelefone(), dto.getTipoServico());
    boolean atualizou = clienteDAO.atualizar(id, clienteParaAtualizar);

    if (!atualizou) {
      throw new IllegalArgumentException("Não foi possível atualizar. Cliente não encontrado para o ID: " + id);
    }

    return new ClienteResponseDTO(clienteParaAtualizar);
  }

  public void deletar(Long id) {
    boolean deletou = clienteDAO.deletar(id);
    if (!deletou) {
      throw new IllegalArgumentException("Não foi possível deletar. Cliente não encontrado para o ID: " + id);
    }
  }
}
