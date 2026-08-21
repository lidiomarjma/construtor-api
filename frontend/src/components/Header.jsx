export default function Header({ onAtualizar, carregando }) {
  return (
    <header className="flex justify-between items-center mb-8 bg-slate-800 p-6 rounded-2xl border border-slate-700 shadow-lg">
      <div>
        <h1 className="text-3xl font-bold text-white flex items-center gap-2">
          🏗️ Construtor Web
        </h1>
        <p className="text-slate-400 text-sm mt-1">
          Painel de Gestão Integrado ao Spring Boot
        </p>
      </div>
      <button
        onClick={onAtualizar}
        disabled={carregando}
        className="bg-blue-600 hover:bg-blue-500 disabled:bg-slate-700 text-white font-semibold px-4 py-2 rounded-xl transition-all shadow-md active:scale-95 cursor-pointer"
      >
        {carregando ? 'Carregando...' : '🔄 Atualizar'}
      </button>
    </header>
  );
}
