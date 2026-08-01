package com.construcao.api.service;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.dao.ObraDAO;
import com.construcao.api.dto.ObraRequestDTO;
import com.construcao.api.dto.ObraResponseDTO;
import com.construcao.api.model.Cliente;
import com.construcao.api.model.Obra;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObraServiceTest {

  @Mock
  private ObraDAO obraDAO;

  @Mock
  private ClienteDAO clienteDAO;

  @InjectMocks
  private ObraService obraService;

  @Test
  @DisplayName("Deve salvar uma obra com sucesso quando o cliente existir")
  void deveSalvarObraComSucesso() {
    // Arrange
    Long clienteId = 1L;
    ObraRequestDTO request = new ObraRequestDTO("Reforma Predial", "Rua A, 123", new BigDecimal("50000.00"),
        "EM_ANDAMENTO", clienteId);
    Cliente clienteExistente = new Cliente(clienteId, "Empresa X", "55999999999", "Comercial");
    Obra obraSalva = new Obra(10L, "Reforma Predial", "Rua A, 123", new BigDecimal("50000.00"), "EM_ANDAMENTO",
        clienteId);

    when(clienteDAO.buscarPorId(clienteId)).thenReturn(Optional.of(clienteExistente));
    when(obraDAO.salvar(any(Obra.class))).thenReturn(obraSalva);

    // Act
    ObraResponseDTO response = obraService.salvar(request);

    // Assert
    assertNotNull(response);
    assertEquals(10L, response.getId());
    assertEquals("Reforma Predial", response.getNome());
    verify(clienteDAO, times(1)).buscarPorId(clienteId);
    verify(obraDAO, times(1)).salvar(any(Obra.class));
  }

  @Test
  @DisplayName("Deve lançar exceção ao tentar cadastrar obra para cliente inexistente")
  void deveLancarExcecaoQuandoClienteNaoExistir() {
    // Arrange
    Long clienteInexistenteId = 99L;
    ObraRequestDTO request = new ObraRequestDTO("Reforma Predial", "Rua A, 123", new BigDecimal("50000.00"),
        "EM_ANDAMENTO", clienteInexistenteId);

    when(clienteDAO.buscarPorId(clienteInexistenteId)).thenReturn(Optional.empty());

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> obraService.salvar(request));

    assertTrue(exception.getMessage().contains("Cliente não encontrado"));
    verify(obraDAO, never()).salvar(any(Obra.class));
  }
}
