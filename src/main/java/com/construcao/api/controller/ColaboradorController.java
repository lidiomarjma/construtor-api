package com.construcao.api.controller;

import com.construcao.api.dao.ColaboradorDAO;
import com.construcao.api.dto.ColaboradorRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.model.Colaborador;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

  private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

  // POST - Criar novo Colaborador
  @PostMapping
  public ResponseEntity<ColaboradorResponseDTO> criar(@Valid @RequestBody ColaboradorRequestDTO dto) {
    Colaborador colaborador = new Colaborador(dto.getNome(), dto.getCpf(), dto.getFuncao());
    colaboradorDAO.salvar(colaborador);

    return ResponseEntity.status(HttpStatus.CREATED).body(new ColaboradorResponseDTO(colaborador));
  }

  // GET - Listar todos
  @GetMapping
  public ResponseEntity<List<ColaboradorResponseDTO>> listarTodos() {
    List<Colaborador> colaboradores = colaboradorDAO.listarTodos();
    List<ColaboradorResponseDTO> response = colaboradores.stream()
        .map(ColaboradorResponseDTO::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

  // GET - Buscar por ID
  @GetMapping("/{id}")
  public ResponseEntity<ColaboradorResponseDTO> buscarPorId(@PathVariable Long id) {
    Colaborador colaborador = colaboradorDAO.buscarPorId(id);

    if (colaborador == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(new ColaboradorResponseDTO(colaborador));
  }

  // PUT - Atualizar
  @PutMapping("/{id}")
  public ResponseEntity<ColaboradorResponseDTO> atualizar(@PathVariable Long id,
      @Valid @RequestBody ColaboradorRequestDTO dto) {
    Colaborador colaboradorExistente = colaboradorDAO.buscarPorId(id);

    if (colaboradorExistente == null) {
      return ResponseEntity.notFound().build();
    }

    colaboradorExistente.setNome(dto.getNome());
    colaboradorExistente.setCpf(dto.getCpf());
    colaboradorExistente.setFuncao(dto.getFuncao());

    colaboradorDAO.atualizar(colaboradorExistente);

    return ResponseEntity.ok(new ColaboradorResponseDTO(colaboradorExistente));
  }

  // DELETE - Deletar
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    boolean deletado = colaboradorDAO.deletar(id);

    if (!deletado) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.noContent().build();
  }
}
