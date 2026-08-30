# Songless Unlimited

Jogo de adivinhar músicas (estilo Heardle)  (adivinhação musical por trecho de áudio): você ouve pedaços cada vez maiores de uma música — 0.5s, 1s, 2s, 4s, 8s e 15s — e tenta adivinhar o nome antes que os trechos acabem.

## Funcionalidades

- **Modo Normal**: sorteio de músicas por gênero (Todos, MPB, Rock, Pop)
- **Modo Artista**: o jogador escolhe um artista específico (validado contra o banco) e joga apenas com músicas dele
- **Player progressivo**: cada erro libera um trecho de áudio maior, com barra de progresso segmentada e marcador de tempo
- **Autocomplete**: sugestões em tempo real tanto para o nome da música quanto para o nome do artista, com busca tolerante a acentos e caracteres especiais
- **Histórico de tentativas**: mostra os palpites anteriores e se foram certos ou errados
- **Tela de resultado**: exibe nome, artista, capa do álbum e link para a faixa ao final da partida

## Tecnologias

**Backend**
- Java + Spring Boot
- PostgreSQL (com `unaccent` e `regexp_replace` para busca fuzzy)
- iTunes Search API (dados e preview de áudio das músicas)
- Last.fm e Deezer API (top tracks por artista, usadas na curadoria do catálogo)
- Gson (parsing de JSON das APIs externas)

**Frontend**
- HTML, CSS e JavaScript puro (sem framework)

## Estrutura do projeto

```
├── ArtistaController.java       # Endpoint de validação de artista
├── AutoCompleteController.java  # Endpoint de autocomplete (música/artista)
├── MusicController.java         # Endpoint de sorteio de música
├── BuscaArtista.java            # Repositório: validação de artista
├── BuscaAutoComplete.java       # Repositório: sugestões de autocomplete
├── MusicaRepositorio.java       # Repositório: CRUD e sorteio de músicas
├── ServicoBuscaMusicas.java     # Integração com iTunes e Last.fm
├── ImportaMusicas.java          # Script de importação/curadoria do catálogo por gênero
├── Musica.java                  # Modelo de domínio
├── Config.java                  # Leitura de variáveis de ambiente (.env)
├── index.html
├── style.css
└── js/
    ├── estado.js                # Referências DOM e estado global
    ├── autocomplete.js          # Busca e exibição de sugestões
    ├── controles.js             # Listeners de botões/teclado
    ├── jogo.js                  # Regras do jogo (chute, avanço de nível, vitória/derrota)
    ├── marcador.js               # Ícone play/pause e marcador de tempo
    ├── musica.js                 # Busca de música na API e setup do player
    ├── progresso.js              # Animação da barra de progresso
    └── main.js                   # Ponto de entrada
```

## Populando o catálogo

O catálogo de músicas é montado com `ImportaMusicas.java`, que busca as faixas mais populares de uma lista de artistas (separados por gênero) via iTunes, Last.fm e Deezer, e salva no banco, filtrando versões ao vivo, remixes, remasters e duplicatas.

## Como jogar

1. Escolha um gênero (ou entre no Modo Artista e digite um artista válido)
2. Clique em play e ouça o trecho liberado
3. Digite seu palpite no campo de resposta (com autocomplete)
4. Errou? O próximo trecho, um pouco maior, é liberado
5. Acertou ou esgotou as 6 tentativas? A música é revelada com capa e link para ouvir completa
