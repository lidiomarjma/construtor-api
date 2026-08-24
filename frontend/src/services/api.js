import axios from 'axios';

// Instância global do Axios
export const api = axios.create({
  baseURL: 'http://localhost:8080',
});

// GET /obras
export async function buscarObrasAPI() {
  const response = await api.get('/obras');
  return response.data;
}

// POST /obras
export async function criarObraAPI(dadosObra) {
  const response = await api.post('/obras', dadosObra);
  return response.data;
}

// DELETE /obras/{id} -> Exclui uma obra pelo ID
export async function deletarObraAPI(id) {
  const response = await api.delete(`/obras/${id}`);
  return response.data;
}

// PUT /obras/{id} -> Atualiza os dados de uma obra
export async function atualizarObraAPI(id, dadosObra) {
  const response = await api.put(`/obras/${id}`, dadosObra);
  return response.data;
}

// GET /clientes
export async function buscarClientesAPI() {
  const response = await api.get('/clientes');
  return response.data;
}

// POST /clientes -> Cadastra um novo cliente
export async function cadastrarClienteAPI(novoCliente) {
  const response = await api.post('/clientes', novoCliente);
  return response.data;
}

// PUT /clientes/{id} -> Atualiza dados do cliente
export async function atualizarClienteAPI(id, dadosCliente) {
  const response = await api.put(`/clientes/${id}`, dadosCliente);
  return response.data;
}

// DELETE /clientes/{id} -> Exclui um cliente pelo ID
export async function deletarClienteAPI(id) {
  const response = await api.delete(`/clientes/${id}`);
  return response.data;
}
