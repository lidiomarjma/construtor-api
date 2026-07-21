package com.construcao.api.controller;

import com.construcao.api.dao.ClienteDAO;
import com.construcao.api.model.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

  private final ClienteDAO clienteDAO = new ClienteDAO();

  @GetMapping
  public List<Cliente> listar() {
    System.out.println(">>> Requesição GET recebida! Listando clientes...");
    return clienteDAO.listarTodos();
  }

  @PostMapping
  public String salvar(@RequestBody Cliente cliente) {
    clienteDAO.salvar(cliente);
    return "Cliente " + cliente.getNome() + " cadastrado com sucesso!";
  }

  @GetMapping("/{id}")
  public Cliente buscarPorId(@PathVariable Long id) {
    return clienteDAO.buscarPorId(id);
  }

  @PutMapping("/{id}")
  public String atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
    cliente.setId(id);
    boolean atualizou = clienteDAO.atualizar(cliente);
    if (atualizou) {
      return "Cliente atualizado com sucesso!";
    } else {
      return "Cliente não encontrado para atualização.";
    }
  }

  @DeleteMapping("/{id}")
  public String deletar(@PathVariable Long id) {
    boolean deletou = clienteDAO.deletar(id);
    if (deletou) {
      return "Cliente removido com sucesso!";
    } else {
      return "Cliente não encontrado para remoção.";
    }
  }
}
