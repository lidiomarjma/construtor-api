import { useState } from 'react';
import { cadastrarClienteAPI } from '../services/api';

export default function ClienteForm({ onClienteCriado }) {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [telefone, setTelefone] = useState('');
  const [tipoServico, setTipoServico] = useState('CONSTRUCAO');
  const [carregando, setCarregando] = useState(false);

  // Estado para armazenar mensagens de feedback (sucesso/erro)
  const [mensagem, setMensagem] = useState(null);

  async function handleSubmit(event) {
    event.preventDefault();
    if (!nome || !email || !telefone) return;

    setMensagem(null); // Limpa mensagens anteriores

    try {
      setCarregando(true);
      const payload = {
        nome: nome.trim(),
        email: email.trim(),
        telefone: telefone.trim(),
        tipoServico: tipoServico,
      };

      await cadastrarClienteAPI(payload);

      // Limpa os campos
      setNome('');
      setEmail('');
      setTelefone('');
      setTipoServico('CONSTRUCAO');

      // Define mensagem de sucesso estilizada
      setMensagem({
        tipo: 'sucesso',
        texto: '✅ Cliente cadastrado com sucesso! Já está disponível para seleção em Obras.',
      });

      if (onClienteCriado) onClienteCriado();
    } catch (error) {
      console.error('Erro ao cadastrar cliente:', error);

      // Define mensagem de erro estilizada
      setMensagem({
        tipo: 'erro',
        texto: '❌ Erro ao cadastrar cliente. Verifique se os dados estão corretos e tente novamente.',
      });
    } finally {
      setCarregando(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="bg-slate-800 p-6 rounded-2xl border border-slate-700 shadow-lg mb-8">
      <h2 className="text-xl font-bold text-white mb-4">👤 Cadastrar Novo Cliente</h2>

      {/* COMPONENTE DE MENSAGEM / FEEDBACK INLINE */}
      {mensagem && (
        <div
          className={`mb-4 p-3 rounded-xl text-sm font-medium transition-all ${mensagem.tipo === 'sucesso'
              ? 'bg-emerald-500/10 border border-emerald-500/30 text-emerald-400'
              : 'bg-rose-500/10 border border-rose-500/30 text-rose-400'
            }`}
        >
          {mensagem.texto}
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Nome Completo / Empresa</label>
          <input
            type="text"
            required
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            placeholder="Ex: João da Silva"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">E-mail</label>
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Ex: cliente@email.com"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Telefone / WhatsApp</label>
          <input
            type="text"
            required
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            placeholder="Ex: 55999999999"
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          />
        </div>

        <div>
          <label className="block text-xs font-semibold text-slate-400 mb-1">Tipo de Serviço</label>
          <select
            value={tipoServico}
            onChange={(e) => setTipoServico(e.target.value)}
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-2 text-white outline-none focus:border-blue-500"
          >
            <option value="CONSTRUCAO">CONSTRUCAO</option>
            <option value="REFORMA">REFORMA</option>
            <option value="CONSULTORIA">CONSULTORIA</option>
          </select>
        </div>
      </div>

      <button
        type="submit"
        disabled={carregando}
        className="mt-4 w-full bg-blue-600 hover:bg-blue-500 disabled:opacity-50 text-white font-semibold py-2 rounded-xl transition-all shadow-md cursor-pointer"
      >
        {carregando ? 'Cadastrando...' : 'Salvar Cliente'}
      </button>
    </form>
  );
}
