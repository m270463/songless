// progresso.js
// Preenchimento da barra de progresso e loop de animação enquanto o áudio toca.

const segmentos = document.querySelectorAll('.segmento');
let animacaoId = null;

function atualizarProgressoBarra(tempoForcado) {
    const tempoAtual = (tempoForcado !== undefined) ? tempoForcado : player.currentTime;

    segmentos.forEach((segmento, i) => {
        const inicio = (i === 0) ? 0 : tempos[i - 1];
        const fim = tempos[i];

        if (tempoAtual >= fim) {
            // Segmento completado em branco
            segmento.style.background = '#ffffff';
        } else if (tempoAtual <= inicio) {
            // Segmento futuro em cinza escuro
            segmento.style.background = '#333333';
        } else {
            // Preenchimento progressivo em branco
            const porcentagem = ((tempoAtual - inicio) / (fim - inicio)) * 100;
            segmento.style.background = `linear-gradient(90deg, #ffffff ${porcentagem}%, #333333 ${porcentagem}%)`;
        }
    });
}

// Loop de alta precisão (60 FPS) enquanto o áudio toca
function animarProgresso() {
    atualizarProgressoBarra();
    if (!player.paused) {
        animacaoId = requestAnimationFrame(animarProgresso);
    }
}

// Inicia a animação fluida no Play
player.addEventListener('play', () => {
    cancelAnimationFrame(animacaoId);
    animacaoId = requestAnimationFrame(animarProgresso);
});

// Força o alinhamento perfeito no Pause
player.addEventListener('pause', () => {
    cancelAnimationFrame(animacaoId);

    const limiteSegundos = tempos[indiceAtual];

    // Se parou perto do limite final do nível, força o preenchimento em 100% no gap exato
    if (Math.abs(player.currentTime - limiteSegundos) < 0.2 || player.currentTime >= limiteSegundos) {
        atualizarProgressoBarra(limiteSegundos);
    } else {
        atualizarProgressoBarra();
    }
});

player.addEventListener('canplaythrough', () => {
    botaoTocar.disabled = false;
    botaoRestart.disabled = false;
    botaoSkip.disabled = false;
});
