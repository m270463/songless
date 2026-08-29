CREATE TABLE IF NOT EXISTS musicasApple (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    artista VARCHAR(255),
    album VARCHAR(255),
    anoLancamento INTEGER,
    linkAudio VARCHAR(1000),
    linkImagem VARCHAR(1000),
    linkRedirecionamento VARCHAR(1000),
    appleId BIGINT UNIQUE,
    genero VARCHAR(255),
    ultimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS musicasArtista(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    artista VARCHAR(255),
    album VARCHAR(255),
    anoLancamento INTEGER,
    linkAudio VARCHAR(1000),
    linkImagem VARCHAR(1000),
    linkRedirecionamento VARCHAR(1000),
    appleId BIGINT UNIQUE,
    genero VARCHAR(255),
    ultimaAtualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

