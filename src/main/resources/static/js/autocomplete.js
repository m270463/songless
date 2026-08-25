// autocomplete.js
// Busca e exibição de sugestões enquanto o usuário digita, e navegação
// pelas sugestões com o teclado.

campoResposta.addEventListener('input', function() {
    clearTimeout(timeoutId);
    const termo = campoResposta.value;
    if (termo.length < 2) {
        listaSugestoes.innerHTML = '';
        return;
    }

    timeoutId = setTimeout(function() {
        buscarSugestoes(termo);
    }, 250);
});

async function buscarSugestoes(termo) {
    const resp = await fetch(`/api/autocomplete?termo=${encodeURIComponent(termo)}`);
    const sugestoes = await resp.json();
    exibirSugestoes(sugestoes);
}

function exibirSugestoes(sugestoes) {
    listaSugestoes.innerHTML = '';
    sugestoes.forEach(texto => {
        const elemento = document.createElement('li');
        elemento.textContent = texto;
        elemento.addEventListener('click', function() {
            selecionar(texto);
        });
        elemento.classList.add('elementoSugestao');
        listaSugestoes.appendChild(elemento);
    })
}

function selecionar(texto) {
    campoResposta.value = texto;
    gerenciarChute();
    listaSugestoes.innerHTML = '';
}

campoResposta.addEventListener('keydown', (e) => {
    const itens = listaSugestoes.querySelectorAll('li');
    if (!itens.length) return;

    if (e.key === 'Enter' && indiceAtivo >= 0) {
        e.preventDefault();
        selecionar(itens[indiceAtivo].textContent);
    }
});

document.addEventListener('click', (e) => {
    if (!e.target.closest('.autocomplete-wrapper')) {
        listaSugestoes.innerHTML = '';
    }
});
