package com.matheus.songless;

import java.util.ArrayList;

import io.github.cdimascio.dotenv.Dotenv;

public class ImportaMusicas {
    	public static Dotenv dotenv = Dotenv.configure().systemProperties().load();

    public static void main(String[] args) {
        ServicoBuscaMusicas servicoBusca = new ServicoBuscaMusicas();
        MusicaRepositorio repositorio = new MusicaRepositorio();

        ArrayList<Musica> musicas = servicoBusca.buscaMusicasPorArtista("Djavan", "MPB", 20); 

        for (Musica m : musicas) {
            System.out.println("Salvando: " + m.getNome() + " - " + m.getArtista());
            repositorio.salvarMusica(m);
        }

        System.out.println("Total salvo: " + musicas.size());
    }
}
