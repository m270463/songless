package com.matheus.songless;

import java.time.LocalDateTime;

public class Musica {
    private String nome;
    private String artista;
    private String album;
    private int anoLancamento;
    private String linkAudio;
    private int id;
    private long appId;
    private String genero;
    private LocalDateTime ultimaAtualizacao;
    private String linkImagem;
    private String linkRedirecionamento;
    
    public Musica() {
    }

    public Musica(String nome, String artista, String album, int anoLancamento, String linkAudio,  long appId, String genero, String linkImagem, String linkRedirecionamento) {
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.anoLancamento = anoLancamento;
        this.linkAudio = linkAudio;
        this.appId = appId;
        this.genero = genero;
        this.linkImagem = linkImagem;
        this.linkRedirecionamento = linkRedirecionamento;
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

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
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

    public String getLinkImagem(){
        return this.linkImagem;
    }

    public String getLinkRedirecionamento(){
        return this.linkRedirecionamento;
    }

    
}
