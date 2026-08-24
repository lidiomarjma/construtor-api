import { useState } from 'react';

export default function ObraCard({ obra, onDeletarObra, onAtualizarStatus }) {
  const [deletando, setDeletando] = useState(false);
  const [atualizando, setAtualizando] = useState(false);
  const [confirmandoExclusao, setConfirmandoExclusao] = useState(false);

  const orcamentoFormatado = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(obra.orcamento);

  const statusCores = {
    EM_ANDAMENTO: 'bg-amber-500/10 text-amber-400 border-amber-500/20',
    CONCLUIDO: 'bg-emerald-500/10 text-emerald-400 border-emerald-500/20',
    PLANEJAMENTO: 'bg-blue-500/10 text-blue-400 border-blue-500/20',
  };

  async function handleMudarStatus(e) {
    const novoStatus = e.target.value;
    try {
      setAtualizando(true);
      await onAtualizarStatus(obra.id, {
        ...obra,
        status: novoStatus,
      });
    } catch (error) {
      console.error('Erro ao atualizar status:', error);
    } finally {
      setAtualizando(false);
    }
  }

  async function ExecutarExclusao() {
    try {
      setDeletando(true);
      await onDeletarObra(obra.id);
    } catch (error) {
      console.error('Erro ao deletar:', error);
      setConfirmandoExclusao(false);
    } finally {
      setDeletando(false);
    }
  }

  return (
    <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-5 shadow-md hover:border-slate-600 transition-all">
      <div className="flex justify-between items-start mb-3">
        <div>
          <span className="text-xs font-mono text-slate-500">ID #{obra.id}</span>
          <h3 className="text-lg font-bold text-white leading-tight">{obra.nome}</h3>
        </div>

        <div className="flex items-center gap-2">
          {/* SELETOR DE STATUS INLINE */}
          <select
            value={obra.status}
            onChange={handleMudarStatus}
            disabled={atualizando}
            className={`text-xs px-2.5 py-1 rounded-full border font-medium bg-slate-900 cursor-pointer outline-none transition-all disabled:opacity-50 ${statusCores[obra.status] || 'bg-slate-700 text-slate-300'
              }`}
          >
            <option value="EM_ANDAMENTO" className="bg-slate-800 text-amber-400">EM_ANDAMENTO</option>
            <option value="CONCLUIDO" className="bg-slate-800 text-emerald-400">CONCLUIDO</option>
            <option value="PLANEJAMENTO" className="bg-slate-800 text-blue-400">PLANEJAMENTO</option>
          </select>

          {!confirmandoExclusao && (
            <button
              onClick={() => setConfirmandoExclusao(true)}
              className="text-slate-400 hover:text-rose-400 p-1.5 rounded-lg hover:bg-slate-700/50 transition-all cursor-pointer"
              title="Excluir Obra"
            >
              🗑️
            </button>
          )}
        </div>
      </div>

      <div className="space-y-1 text-sm text-slate-300">
        <p className="flex items-center gap-1.5 text-xs text-slate-400">
          📍 {obra.endereco}
        </p>
        <p className="font-semibold text-emerald-400 pt-1">
          💰 Orçamento: {orcamentoFormatado}
        </p>
      </div>

      {confirmandoExclusao && (
        <div className="mt-4 pt-3 border-t border-slate-700/60 flex items-center justify-between bg-rose-500/10 p-3 rounded-xl border border-rose-500/20">
          <span className="text-xs text-rose-300 font-medium">
            Confirmar exclusão desta obra?
          </span>
          <div className="flex gap-2">
            <button
              onClick={() => setConfirmandoExclusao(false)}
              disabled={deletando}
              className="text-xs px-3 py-1.5 rounded-lg bg-slate-700 hover:bg-slate-600 text-slate-200 font-medium transition-all cursor-pointer"
            >
              Cancelar
            </button>
            <button
              onClick={ExecutarExclusao}
              disabled={deletando}
              className="text-xs px-3 py-1.5 rounded-lg bg-rose-600 hover:bg-rose-500 text-white font-semibold transition-all shadow-md cursor-pointer disabled:opacity-50"
            >
              {deletando ? 'Excluindo...' : 'Sim, Excluir'}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
