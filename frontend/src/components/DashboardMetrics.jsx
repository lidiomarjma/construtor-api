export default function DashboardMetrics({ obras }) {
  const totalObras = obras.length;
  const emAndamento = obras.filter((o) => o.status === 'EM_ANDAMENTO').length;
  const concluidas = obras.filter((o) => o.status === 'CONCLUIDO').length;
  const planejamento = obras.filter((o) => o.status === 'PLANEJAMENTO').length;

  const orcamentoTotal = obras.reduce((acc, obra) => acc + (Number(obra.orcamento) || 0), 0);

  const valorFormatado = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(orcamentoTotal);

  return (
    <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-6">
      <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-4 shadow-md">
        <p className="text-xs font-semibold text-slate-400">Total de Obras</p>
        <p className="text-2xl font-bold text-white mt-1">{totalObras}</p>
        <p className="text-xs text-slate-500 mt-1">
          ⏳ {emAndamento} andamento | ✅ {concluidas} concluídas | 📋 {planejamento} plan.
        </p>
      </div>

      <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-4 shadow-md">
        <p className="text-xs font-semibold text-slate-400">Investimento Total</p>
        <p className="text-2xl font-bold text-emerald-400 mt-1">{valorFormatado}</p>
        <p className="text-xs text-slate-500 mt-1">Soma dos orçamentos cadastrados</p>
      </div>

      <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-4 shadow-md">
        <p className="text-xs font-semibold text-slate-400">Taxa de Conclusão</p>
        <p className="text-2xl font-bold text-blue-400 mt-1">
          {totalObras > 0 ? `${Math.round((concluidas / totalObras) * 100)}%` : '0%'}
        </p>
        <p className="text-xs text-slate-500 mt-1">Projetos finalizados</p>
      </div>
    </div>
  );
}
