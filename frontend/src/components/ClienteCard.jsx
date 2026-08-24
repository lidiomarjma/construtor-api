export default function ClienteCard({ cliente }) {
  return (
    <div className="bg-slate-800 border border-slate-700/60 rounded-2xl p-5 shadow-md flex justify-between items-center">
      <div>
        <div className="flex items-center gap-2 mb-1">
          <span className="text-xs font-mono text-slate-500">ID #{cliente.id}</span>
          <span className="text-xs px-2 py-0.5 rounded-full border bg-blue-500/10 text-blue-400 border-blue-500/20 font-medium">
            {cliente.tipoServico || 'N/A'}
          </span>
        </div>
        <h3 className="text-lg font-bold text-white">{cliente.nome}</h3>
        <p className="text-xs text-slate-400 mt-1">✉️ {cliente.email || 'Sem e-mail'}</p>
        <p className="text-xs text-slate-400">📞 {cliente.telefone || 'Sem telefone'}</p>
      </div>
    </div>
  );
}
