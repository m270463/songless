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
    carregarMusica(true);
    botaoSkip.innerHTML = svgPular;
    containerFim.style.display = 'none';
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
            opcaoAtual= listaOpcoes[i];
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

function corrigeOpcoes(){
    const botoes = opcoesJogo.querySelectorAll('.botaoOpcao');
    botoes.forEach(opcao => {
        if (indiceAtual > 0){
            opcao.disabled = true;
        }
        else{
            opcao.disabled = false;
        }
    });

}



botaoModo.addEventListener('click',function(){
    if (modoAtual == 'Normal'){
        modoAtual = 'Artista';
        botaoModo.innerText = 'Modo Normal';
        botaoConfirmar.classList.add('active');
        campoArtista.classList.add('active');
    }
    else if (modoAtual == 'Artista'){
        modoAtual = 'Normal';
        botaoModo.innerText = 'Modo Artista';
        botaoConfirmar.classList.remove('active');
        campoArtista.classList.remove('active');
    }
    trocaModo()
})



function trocaModo(){
    atualizarProgressoBarra(0);
    const botoes = opcoesJogo.querySelectorAll('.botaoOpcao');
    botoes.forEach(opcao => {
        if (modoAtual == 'Artista'){
            opcao.style.display = 'none';
        }
        else{
            opcao.style.display = 'inline-block';
        }
    });
    if (modoAtual == 'Normal'){
        artistaAtual = null;
        botaoChute.disabled = false;
        botaoRestart.disabled = false;
        botaoTocar.disabled = false;
        botaoSkip.disabled = false;
        campoResposta.disabled = false;
        carregarMusica(false);
    }

    else{
        botaoChute.disabled = true;
        botaoRestart.disabled = true;
        botaoTocar.disabled = true;
        botaoSkip.disabled = true;
        campoResposta.disabled = true;
        campoArtista.value = '';
    }
}

botaoConfirmar.addEventListener('click', async function(){
    const artistaValido = await validaArtista(campoArtista.value);

    if (artistaValido){
        textoConfirmar.innerText = 'Artista encontrado';
        textoConfirmar.classList.add('correto');
        artistaAtual = campoArtista.value;
        carregarMusica(true);
        botaoChute.disabled = false;
        botaoRestart.disabled = false;
        botaoTocar.disabled = false;
        botaoSkip.disabled = false;
        campoResposta.disabled = false;
    }
    else{
        textoConfirmar.innerText = 'Artista não encontrado';
        textoConfirmar.classList.add('errado');
    }

    temporizador = setTimeout(function() {
        clearTimeout(temporizador);
        textoConfirmar.classList.remove('correto','errado');
    },1000);

    listaSugestoesArtistas.innerHTML = '';
    atualizarProgressoBarra(0);
});

async function validaArtista(termo){
    const resp = await fetch(`/api/artista?artista=${encodeURIComponent(termo)}`);
    const resultado = await resp.json();
    return resultado ===  true;
}