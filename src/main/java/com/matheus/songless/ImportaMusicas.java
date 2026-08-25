package com.matheus.songless;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImportaMusicas {
    

    public static void main(String[] args) {
        ServicoBuscaMusicas servicoBusca = new ServicoBuscaMusicas();
        MusicaRepositorio repositorio = new MusicaRepositorio();

        // 1. Lista com 10 das maiores bandas/artistas do Rock
        List<String> artistasRock = List.of(
            "Queen",
            "Guns N' Roses",
            "Scorpions",
            "Nirvana",
            "Metallica",
            "Pink Floyd",
            "Red Hot Chili Peppers",
            "Foo Fighters",
            "Iron Maiden",
            "Creed"
        );

        String genero = "Rock";
        int totalGeralSalvo = 0;

        // 2. Iteração para rodar o processo para cada membro da lista
        for (String artista : artistasRock) {
            System.out.println("\n=== Buscando Top Hits: " + artista + " ===");

            ArrayList<Musica> musicas = servicoBusca.buscaTopMusicasDoArtista(artista, genero, 10); 
            Set<String> nomesSalvos = new HashSet<>();
            int totalSalvosArtista = 0;

            for (Musica m : musicas) {
                if (!m.getArtista().equalsIgnoreCase(artista)) {
                    continue;
                }

                String nomeOriginal = m.getNome(); 

                // // Ignora versões ao vivo, remasters e remixes
                // if (ehVersaoIndesejada(nomeOriginal)) {
                //     System.out.println("Ignorado (versão alternativa): " + nomeOriginal);
                //     continue;
                // }

                // Normaliza o nome (remove sufixos e parênteses) para comparação
                String nomeBase = normalizarNome(nomeOriginal);

                // Evita salvar faixas repetidas vindas de álbuns diferentes
                if (nomesSalvos.contains(nomeBase)) {
                    System.out.println("Ignorado (duplicado na lista): " + nomeOriginal);
                    continue;
                }

                nomesSalvos.add(nomeBase);
                System.out.println("Salvando: " + m.getNome() + " - " + m.getArtista());
                repositorio.salvarMusica(m); //[cite: 13, 15]
                totalSalvosArtista++;
            }

            System.out.println("Total salvos para " + artista + ": " + totalSalvosArtista);
            totalGeralSalvo += totalSalvosArtista;

            // Pausa de 300ms para evitar estouro de requisições na API da Apple
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n==========================================");
        System.out.println("TOTAL GERAL DE MÚSICAS SALVAS: " + totalGeralSalvo);
        System.out.println("==========================================");
    }

    private static boolean ehVersaoIndesejada(String nome) {
        String nomeLower = nome.toLowerCase();
        String[] termosBloqueados = {
            "live", "ao vivo", "remaster", "deluxe", "version", 
            "edit", "mix", "acoustic", "acústico", "demo"
        };
        for (String termo : termosBloqueados) {
            if (nomeLower.contains(termo)) {
                return true;
            }
        }
        return false;
    }

    private static String normalizarNome(String nome) {
        return nome.replaceAll("(?i)\\s*[\\[\\(].*?[\\]\\)]", "")
                   .replaceAll("(?i)\\s*-.*$", "")
                   .trim()
                   .toLowerCase();
    }
}