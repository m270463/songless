// marcador.js
// Atualização do ícone play/pause e do marcador de tempo acima da barra.

function atualizaSvg() {
    player.paused ? botaoTocar.innerHTML = svgPlay : botaoTocar.innerHTML = svgPausa;
}

function atualizarMarcador() {
    const segmentoAtivo = document.querySelectorAll('.segmento')[indiceAtual];
    const wrapper = document.querySelector('.wrapper-barra');
    const marcador = document.querySelector('.marcador');
    const textoMarcador = document.querySelector('.marcador-texto');

    if (!segmentoAtivo || !wrapper || !marcador) return;

    const rectSegmento = segmentoAtivo.getBoundingClientRect();
    const rectWrapper = wrapper.getBoundingClientRect();

    const posicaoFinal = rectSegmento.right - rectWrapper.left;

    marcador.style.left = posicaoFinal + 'px';
    marcador.style.transform = 'translateX(-50%)';

    textoMarcador.textContent = tempos[indiceAtual] + 's';
}

window.addEventListener('resize', atualizarMarcador);
