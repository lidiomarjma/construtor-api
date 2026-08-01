package com.construcao.api.service;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.dto.ClienteRequestDTO;
import com.construcao.api.dto.ClienteResponseDTO;
import com.construcao.api.model.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

  @Mock
  private ClienteDAO clienteDAO;

  @InjectMocks
  private ClienteService clienteService;

  @Test
  @DisplayName("Deve salvar um cliente com sucesso")
  void deveSalvarClienteComSucesso() {
    // Arrange (Preparação)
    ClienteRequestDTO requestDTO = new ClienteRequestDTO("João Silva", "55999999999", "Residencial");
    Cliente clienteSalvo = new Cliente(1L, "João Silva", "55999999999", "Residencial");

    when(clienteDAO.salvar(any(Cliente.class))).thenReturn(clienteSalvo);

    // Act (Ação)
    ClienteResponseDTO responseDTO = clienteService.salvar(requestDTO);

    // Assert (Verificação)
    assertNotNull(responseDTO);
    assertEquals(1L, responseDTO.getId());
    assertEquals("João Silva", responseDTO.getNome());
    verify(clienteDAO, times(1)).salvar(any(Cliente.class));
  }

  @Test
  @DisplayName("Deve buscar cliente por ID quando ele existir")
  void deveBuscarClientePorIdQuandoExistir() {
    // Arrange
    Long id = 1L;
    Cliente cliente = new Cliente(id, "Maria Souza", "55888888888", "Comercial");
    when(clienteDAO.buscarPorId(id)).thenReturn(Optional.of(cliente));

    // Act
    ClienteResponseDTO response = clienteService.buscarPorId(id);

    // Assert
    assertNotNull(response);
    assertEquals("Maria Souza", response.getNome());
    verify(clienteDAO, times(1)).buscarPorId(id);
  }

  @Test
  @DisplayName("Deve lançar exceção ao buscar ID de cliente que não existe")
  void deveLancarExcecaoQuandoClienteNaoExistir() {
    // Arrange
    Long id = 99L;
    when(clienteDAO.buscarPorId(id)).thenReturn(Optional.empty());

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> clienteService.buscarPorId(id));

    assertEquals("Cliente não encontrado para o ID: 99", exception.getMessage());
    verify(clienteDAO, times(1)).buscarPorId(id);
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar deletar cliente inexistente")
  void deveLancarExcecaoAoDeletarClienteInexistente() {
    // Arrange
    Long id = 99L;
    when(clienteDAO.deletar(id)).thenReturn(false);

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> clienteService.deletar(id));

    assertTrue(exception.getMessage().contains("Não foi possível deletar"));
    verify(clienteDAO, times(1)).deletar(id);
  }
}
