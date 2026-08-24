import { useState, useEffect } from 'react';
import { buscarClientesAPI } from '../services/api';

export default function ObraForm({ onCriarObra, carregando }) {
  const [nome, setNome] = useState('');
  const [endereco, setEndereco] = useState('');
  const [orcamento, setOrcamento] = useState('');
  const [status, setStatus] = useState('EM_ANDAMENTO');
  const [clienteId, setClienteId] = useState('');

  // Estados para carregar e armazenar a lista de clientes
  const [clientes, setClientes] = useState([]);
  const [carregandoClientes, setCarregandoClientes] = useState(true);

  // Busca a lista de clientes ao carregar o componente
  useEffect(() => {
    async function carregarListaClientes() {
      try {
        setCarregandoClientes(true);
        const dados = await buscarClientesAPI();
        setClientes(dados);

        // Se houver clientes cadastrados, seleciona o primeiro por padrão
        if (dados.length > 0) {
          setClienteId(dados[0].id);
        }
      } catch (error) {
        console.error('Erro ao carregar clientes:', error);
      } finally {
        setCarregandoClientes(false);
      }
    }

    carregarListaClientes();
  }, []);

  async function handleSubmit(event) {
    event.preventDefault(); // Impede o reload padrão da página HTML
    if (!nome || !endereco || !orcamento || !clienteId) return;

    const novaObra = {
      nome: nome.trim(),
      endereco: endereco.trim(),
      orcamento: Number(orcamento),
      status: status,
      clienteId: Number(clienteId),
    };

    const sucesso = await onCriarObra(novaObra);
    if (sucesso) {
      // Limpa os campos após o envio bem-sucedido
      setNome('');
      setEndereco('');
      setOrcamento('');
      setStatus('EM_ANDAMENTO');
      // Mantém o primeiro cliente selecionado se a lista não estiver vazia
      if (clientes.length > 0) {
        setClienteId(clientes[0].id);
      }
    }
  }

  return (
    <form onSubmit={handleSubmit} className="bg-slate-800 p-6 rounded-2xl border border-slate-700 shadow-lg mb-8">
      <h2 className="text-xl font-bold text-white mb-4">➕ Cadastrar Nova Obra</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Nome da Obra</label>
          <input
            type="text"
            required
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            placeholder="Ex: Reforma do Galpão"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Endereço</label>
          <input
            type="text"
            required
            value={endereco}
            onChange={(e) => setEndereco(e.target.value)}
            placeholder="Ex: Av. Principal, 1000"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Orçamento (R$)</label>
          <input
            type="number"
            step="0.01"
            required
            value={orcamento}
            onChange={(e) => setOrcamento(e.target.value)}
            placeholder="Ex: 45000.00"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        {/* SELECT DINÂMICO DE CLIENTES */}
        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Cliente Responsável</label>
          <select
            required
            value={clienteId}
            onChange={(e) => setClienteId(e.target.value)}
            disabled={carregandoClientes || clientes.length === 0}
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500 disabled:opacity-50"
          >
            {carregandoClientes ? (
              <option value="">Carregando clientes...</option>
            ) : clientes.length === 0 ? (
              <option value="">Nenhum cliente cadastrado no sistema</option>
            ) : (
              clientes.map((cliente) => (
                <option key={cliente.id} value={cliente.id}>
                  {cliente.nome} {cliente.email ? `(${cliente.email})` : ''}
                </option>
              ))
            )}
          </select>
        </div>

        <div className="md:col-span-2">
          <label className="block text-xs font-semibold text-slate-400 mb-1">Status</label>
          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          >
            <option value="EM_ANDAMENTO">EM_ANDAMENTO</option>
            <option value="CONCLUIDO">CONCLUIDO</option>
            <option value="PLANEJAMENTO">PLANEJAMENTO</option>
          </select>
        </div>
      </div>

      <button
        type="submit"
        disabled={carregando || carregandoClientes || clientes.length === 0}
        className="mt-4 w-full bg-emerald-600 hover:bg-emerald-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-semibold py-2 rounded-xl transition-all shadow-md cursor-pointer"
      >
        {carregando ? 'Salvando...' : 'Salvar Obra'}
      </button>

      {clientes.length === 0 && !carregandoClientes && (
        <p className="text-xs text-amber-400 mt-2 text-center">
          ⚠️ Crie um cliente antes de cadastrar uma nova obra.
        </p>
      )}
    </form>
  );
}
