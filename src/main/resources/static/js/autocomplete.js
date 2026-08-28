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
        buscarSugestoes(termo,'Resposta');
    }, 250);
});

campoArtista.addEventListener('input', function() {
    clearTimeout(timeoutId);
    const termo = campoArtista.value;
    if (termo.length < 2) {
        listaSugestoesArtistas.innerHTML = '';
        return;
    }

    timeoutId = setTimeout(function() {
        buscarSugestoes(termo,'Artista');
    }, 250);
});



async function buscarSugestoes(termo,campo) {
    const resp = await fetch(`/api/autocomplete?termo=${encodeURIComponent(termo)}&campo=${campo}&modo=${modoAtual}`);
    const sugestoes = await resp.json();
    if (campo == 'Resposta'){
        exibirSugestoes(sugestoes,listaSugestoes);
    }
    else{
        exibirSugestoes(sugestoes,listaSugestoesArtistas);
    }
}

function exibirSugestoes(sugestoes,listaSugestoes) {
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

function selecionar(texto,campo) {
    if (campo == 'Resposta'){
    campoResposta.value = texto;
    gerenciarChute();
    listaSugestoes.innerHTML = '';
    }
    else{
        campoArtista.value = texto;
        listaSugestoesArtistas.innerHTML = '';
    }
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
        listaSugestoesArtistas.innerHTML = '';
    }
});
