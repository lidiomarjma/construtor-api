package com.construcao.api.controller;

import com.construcao.api.dao.ObraDAO;
import com.construcao.api.dto.ObraRequestDTO;
import com.construcao.api.dto.ObraResponseDTO;
import com.construcao.api.model.Obra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/obras")
public class ObraController {

  private final ObraDAO obraDAO;

  public ObraController(ObraDAO obraDAO) {
    this.obraDAO = obraDAO;
  }

  @PostMapping
  public ResponseEntity<ObraResponseDTO> criar(@RequestBody ObraRequestDTO dto) {
    Obra obra = new Obra(null, dto.getNome(), dto.getEndereco(), dto.getOrcamento(), dto.getStatus());
    Obra obraSalva = obraDAO.salvar(obra);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ObraResponseDTO(obraSalva));
  }

  @GetMapping
  public ResponseEntity<List<ObraResponseDTO>> listarTodos() {
    List<ObraResponseDTO> obras = obraDAO.buscarTodas()
        .stream()
        .map(ObraResponseDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(obras);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ObraResponseDTO> buscarPorId(@PathVariable Long id) {
    Optional<Obra> obraOptional = obraDAO.buscarPorId(id);
    if (obraOptional.isPresent()) {
      return ResponseEntity.ok(new ObraResponseDTO(obraOptional.get()));
    }
    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ObraResponseDTO> atualizar(@PathVariable Long id, @RequestBody ObraRequestDTO dto) {
    Obra obraParaAtualizar = new Obra(id, dto.getNome(), dto.getEndereco(), dto.getOrcamento(), dto.getStatus());
    boolean atualizado = obraDAO.atualizar(id, obraParaAtualizar);

    if (atualizado) {
      return ResponseEntity.ok(new ObraResponseDTO(obraParaAtualizar));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    boolean deletado = obraDAO.deletar(id);
    if (deletado) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

}
