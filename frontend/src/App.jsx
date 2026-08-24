import { useState, useEffect } from 'react';
import { buscarObrasAPI, criarObraAPI, deletarObraAPI, atualizarObraAPI, buscarClientesAPI } from './services/api';
import ObraForm from './components/ObraForm';
import ObraCard from './components/ObraCard';
import ClienteForm from './components/ClienteForm';
import ClienteCard from './components/ClienteCard';

export default function App() {
  const [obras, setObras] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [carregando, setCarregando] = useState(false);
  const [abaAtiva, setAbaAtiva] = useState('obras');

  async function carregarObras() {
    try {
      setCarregando(true);
      const dados = await buscarObrasAPI();
      setObras(dados);
    } catch (error) {
      console.error('Erro ao carregar obras:', error);
    } finally {
      setCarregando(false);
    }
  }

  async function carregarClientes() {
    try {
      const dados = await buscarClientesAPI();
      setClientes(dados);
    } catch (error) {
      console.error('Erro ao carregar clientes:', error);
    }
  }

  useEffect(() => {
    carregarObras();
    carregarClientes();
  }, []);

  async function handleCriarObra(novaObra) {
    try {
      setCarregando(true);
      await criarObraAPI(novaObra);
      await carregarObras();
      return true;
    } catch (error) {
      console.error('Erro ao criar obra:', error);
      alert('Erro ao salvar obra.');
      return false;
    } finally {
      setCarregando(false);
    }
  }

  async function handleDeletarObra(id) {
    try {
      await deletarObraAPI(id);
      await carregarObras();
    } catch (error) {
      console.error('Erro ao excluir obra:', error);
    }
  }

  async function handleAtualizarStatus(id, dadosAtualizados) {
    try {
      await atualizarObraAPI(id, dadosAtualizados);
      await carregarObras();
    } catch (error) {
      console.error('Erro ao atualizar status:', error);
    }
  }

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 p-6 font-sans">
      <div className="max-w-4xl mx-auto">
        <header className="mb-6 flex justify-between items-center border-b border-slate-800 pb-4">
          <div>
            <h1 className="text-3xl font-bold text-white flex items-center gap-2">
              🏗️ Construtor Web
            </h1>
            <p className="text-slate-400 text-sm mt-1">Painel de Gestão Integrado ao Spring Boot</p>
          </div>

          <button
            onClick={() => { carregarObras(); carregarClientes(); }}
            className="bg-slate-800 hover:bg-slate-700 text-slate-200 text-xs px-4 py-2 rounded-xl border border-slate-700 transition-all cursor-pointer"
          >
            🔄 Atualizar Dados
          </button>
        </header>

        {/* NAVEGAÇÃO DE ABAS */}
        <div className="flex gap-4 mb-6">
          <button
            onClick={() => setAbaAtiva('obras')}
            className={`px-5 py-2.5 rounded-xl font-semibold text-sm transition-all cursor-pointer ${abaAtiva === 'obras'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'bg-slate-800 text-slate-400 hover:bg-slate-700'
              }`}
          >
            🚧 Gestão de Obras
          </button>
          <button
            onClick={() => setAbaAtiva('clientes')}
            className={`px-5 py-2.5 rounded-xl font-semibold text-sm transition-all cursor-pointer ${abaAtiva === 'clientes'
                ? 'bg-blue-600 text-white shadow-lg'
                : 'bg-slate-800 text-slate-400 hover:bg-slate-700'
              }`}
          >
            👤 Gestão de Clientes
          </button>
        </div>

        {/* CONTEÚDO */}
        {abaAtiva === 'obras' ? (
          <div>
            <ObraForm onCriarObra={handleCriarObra} carregando={carregando} />
            <h2 className="text-xl font-bold text-white mb-4">📋 Catálogo de Obras ({obras.length})</h2>
            <div className="grid grid-cols-1 gap-4">
              {obras.map((obra) => (
                <ObraCard
                  key={obra.id}
                  obra={obra}
                  onDeletarObra={handleDeletarObra}
                  onAtualizarStatus={handleAtualizarStatus}
                />
              ))}
            </div>
          </div>
        ) : (
          <div>
            <ClienteForm onClienteCriado={carregarClientes} />
            <h2 className="text-xl font-bold text-white mb-4">👥 Clientes Cadastrados ({clientes.length})</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {clientes.map((cliente) => (
                <ClienteCard key={cliente.id} cliente={cliente} />
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
