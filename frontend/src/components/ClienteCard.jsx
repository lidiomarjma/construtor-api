import { useState } from 'react';

export default function ClienteCard({ cliente, onDeletar, onAtualizar }) {
  const [editando, setEditando] = useState(false);
  const [confirmandoExclusao, setConfirmandoExclusao] = useState(false);
  const [carregando, setCarregando] = useState(false);

  // Estados dos campos em edição
  const [nome, setNome] = useState(cliente.nome);
  const [email, setEmail] = useState(cliente.email || '');
  const [telefone, setTelefone] = useState(cliente.telefone || '');
  const [tipoServico, setTipoServico] = useState(cliente.tipoServico || 'CONSTRUCAO');

  async function handleSalvarEdicao(e) {
    e.preventDefault();
    try {
      setCarregando(true);
      await onAtualizar(cliente.id, { nome, email, telefone, tipoServico });
      setEditando(false);
    } catch (error) {
      console.error('Erro ao editar cliente:', error);
    } finally {
      setCarregando(false);
    }
  }

  async function handleExcluir() {
    try {
      setCarregando(true);
      await onDeletar(cliente.id);
    } catch (error) {
      console.error('Erro ao excluir cliente:', error);
      setConfirmandoExclusao(false);
    } finally {
      setCarregando(false);
    }
  }

  if (editando) {
    return (
      <form onSubmit={handleSalvarEdicao} className="bg-slate-800 border border-blue-500/50 rounded-2xl p-5 shadow-lg space-y-3">
        <h4 className="text-sm font-bold text-blue-400">✏️ Editando Cliente #{cliente.id}</h4>

        <div>
          <input
            type="text"
            required
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-1.5 text-xs text-white outline-none focus:border-blue-500"
            placeholder="Nome"
          />
        </div>

        <div className="grid grid-cols-2 gap-2">
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="bg-slate-900 border border-slate-700 rounded-lg px-3 py-1.5 text-xs text-white outline-none focus:border-blue-500"
            placeholder="E-mail"
          />
          <input
            type="text"
            required
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            className="bg-slate-900 border border-slate-700 rounded-lg px-3 py-1.5 text-xs text-white outline-none focus:border-blue-500"
            placeholder="Telefone"
          />
        </div>

        <div>
          <select
            value={tipoServico}
            onChange={(e) => setTipoServico(e.target.value)}
            className="w-full bg-slate-900 border border-slate-700 rounded-lg px-3 py-1.5 text-xs text-white outline-none focus:border-blue-500"
          >
            <option value="CONSTRUCAO">CONSTRUCAO</option>
            <option value="REFORMA">REFORMA</option>
            <option value="CONSULTORIA">CONSULTORIA</option>
          </select>
        </div>

        <div className="flex justify-end gap-2 pt-2">
          <button
            type="button"
            onClick={() => setEditando(false)}
            disabled={carregando}
            className="text-xs px-3 py-1.5 rounded-lg bg-slate-700 hover:bg-slate-600 text-slate-200 transition-all cursor-pointer"
          >
            Cancelar
          </button>
          <button
            type="submit"
            disabled={carregando}
            className="text-xs px-3 py-1.5 rounded-lg bg-blue-600 hover:bg-blue-500 text-white font-semibold transition-all shadow-md cursor-pointer disabled:opacity-50"
          >
            {carregando ? 'Salvando...' : 'Salvar Alterações'}
          </button>
        </div>
      </form>
    );
  }

  return (
    <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-5 shadow-md flex flex-col justify-between">
      <div>
        <div className="flex justify-between items-start mb-2">
          <div className="flex items-center gap-2">
            <span className="text-xs font-mono text-slate-500">ID #{cliente.id}</span>
            <span className="text-xs px-2 py-0.5 rounded-full border bg-blue-500/10 text-blue-400 border-blue-500/20 font-medium">
              {cliente.tipoServico || 'N/A'}
            </span>
          </div>

          <div className="flex items-center gap-1">
            <button
              onClick={() => setEditando(true)}
              className="text-slate-400 hover:text-blue-400 p-1 rounded-lg hover:bg-slate-700/50 transition-all cursor-pointer text-xs"
              title="Editar Cliente"
            >
              ✏️
            </button>
            {!confirmandoExclusao && (
              <button
                onClick={() => setConfirmandoExclusao(true)}
                className="text-slate-400 hover:text-rose-400 p-1 rounded-lg hover:bg-slate-700/50 transition-all cursor-pointer text-xs"
                title="Excluir Cliente"
              >
                🗑️
              </button>
            )}
          </div>
        </div>

        <h3 className="text-lg font-bold text-white">{cliente.nome}</h3>
        <p className="text-xs text-slate-400 mt-1">✉️ {cliente.email || 'Sem e-mail'}</p>
        <p className="text-xs text-slate-400">📞 {cliente.telefone || 'Sem telefone'}</p>
      </div>

      {confirmandoExclusao && (
        <div className="mt-4 pt-3 border-t border-slate-700/60 flex items-center justify-between bg-rose-500/10 p-2.5 rounded-xl border border-rose-500/20">
          <span className="text-xs text-rose-300 font-medium">Excluir cliente?</span>
          <div className="flex gap-2">
            <button
              onClick={() => setConfirmandoExclusao(false)}
              disabled={carregando}
              className="text-xs px-2.5 py-1 rounded-lg bg-slate-700 text-slate-200 transition-all cursor-pointer"
            >
              Não
            </button>
            <button
              onClick={handleExcluir}
              disabled={carregando}
              className="text-xs px-2.5 py-1 rounded-lg bg-rose-600 text-white font-semibold transition-all cursor-pointer disabled:opacity-50"
            >
              {carregando ? '...' : 'Sim'}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
