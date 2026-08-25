// controles.js
// Listeners de clique/teclado dos controles do player e dos botões do jogo.

botaoChute.addEventListener('click', function() {
    gerenciarChute();
});

campoResposta.addEventListener('keypress', function(e) {
    if (e.key == 'Enter') {
        gerenciarChute();
    }
});

    botaoTocar.addEventListener('click', function() {
    if (!jogoFinalizado) {
        if (!player.paused) {
            player.pause();
            atualizaSvg();
            return;
        }

        if (player.currentTime >= tempos[indiceAtual]) {
            player.currentTime = 0;
        }

        player.play();
        atualizaSvg();
        checarLimiteAudio(); 
    } else {
        if (!player.paused) {
            player.pause();
            atualizaSvg();
            return;
        }
        player.play();
        atualizaSvg();
    }
});

function checarLimiteAudio() {
    const limite = tempos[indiceAtual];

    if (player.currentTime >= limite) {
        player.pause();
        atualizaSvg();
        return;
    }

    if (!player.paused) {
        requestAnimationFrame(checarLimiteAudio);
    }
}

botaoRestart.addEventListener('click', function() {
    clearTimeout(temporizador)
    player.currentTime = 0;
    atualizarProgressoBarra();

    if (!player.paused && !jogoFinalizado) {
checarLimiteAudio();
    }
});

botaoSkip.addEventListener('click', function() {
    adicionarHistorico('Skipped', false);
    avancarNivel();
});

botaoProximMusica.addEventListener('click', function() {
    containerFim.style.display = 'none';
    carregarMusica(true);
});

player.addEventListener('ended', function() {
    atualizaSvg();
});

player.addEventListener('play', function(){
    atualizaSvg()
});

player.addEventListener('pause', function(){
    atualizaSvg()
});

function defineAcao() {
    const botoes = opcoesJogo.querySelectorAll('.botaoOpcao');
    const momentosJogo = historico.querySelectorAll('.linhaHistorico');
    botoes.forEach((opcao, i) => {
        opcao.addEventListener('click', function() {
            opcao.innerText;
            indiceMusicaAtual = i;
            ajustaResto();

            carregarMusica(false);
            resetar();
            opcao.disabled = true;
            opcao.classList.add('ativo');

        });
    });
    botoes[0].classList.add('ativo');
}

function ajustaResto(){
    const botoes = opcoesJogo.querySelectorAll('.botaoOpcao');
    botoes.forEach(opcao => {
        opcao.classList.remove('ativo');
        opcao.disabled = false;

        
    });
}