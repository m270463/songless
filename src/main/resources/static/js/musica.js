// musica.js
// Busca uma nova música na API e prepara o player.

function carregarMusica() {
    const containerJogo = document.getElementsByClassName('CardPrincipal');
    // containerJogo.disabled = true;
    botaoTocar.disabled = true;
    botaoRestart.disabled = true;
    botaoSkip.disabled = true;
    fetch('/api/musica')
        .then(resposta => resposta.json())
        .then(musica => {
            musicaAtual = musica;
            botaoTocar.disabled = false;
            botaoRestart.disabled = false;
            botaoSkip.disabled = false;
            player.src = musica.linkAudio;
            imagem.src = musica.linkImagem.replace("170x170", "600x600");
            resultado.innerText = musica.nome + ' - ' + musica.artista;
            linkCapa.href = musica.linkRedirecionamento;
            linkNome.href = musica.linkRedirecionamento;
            botaoSkip.innerHTML = svgPular;
            player.load();
            atualizaSvg();
            // containerJogo.disabled = false;
        });
};
