// jogo.js
// Regras do jogo: avaliar chutes, avançar de nível, vencer/perder,
// histórico de tentativas e reset para a próxima rodada.

function adicionarHistorico(texto, acertou) {
    
    const linha = historico.children[indiceAtual];
    linha.innerText = texto;
    if (texto == 'Skipped') {
        return;
    }
    linha.classList.add(acertou ? 'acerto' : 'erro');
}

function gerenciarChute() {
    const chute = campoResposta.value.trim();
    if (!chute || !musicaAtual) {
        return;
    }

    if (normalizarTexto(chute.split(' - ')[0].toLowerCase()) == normalizarTexto(musicaAtual.nome.toLowerCase())) {
        adicionarHistorico(campoResposta.value, true);
        vencerJogo();
        return;
    }
    adicionarHistorico(campoResposta.value, false);
    campoResposta.value = '';
    avancarNivel();
}
function normalizarTexto(texto) {
    return texto
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .replace(/[,.!?;:'"()]/g, '')
        .replace(/\s+/g, '')         
        .toLowerCase();
}

function avancarNivel() {
    if (modoAtual == 'Artista'){
        campoArtista.disabled = true;
        botaoConfirmar.disabled = true;
    }
    botaoModo.disabled = true;
    if (indiceAtual < tempos.length - 1) {
        indiceAtual++;
        atualizarMarcador();
        corrigeOpcoes();
        
        if (!player.paused) {
            checarLimiteAudio();
            
        }
        if (indiceAtual == tempos.length - 1){
            botaoSkip.innerHTML = svgDesistir;
        }
        return;
    }
    perderJogo();
}

function vencerJogo() {
    clearTimeout(temporizador);
    player.currentTime = 0;
    player.play();
    atualizaSvg();
    desativar();
    finalizarJogo(true)
}

function perderJogo() {
    clearTimeout(temporizador);
    player.pause();
    player.currentTime = 0;
    atualizaSvg();
    desativar();
    finalizarJogo(false);
}

function desativar() {
    jogoFinalizado = true;
    botaoChute.disabled = true;
    botaoSkip.disabled = true;
    campoResposta.disabled = true;
}

function finalizarJogo(vitoria) {

    containerFim.style.display = 'flex';
    const titulo = document.getElementById('result');
    if (vitoria) {
        titulo.innerText = 'You Won!';
        titulo.style.backgroundColor = '#1db954';
    }
    else {
        titulo.innerText = 'You Lost!'
        titulo.style.backgroundColor = 'red';
    }
        
    resetar();
}

function resetar() {
    indiceAtual = 0;
    jogoFinalizado = false;
    campoResposta.value = '';

    // Reativa os botões e o campo de texto
    botaoChute.disabled = false;
    botaoSkip.disabled = false;
    campoResposta.disabled = false;
    botaoConfirmar.disabled = false;
    campoArtista.disabled = false;
    botaoModo.disabled = false;
    // Reseta as linhas do histórico
    for (let i = 0; i < historico.children.length; i++) {
        const linha = historico.children[i];
        linha.innerText = '';

        linha.classList.remove('acerto', 'erro');
    }
    // Reseta áudio, marcador e barra de progresso
    player.currentTime = 0;
    atualizaSvg();
    atualizarMarcador();
    atualizarProgressoBarra(0);
    corrigeOpcoes();
}
