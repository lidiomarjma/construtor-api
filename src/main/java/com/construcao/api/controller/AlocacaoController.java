package com.construcao.api.controller;

import com.construcao.api.dto.AlocacaoRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.service.AlocacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alocacoes")
public class AlocacaoController {

  private final AlocacaoService alocacaoService;

  public AlocacaoController(AlocacaoService alocacaoService) {
    this.alocacaoService = alocacaoService;
  }

  // POST /alocacoes - Aloca um colaborador em uma obra
  @PostMapping
  public ResponseEntity<Void> alocar(@Valid @RequestBody AlocacaoRequestDTO dto) {
    alocacaoService.alocarColaborador(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // DELETE /alocacoes/obras/{obraId}/colaboradores/{colaboradorId} - Removendo
  // alocação
  @DeleteMapping("/obras/{obraId}/colaboradores/{colaboradorId}")
  public ResponseEntity<Void> desalocar(@PathVariable Long obraId, @PathVariable Long colaboradorId) {
    alocacaoService.desalocarColaborador(obraId, colaboradorId);
    return ResponseEntity.noContent().build();
  }

  // GET /alocacoes/obras/{obraId}/colaboradores - Lista todos os colaboradores de
  // uma obra
  @GetMapping("/obras/{obraId}/colaboradores")
  public ResponseEntity<List<ColaboradorResponseDTO>> listarColaboradoresPorObra(@PathVariable Long obraId) {
    List<ColaboradorResponseDTO> colaboradores = alocacaoService.listarColaboradoresPorObra(obraId);
    return ResponseEntity.ok(colaboradores);
  }
}
