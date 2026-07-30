package com.construcao.api.controller;

import com.construcao.api.dto.ColaboradorRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.service.ColaboradorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

  private final ColaboradorService colaboradorService;

  public ColaboradorController(ColaboradorService colaboradorService) {
    this.colaboradorService = colaboradorService;
  }

  @PostMapping
  public ResponseEntity<ColaboradorResponseDTO> criar(@Valid @RequestBody ColaboradorRequestDTO dto) {
    ColaboradorResponseDTO response = colaboradorService.criar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ColaboradorResponseDTO>> listarTodos() {
    return ResponseEntity.ok(colaboradorService.listarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ColaboradorResponseDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(colaboradorService.buscarPorId(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ColaboradorResponseDTO> atualizar(@PathVariable Long id,
      @Valid @RequestBody ColaboradorRequestDTO dto) {
    return ResponseEntity.ok(colaboradorService.atualizar(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    colaboradorService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
