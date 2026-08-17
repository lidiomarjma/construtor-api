import { useEffect, useState } from 'react';

export default function App() {
  const [obras, setObras] = useState([]);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState(null);

  async function buscarObras() {
    setLoading(true);
    setErro(null);
    try {
      const resposta = await fetch('http://localhost:8080/obras');
      if (!resposta.ok) throw new Error('Erro ao buscar dados da API');
      const dados = await resposta.json();
      setObras(dados);
    } catch (err) {
      console.error(err);
      setErro('Não foi possível conectar ao servidor Spring Boot.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    buscarObras();
  }, []);

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 p-8">
      <div className="max-w-4xl mx-auto">
        <header className="flex justify-between items-center mb-8 bg-slate-800 p-6 rounded-2xl border border-slate-700 shadow-lg">
          <div>
            <h1 className="text-3xl font-bold text-white flex items-center gap-2">
              🏗️ Construtor Web
            </h1>
            <p className="text-slate-400 text-sm mt-1">
              Frontend em React + Vite integrado ao Spring Boot
            </p>
          </div>
          <button
            onClick={buscarObras}
            disabled={loading}
            className="bg-blue-600 hover:bg-blue-500 disabled:bg-slate-700 text-white font-semibold px-4 py-2 rounded-xl transition-all shadow-md active:scale-95"
          >
            {loading ? 'Carregando...' : '🔄 Atualizar'}
          </button>
        </header>

        {erro && (
          <div className="bg-red-950/80 border border-red-500 text-red-200 p-4 rounded-xl mb-6">
            ⚠️ {erro}
          </div>
        )}

        <main className="grid gap-4">
          {obras.length === 0 && !loading && !erro && (
            <p className="text-slate-400 italic">Nenhuma obra cadastrada.</p>
          )}

          {obras.map((obra) => (
            <div
              key={obra.id}
              className="bg-slate-800 p-6 rounded-2xl border border-slate-700 hover:border-blue-500 transition-all shadow-md flex justify-between items-center"
            >
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
                  className={`px-3 py-1 rounded-full text-xs font-bold ${obra.status === 'EM_ANDAMENTO'
                      ? 'bg-amber-500/20 text-amber-300 border border-amber-500/30'
                      : 'bg-blue-500/20 text-blue-300 border border-blue-500/30'
                    }`}
                >
                  {obra.status}
                </span>
              </div>
            </div>
          ))}
        </main>
      </div>
    </div>
  );
}
