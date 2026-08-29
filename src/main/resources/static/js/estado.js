// estado.js
// Referências a elementos do DOM, constantes e variáveis de estado
// compartilhadas entre os demais arquivos.

const botaoTocar = document.getElementById('botaoTocar');
const botaoSkip = document.getElementById('botaoSkip');
const player = document.getElementById('musicPlayer');
const botaoRestart = document.getElementById('botaoRestart');
const campoResposta = document.getElementById('campoResposta');
const listaSugestoes = document.getElementById('sugestoes');
const listaSugestoesArtistas = document.getElementById('sugestoesArtistas');
listaSugestoesArtistas.classList.add('artista');
const botaoChute = document.getElementById('botaoChute');
const historico = document.getElementById('historico');
const imagem = document.getElementById('modalCapaAlbum');
const resultado = document.getElementById('modalNomeMusica');
const botaoProximMusica = document.getElementById('botaoProximo');
const containerFim = document.getElementById('fimJogo');
const linkNome = document.getElementById('linkNome');
const linkCapa = document.getElementById('linkCapa');
const tempos = [0.5, 1.0, 2.0, 4.0, 8.0, 15.0];
const opcoesJogo = document.getElementById('opcoes');
const musicas = [null,null,null,null];
const listaOpcoes = ['Todos', 'MPB', 'Rock','Pop'];
const botaoModo = document.getElementById('botaoModo');
const campoArtista = document.getElementById('campoArtista');
const botaoConfirmar = document.getElementById('botaoConfirmar');
const textoConfirmar = document.getElementById('textoConfirmação');
let modoAtual = 'Normal';
let artistaAtual = null;
let opcaoAtual = 'Todos';
let indiceAtual = 0;
let indiceMusicaAtual = 0;
let temporizador;
let jogoFinalizado = false;
let musicaAtual = null;
let timeoutId = null;
let indiceAtivo = 0;

campoResposta.value = '';

const svgPausa = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-pause-icon lucide-pause"><rect x="14" y="3" width="5" height="18" rx="1"/><rect x="5" y="3" width="5" height="18" rx="1"/></svg>';
const svgPlay = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-play-icon lucide-play"><path d="M5 5a2 2 0 0 1 3.008-1.728l11.997 6.998a2 2 0 0 1 .003 3.458l-12 7A2 2 0 0 1 5 19z"/></svg>';
const svgDesistir = '<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-flag-icon lucide-flag"><path d="M4 22V4a1 1 0 0 1 .4-.8A6 6 0 0 1 8 2c3 0 5 2 7.333 2q2 0 3.067-.8A1 1 0 0 1 20 4v10a1 1 0 0 1-.4.8A6 6 0 0 1 16 16c-3 0-5-2-8-2a6 6 0 0 0-4 1.528"/></svg>';
const svgPular = ' <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-skip-forward-icon lucide-skip-forward"><path d="M21 4v16"/><path d="M6.029 4.285A2 2 0 0 0 3 6v12a2 2 0 0 0 3.029 1.715l9.997-5.998a2 2 0 0 0 .003-3.432z"/></svg>';