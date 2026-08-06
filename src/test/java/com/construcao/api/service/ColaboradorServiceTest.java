package com.construcao.api.service;

import com.construcao.api.dao.ColaboradorDAO;
import com.construcao.api.dto.ColaboradorRequestDTO;
import com.construcao.api.dto.ColaboradorResponseDTO;
import com.construcao.api.model.Colaborador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColaboradorServiceTest {

  @Mock
  private ColaboradorDAO colaboradorDAO;

  @InjectMocks
  private ColaboradorService colaboradorService;

  @Test
  @DisplayName("Deve criar um colaborador com sucesso")
  void deveCriarColaboradorComSucesso() {
    ColaboradorRequestDTO request = new ColaboradorRequestDTO("João Silva", "12345678900", "Engenheiro");
    Colaborador colaboradorSalvo = new Colaborador(1L, "João Silva", "12345678900", "Engenheiro");

    when(colaboradorDAO.listarTodos()).thenReturn(Collections.emptyList());
    when(colaboradorDAO.salvar(any(Colaborador.class))).thenReturn(colaboradorSalvo);

    ColaboradorResponseDTO response = colaboradorService.criar(request);

    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("João Silva", response.getNome());
    assertEquals("12345678900", response.getCpf());
    assertEquals("Engenheiro", response.getFuncao());

    verify(colaboradorDAO, times(1)).listarTodos();
    verify(colaboradorDAO, times(1)).salvar(any(Colaborador.class));
  }

  @Test
  @DisplayName("Deve buscar colaborador por ID com sucesso")
  void deveBuscarColaboradorPorIdComSucesso() {
    Long id = 1L;
    Colaborador colaborador = new Colaborador(id, "Maria Souza", "98765432100", "Mestre de Obras");

    when(colaboradorDAO.buscarPorId(id)).thenReturn(Optional.of(colaborador));

    ColaboradorResponseDTO response = colaboradorService.buscarPorId(id);

    assertNotNull(response);
    assertEquals(id, response.getId());
    assertEquals("Maria Souza", response.getNome());
    assertEquals("98765432100", response.getCpf());
    assertEquals("Mestre de Obras", response.getFuncao());

    verify(colaboradorDAO, times(1)).buscarPorId(id);
  }

  @Test
  @DisplayName("Deve lançar exceção ao buscar colaborador por ID inexistente")
  void deveLancarExcecaoQuandoColaboradorNaoEncontrado() {
    Long idInexistente = 99L;
    when(colaboradorDAO.buscarPorId(idInexistente)).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> colaboradorService.buscarPorId(idInexistente));

    assertTrue(exception.getMessage().contains("Colaborador não encontrado"));
    verify(colaboradorDAO, times(1)).buscarPorId(idInexistente);
  }

  @Test
  @DisplayName("Deve listar todos os colaboradores")
  void deveListarTodosOsColaboradores() {
    List<Colaborador> lista = List.of(
        new Colaborador(1L, "João Silva", "12345678900", "Engenheiro"),
        new Colaborador(2L, "Maria Souza", "98765432100", "Mestre de Obras"));

    when(colaboradorDAO.listarTodos()).thenReturn(lista);

    List<ColaboradorResponseDTO> response = colaboradorService.listarTodos();

    assertNotNull(response);
    assertEquals(2, response.size());
    verify(colaboradorDAO, times(1)).listarTodos();
  }
}
