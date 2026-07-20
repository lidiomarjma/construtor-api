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
}
