export default function ObraCard({ obra }) {
  const isEmAndamento = obra.status === 'EM_ANDAMENTO';

  return (
    <div className="bg-slate-800 p-6 rounded-2xl border border-slate-700 hover:border-blue-500 transition-all shadow-md flex justify-between items-center">
      <div>
        <span className="text-xs font-mono font-bold text-blue-400 uppercase tracking-wider">
          ID #{obra.id}
        </span>
        <h2 className="text-xl font-bold text-white mt-1">{obra.nome}</h2>
        <p className="text-slate-400 text-sm mt-1">📍 {obra.endereco}</p>
        <p className="text-emerald-400 font-semibold text-sm mt-2">
          💰 Orçamento: R$ {Number(obra.orcamento).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
        </p>
      </div>
      <div>
        <span
          className={`px-3 py-1 rounded-full text-xs font-bold ${isEmAndamento
              ? 'bg-amber-500/20 text-amber-300 border border-amber-500/30'
              : 'bg-blue-500/20 text-blue-300 border border-blue-500/30'
            }`}
        >
          {obra.status}
        </span>
      </div>
    </div>
  );
}
