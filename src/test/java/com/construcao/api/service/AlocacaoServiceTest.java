package com.construcao.api.service;

import com.construcao.api.dao.AlocacaoDAO;
import com.construcao.api.dao.ColaboradorDAO;
import com.construcao.api.dao.ObraDAO;
import com.construcao.api.dto.AlocacaoRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.model.Colaborador;
import com.construcao.api.model.Obra;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlocacaoServiceTest {

  @Mock
  private AlocacaoDAO alocacaoDAO;

  @Mock
  private ObraDAO obraDAO;

  @Mock
  private ColaboradorDAO colaboradorDAO;

  @InjectMocks
  private AlocacaoService alocacaoService;

  // --- TESTES DE ALOCAÇÃO ---

  @Test
  @DisplayName("Deve alocar colaborador na obra com sucesso")
  void deveAlocarColaboradorComSucesso() {
    Long obraId = 10L;
    Long colaboradorId = 5L;
    AlocacaoRequestDTO request = new AlocacaoRequestDTO(obraId, colaboradorId);

    Obra obraExistente = new Obra(obraId, "Reforma Central", "Rua A, 123", new BigDecimal("100000.00"), "EM_ANDAMENTO",
        1L);
    Colaborador colaboradorExistente = new Colaborador(colaboradorId, "Carlos Silva", "12345678900", "Pedreiro");

    when(obraDAO.buscarPorId(obraId)).thenReturn(Optional.of(obraExistente));
    when(colaboradorDAO.buscarPorId(colaboradorId)).thenReturn(Optional.of(colaboradorExistente));
    when(alocacaoDAO.isAlocado(obraId, colaboradorId)).thenReturn(false);

    assertDoesNotThrow(() -> alocacaoService.alocarColaborador(request));

    verify(obraDAO, times(1)).buscarPorId(obraId);
    verify(colaboradorDAO, times(1)).buscarPorId(colaboradorId);
    verify(alocacaoDAO, times(1)).isAlocado(obraId, colaboradorId);
    verify(alocacaoDAO, times(1)).alocar(obraId, colaboradorId);
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar alocar em obra inexistente")
  void deveLancarExcecaoQuandoObraNaoExistir() {
    Long obraInexistenteId = 99L;
    Long colaboradorId = 5L;
    AlocacaoRequestDTO request = new AlocacaoRequestDTO(obraInexistenteId, colaboradorId);

    when(obraDAO.buscarPorId(obraInexistenteId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> alocacaoService.alocarColaborador(request));

    assertTrue(exception.getMessage().contains("Obra não encontrada"));
    verify(obraDAO, times(1)).buscarPorId(obraInexistenteId);
    verify(colaboradorDAO, never()).buscarPorId(anyLong());
    verify(alocacaoDAO, never()).alocar(anyLong(), anyLong());
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar alocar colaborador inexistente")
  void deveLancarExcecaoQuandoColaboradorNaoExistir() {
    Long obraId = 10L;
    Long colaboradorInexistenteId = 88L;
    AlocacaoRequestDTO request = new AlocacaoRequestDTO(obraId, colaboradorInexistenteId);

    Obra obraExistente = new Obra(obraId, "Reforma Central", "Rua A, 123", new BigDecimal("100000.00"), "EM_ANDAMENTO",
        1L);

    when(obraDAO.buscarPorId(obraId)).thenReturn(Optional.of(obraExistente));
    when(colaboradorDAO.buscarPorId(colaboradorInexistenteId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> alocacaoService.alocarColaborador(request));

    assertTrue(exception.getMessage().contains("Colaborador não encontrado"));
    verify(obraDAO, times(1)).buscarPorId(obraId);
    verify(colaboradorDAO, times(1)).buscarPorId(colaboradorInexistenteId);
    verify(alocacaoDAO, never()).alocar(anyLong(), anyLong());
  }

  @Test
  @DisplayName("Deve lançar exceção quando colaborador já estiver alocado na mesma obra")
  void deveLancarExcecaoQuandoColaboradorJaEstiverAlocado() {
    Long obraId = 10L;
    Long colaboradorId = 5L;
    AlocacaoRequestDTO request = new AlocacaoRequestDTO(obraId, colaboradorId);

    Obra obraExistente = new Obra(obraId, "Reforma Central", "Rua A, 123", new BigDecimal("100000.00"), "EM_ANDAMENTO",
        1L);
    Colaborador colaboradorExistente = new Colaborador(colaboradorId, "Carlos Silva", "12345678900", "Pedreiro");

    when(obraDAO.buscarPorId(obraId)).thenReturn(Optional.of(obraExistente));
    when(colaboradorDAO.buscarPorId(colaboradorId)).thenReturn(Optional.of(colaboradorExistente));
    when(alocacaoDAO.isAlocado(obraId, colaboradorId)).thenReturn(true);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> alocacaoService.alocarColaborador(request));

    assertEquals("Este colaborador já está alocado nesta obra.", exception.getMessage());
    verify(alocacaoDAO, never()).alocar(anyLong(), anyLong());
  }

  // --- TESTES DE DESALOCAÇÃO ---

  @Test
  @DisplayName("Deve desalocar colaborador com sucesso")
  void deveDesalocarColaboradorComSucesso() {
    Long obraId = 10L;
    Long colaboradorId = 5L;

    when(alocacaoDAO.desalocar(obraId, colaboradorId)).thenReturn(true);

    assertDoesNotThrow(() -> alocacaoService.desalocarColaborador(obraId, colaboradorId));

    verify(alocacaoDAO, times(1)).desalocar(obraId, colaboradorId);
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar desalocar vínculo inexistente")
  void deveLancarExcecaoAoDesalocarVinculoInexistente() {
    Long obraId = 10L;
    Long colaboradorId = 5L;

    when(alocacaoDAO.desalocar(obraId, colaboradorId)).thenReturn(false);

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> alocacaoService.desalocarColaborador(obraId, colaboradorId));

    assertTrue(exception.getMessage().contains("Vínculo não encontrado"));
    verify(alocacaoDAO, times(1)).desalocar(obraId, colaboradorId);
  }

  // --- TESTES DE LISTAGEM ---

  @Test
  @DisplayName("Deve listar colaboradores de uma obra existente com sucesso")
  void deveListarColaboradoresPorObraComSucesso() {
    Long obraId = 10L;
    Obra obraExistente = new Obra(obraId, "Reforma Central", "Rua A, 123", new BigDecimal("100000.00"), "EM_ANDAMENTO",
        1L);
    List<Colaborador> colaboradores = List.of(
        new Colaborador(1L, "João Silva", "12345678900", "Engenheiro"),
        new Colaborador(2L, "Maria Souza", "98765432100", "Mestre de Obras"));

    when(obraDAO.buscarPorId(obraId)).thenReturn(Optional.of(obraExistente));
    when(alocacaoDAO.listarColaboradoresDaObra(obraId)).thenReturn(colaboradores);

    List<ColaboradorResponseDTO> response = alocacaoService.listarColaboradoresPorObra(obraId);

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals("João Silva", response.get(0).getNome());
    assertEquals("Maria Souza", response.get(1).getNome());

    verify(obraDAO, times(1)).buscarPorId(obraId);
    verify(alocacaoDAO, times(1)).listarColaboradoresDaObra(obraId);
  }

  @Test
  @DisplayName("Deve lançar exceção ao listar colaboradores de obra inexistente")
  void deveLancarExcecaoAoListarColaboradoresDeObraInexistente() {
    Long obraInexistenteId = 99L;

    when(obraDAO.buscarPorId(obraInexistenteId)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> alocacaoService.listarColaboradoresPorObra(obraInexistenteId));

    assertTrue(exception.getMessage().contains("Obra não encontrada"));
    verify(obraDAO, times(1)).buscarPorId(obraInexistenteId);
    verify(alocacaoDAO, never()).listarColaboradoresDaObra(anyLong());
  }
}
