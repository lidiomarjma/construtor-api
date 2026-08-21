import { useEffect, useState } from 'react';
import Header from './components/Header';
import ObraCard from './components/ObraCard';
import ObraForm from './components/ObraForm';
import { buscarObrasAPI, criarObraAPI } from './services/api';

export default function App() {
  const [obras, setObras] = useState([]);
  const [carregando, setCarregando] = useState(false);
  const [erro, setErro] = useState(null);

  async function carregarObras() {
    setCarregando(true);
    setErro(null);
    try {
      const dados = await buscarObrasAPI();
      setObras(dados);
    } catch (err) {
      console.error(err);
      setErro('Não foi possível carregar as obras. Verifique se o Spring Boot está rodando.');
    } finally {
      setCarregando(false);
    }
  }

  async function handleCriarObra(novaObra) {
    setCarregando(true);
    setErro(null);
    try {
      await criarObraAPI(novaObra);
      await carregarObras();
      return true;
    } catch (err) {
      console.error(err);
      setErro('Erro ao salvar a nova obra.');
      return false;
    } finally {
      setCarregando(false);
    }
  }

  useEffect(() => {
    carregarObras();
  }, []);

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 p-8">
      <div className="max-w-4xl mx-auto">
        <Header onAtualizar={carregarObras} carregando={carregando} />

        <ObraForm onCriarObra={handleCriarObra} carregando={carregando} />

        {erro && (
          <div className="bg-red-950/80 border border-red-500 text-red-200 p-4 rounded-xl mb-6">
            ⚠️ {erro}
          </div>
        )}

        <main className="grid gap-4">
          {obras.length === 0 && !carregando && !erro && (
            <p className="text-slate-400 italic">Nenhuma obra cadastrada.</p>
          )}

          {obras.map((obra) => (
            <ObraCard key={obra.id} obra={obra} />
          ))}
        </main>
      </div>
    </div>
  );
}
