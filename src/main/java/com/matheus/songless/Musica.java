package com.matheus.songless;

public class Musica {
    private String nome;
    private String artista;
    private String album;
    private int anoLancamento;
    private String linkAudio;

    public Musica(String nome, String artista, String album, int anoLancamento, String linkAudio){
        this.nome = nome;
        this.artista = artista;
        this.album = album;
        this.anoLancamento = anoLancamento;
        this.linkAudio = linkAudio;
    }

    public String getNome(){
        return nome;
    }

    public String getArtista(){
        return artista;
    }

    public String getAlbum(){
        return album;
    }

    public int getAnoLancamento(){
        return anoLancamento;
    }

    public String getLinkAudio(){
        return linkAudio;
    }
}
