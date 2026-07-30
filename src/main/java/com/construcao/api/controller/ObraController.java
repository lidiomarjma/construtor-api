package com.construcao.api.controller;

import com.construcao.api.dto.ObraRequestDTO;
import com.construcao.api.dto.ObraResponseDTO;
import com.construcao.api.service.ObraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras")
public class ObraController {

  private final ObraService obraService;

  public ObraController(ObraService obraService) {
    this.obraService = obraService;
  }

  @PostMapping
  public ResponseEntity<ObraResponseDTO> cadastrar(@Valid @RequestBody ObraRequestDTO dto) {
    ObraResponseDTO response = obraService.salvar(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping
  public ResponseEntity<List<ObraResponseDTO>> listarTodas() {
    return ResponseEntity.ok(obraService.listarTodas());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ObraResponseDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(obraService.buscarPorId(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObraResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ObraRequestDTO dto) {
    return ResponseEntity.ok(obraService.atualizar(id, dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    obraService.deletar(id);
    return ResponseEntity.noContent().build();
  }
}
