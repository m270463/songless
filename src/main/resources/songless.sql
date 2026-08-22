CREATE TABLE IF NOT EXISTS musicas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    artista VARCHAR(255),
    album VARCHAR(255),
    anoLancamento INTEGER,
    linkAudio VARCHAR(1000),
    deezerId BIGINT UNIQUE,
    genero VARCHAR(255),
    ultimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

