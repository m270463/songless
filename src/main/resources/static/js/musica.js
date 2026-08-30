// musica.js
// Busca uma nova música na API e prepara o player.

function carregarMusica(fim) {
    // containerJogo.disabled = true;
    botaoTocar.disabled = true;
    botaoRestart.disabled = true;
    botaoSkip.disabled = true;
    if (musicas[indiceMusicaAtual] == null || fim){
        const chave = chaveModoAtual();
        const jogadas = musicasJogadas[chave] ? Array.from(musicasJogadas[chave]) :[];



        fetch(`/api/musica?opcao=${opcaoAtual}&artista=${artistaAtual}&excluir=${jogadas.join(',')}`)
            .then(resposta => resposta.json())
            .then(musica => {
            if (!musica) {
                if (jogadas.length > 0){
                    musicasJogadas[chave] = new Set();
                    carregarMusica(fim);
                    return;
                }

                alert('Nenhuma música encontrada para essa opção.');
                botaoTocar.disabled = false;
                botaoRestart.disabled = false;
                botaoSkip.disabled = false;
                return;
            }
            if (!musicasJogadas[chave])
                musicasJogadas[chave] = new Set();  


            musicasJogadas[chave].add(musica.id);

            if (modoAtual == 'Normal'){
                musicas[indiceMusicaAtual] = musica;
            }
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
            });
    }
    else{
        const musica = musicas[indiceMusicaAtual];
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
    }
};



function chaveModoAtual() {
    return modoAtual === 'Artista' ? `artista:${artistaAtual}` : `opcao:${opcaoAtual}`;
}