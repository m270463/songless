package com.matheus.songless;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImportaMusicas {
    
    public static void main(String[] args) {
        ServicoBuscaMusicas servicoBusca = new ServicoBuscaMusicas();
        MusicaRepositorio repositorio = new MusicaRepositorio();
        


        Map<String, Integer> hashRock = new HashMap<>(Map.ofEntries(
            Map.entry("Queen", 20),
            Map.entry("Guns N' Roses", 20),
            Map.entry("Scorpions", 15),
            Map.entry("Nirvana", 10),
            Map.entry("Metallica", 20),
            Map.entry("Pink Floyd", 15),
            Map.entry("Red Hot Chili Peppers", 15),
            Map.entry("Foo Fighters", 5),
            Map.entry("Iron Maiden", 10),
            Map.entry("Creed", 5),
            Map.entry("Aerosmith", 10),
            Map.entry("AC/DC", 20),
            Map.entry("Megadeth", 10),
            Map.entry("Led Zepellin", 10),
            Map.entry("Pearl Jam", 10),
            Map.entry("Alice in Chains", 10),
            Map.entry("Rolling Stones", 10),
            Map.entry("Dire Straits", 5),
            Map.entry("Beatles", 20),
            Map.entry("Evanescence", 5),
            Map.entry("Paramore", 5),
            Map.entry("Linkin Park", 15),
            Map.entry("Bon Jovi", 15),
            Map.entry("Ozzy Osbourne", 20),
            Map.entry("Black Sabbath", 10),
            Map.entry("Dio", 5),
            Map.entry("Europe", 3),
            Map.entry("Boston", 3),
            Map.entry("The Police", 3),
            Map.entry("The Goo Goo Dolls", 3),
            Map.entry("Oasis", 15),
            Map.entry("Skid Row", 5),
            Map.entry("Green Day", 10),
            Map.entry("Sepultura", 5),
            Map.entry("Jimi Hendrix", 5),
            Map.entry("Angra", 10),
            Map.entry("Radiohead", 10),
            Map.entry("Elvis Presley", 10),
            Map.entry("Legião Urbana", 10),
            Map.entry("Charlie Brown", 15),
            Map.entry("Engenheiros do Hawaii", 5),
            Map.entry("O Rappa", 3),
            Map.entry("Skank", 10),
            Map.entry("Capital Inicial", 10),
            Map.entry("Jota Quest", 10),
            Map.entry("Raimundos", 5),
            Map.entry("Pitty", 5),
            Map.entry("Paralamas do Sucessos", 5),
            Map.entry("Dream Theater", 10),
            Map.entry("Soundgarden", 3),
            Map.entry("Audioslave", 5),
            Map.entry("Mamonas Assassinas",3),
            Map.entry("Deep Purple", 5)
        ));


        Map<String, Integer> hashMpb = new HashMap<>(Map.ofEntries(
            Map.entry("Gal Costa",25),
            Map.entry("Chico Buarque",20),
            Map.entry("Milton Nascimento",20),
            Map.entry("Maria Bethânia",15),
            Map.entry("Roupa Nova",10),
            Map.entry("Zizi Possi", 25),
            Map.entry("Antônio Carlos Jobim",15),
            Map.entry("Ivan Lins", 7),
            Map.entry("Elis Regina", 20),
            Map.entry("Djavan", 25),
            Map.entry("Tim Maia", 15),
            Map.entry("Gonzaguinha", 10),
            Map.entry("Gilberto Gil", 20),
            Map.entry("Rita Lee", 20),
            Map.entry("Marina Lima", 10),
            Map.entry("Lulu Santos", 20),
            Map.entry("Guilherme Arantes", 15),
            Map.entry("Secos e Molhados", 5),
            Map.entry("Cartola", 10),
            Map.entry("Marisa Monte", 15),
            Map.entry("Nando Reis", 10),
            Map.entry("Jorge Vercillo", 15),
            Map.entry("Emilio Santiago", 5),
            Map.entry("Novos Baianos", 5),
            Map.entry("Titas",5),
            Map.entry("Lo Borges", 5),
            Map.entry("Raul Seixas", 5),
            Map.entry("Ana Carolina", 15),
            Map.entry("Adriana Calcanhoto", 10)


        ));

        Map<String, Integer> hashPop = new HashMap<>(Map.ofEntries(
        Map.entry("Whitney Houston", 15),
        Map.entry("Ed Sheeran", 15),
        Map.entry("Bruno Mars", 25),
        Map.entry("Michael Jackson", 35),
        Map.entry("Rihanna" , 15),
        Map.entry("Coldplay", 15),
        Map.entry("Taylor Swift", 10),
        Map.entry("Beyonce", 15),
        Map.entry("Adele", 15),
        Map.entry("Lady Gaga", 15),
        Map.entry("Harry Styles",10),
        Map.entry("Sabrina Carpenter",5),
        Map.entry("Billie Eilish", 10),
        Map.entry("Katy Perry", 20),
        Map.entry("Mariah Carey", 10),
        Map.entry("Black Eyed Peas", 7),
        Map.entry("Justin Bieber", 15),
        Map.entry("Sia", 5),
        Map.entry("Britney Spears", 10),
        Map.entry("Céline Dion", 10),
        Map.entry("Demi Lovato", 5),
        Map.entry("Backstreet Boys", 5),
        Map.entry("Pharrell Williams" ,3),
        Map.entry("Miley Cyrus", 10),
        Map.entry("The Weeknd", 15),
        Map.entry("Stevie Wonder", 15),
        Map.entry("Bee Gees", 5),
        Map.entry("Prince", 5),
        Map.entry("Dua Lipa", 5)
        ));
// Chave: Nome do Gênero (String - Imutável)
// Valor: Mapa de Artistas (Map<String, Integer>)
Map<String, Map<String, Integer>> hashGeral = Map.of(
    "Rock", hashRock,
    "MPB", hashMpb,
    "Pop", hashPop
);


        String genero = "";
        int totalGeralSalvo = 0;
        int limite = 0;
        ArrayList<Musica>musicas = new ArrayList<>();
        String artista = "";
    for(Map.Entry<String,Map<String,Integer>> geral: hashGeral.entrySet()){
        Map<String,Integer> map = geral.getValue();
        genero = geral.getKey();
        for (Map.Entry<String,Integer> entrada: map.entrySet()) {
            artista = entrada.getKey();
            limite = entrada.getValue();
            System.out.println("\n=== Buscando Top Hits: " + artista + " ===");
            try{
                musicas = servicoBusca.buscaMusicasPopularesLastFm(artista, genero, limite);
                musicas.addAll(servicoBusca.buscaMusicasPopularesDeezer(artista, genero, limite));
            } catch(InterruptedException e){
                e.printStackTrace();
            }
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