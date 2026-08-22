package com.matheus.songless;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Musica {
    private String nome;
    private String artista;
    private String album;
    private int anoLancamento;
    private String linkAudio;
    private int id;
    private long deezerId;
    private String genero;
    private LocalDateTime ultimaAtualizacao;
    
    public Musica() {
    }

    public Musica(String nome, String artista, String album, int anoLancamento, String linkAudio) {
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.anoLancamento = anoLancamento;
        this.linkAudio = linkAudio;
    }


    public Musica(String nome, String artista, String album, int anoLancamento, String linkAudio, int id, long deezerId, String genero, LocalDateTime ultimaAtualizacao) {
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.anoLancamento = anoLancamento;
        this.linkAudio = linkAudio;
        this.id = id;
        this.deezerId = deezerId;
        this.genero = genero;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getLinkAudio() {
        return linkAudio;
    }

    public void setLinkAudio(String linkAudio) {
        this.linkAudio = linkAudio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDeezerId() {
        return deezerId;
    }

    public void setDeezerId(long deezerId) {
        this.deezerId = deezerId;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }
}