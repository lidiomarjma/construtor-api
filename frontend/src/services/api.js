const API_URL = 'http://localhost:8080/obras';

export async function buscarObrasAPI() {
  const resposta = await fetch(API_URL);
  if (!resposta.ok) {
    throw new Error('Erro ao buscar a lista de obras na API.');
  }
  return await resposta.json();
}

export async function criarObraAPI(dadosObra) {
  const resposta = await fetch(API_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(dadosObra),
  });

  if (!resposta.ok) {
    throw new Error('Erro ao cadastrar a nova obra na API.');
  }

  return await resposta.json();
}
