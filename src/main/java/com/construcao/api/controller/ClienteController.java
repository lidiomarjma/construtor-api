package com.construcao.api.controller;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.dto.ClienteRequestDTO;
import com.construcao.api.dto.ClienteResponseDTO;
import com.construcao.api.model.Cliente;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteDAO clienteDAO = new ClienteDAO();

  // 1. LISTAR TODOS (GET /clientes)
  @GetMapping
  public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
    List<Cliente> clientes = clienteDAO.listarTodos();

    // Converte a lista de Cliente (Model) em uma lista de ClienteResponseDTO
    List<ClienteResponseDTO> responseList = clientes.stream()
        .map(ClienteResponseDTO::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok(responseList);
  }

  // 2. BUSCAR POR ID (GET /clientes/{id})
  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
    Cliente cliente = clienteDAO.buscarPorId(id);

    if (cliente != null) {
      return ResponseEntity.ok(new ClienteResponseDTO(cliente));
    } else {
      return ResponseEntity.notFound().build(); // HTTP 404 Not Found
    }
  }

  // 3. CADASTRAR (POST /clientes)
  @PostMapping
  public ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteRequestDTO dto) {
    // Converte o DTO recebido para a Entidade Cliente
    Cliente novoCliente = new Cliente(dto.getNome(), dto.getTelefone(), dto.getTipoServico());

    // Chama o salvar do DAO (que é void)
    clienteDAO.salvar(novoCliente);

    // Retorna o DTO com Status 201 Created
    return ResponseEntity.status(HttpStatus.CREATED).body(new ClienteResponseDTO(novoCliente));
  }

  // 4. ATUALIZAR (PUT /clientes/{id})
  @PutMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
      @Valid @RequestBody ClienteRequestDTO dto) {
    // Verifica se o cliente existe antes de atualizar
    Cliente clienteExistente = clienteDAO.buscarPorId(id);
    if (clienteExistente == null) {
      return ResponseEntity.notFound().build(); // HTTP 404
    }

    // Atualiza os dados do cliente com o que veio no DTO
    clienteExistente.setNome(dto.getNome());
    clienteExistente.setTelefone(dto.getTelefone());
    clienteExistente.setTipoServico(dto.getTipoServico());

    boolean atualizou = clienteDAO.atualizar(clienteExistente);

    if (atualizou) {
      return ResponseEntity.ok(new ClienteResponseDTO(clienteExistente));
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  // 5. DELETAR (DELETE /clientes/{id})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    boolean deletou = clienteDAO.deletar(id);

    if (deletou) {
      return ResponseEntity.noContent().build(); // HTTP 204 No Content
    } else {
      return ResponseEntity.notFound().build(); // HTTP 404 Not Found
    }
  }
}
