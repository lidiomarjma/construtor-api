package com.construcao.api.controller;

import com.construcao.api.dto.ClienteRequestDTO;
import com.construcao.api.dto.ClienteResponseDTO;
import com.construcao.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteService clienteService;

  public ClienteController(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @PostMapping
  public ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteRequestDTO dto) {
    ClienteResponseDTO response = clienteService.salvar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
    return ResponseEntity.ok(clienteService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(clienteService.buscarPorId(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
      @Valid @RequestBody ClienteRequestDTO dto) {
    return ResponseEntity.ok(clienteService.atualizar(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    clienteService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
