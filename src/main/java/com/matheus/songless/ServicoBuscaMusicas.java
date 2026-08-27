package com.matheus.songless;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ServicoBuscaMusicas {

    private JsonObject buscaGenero(int generoId){
        String url = "https://itunes.apple.com/search?term=Gal+Costa&entity=song&limit=10";
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonParser.parseString(response.body()).getAsJsonObject();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String buscaTermo(String termo, int limite) {
        // Adicionado &attribute=artistTerm para priorizar os hits do artista
        String url = "https://itunes.apple.com/search?term=" + URLEncoder.encode(termo, StandardCharsets.UTF_8)
                + "&entity=song&attribute=artistTerm&limit=" + limite;
        try {
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Musica> buscaMusicasGenero(int generoId){
        ArrayList<Musica> musicasApple = new ArrayList<>();
        JsonObject resposta = buscaGenero(generoId);
        if (resposta == null)
            return musicasApple;

        JsonArray musicas = resposta.getAsJsonObject("feed").getAsJsonArray("entry");

        for (JsonElement el : musicas) {
            JsonObject musica = el.getAsJsonObject();

            String nomeMusica = musica.getAsJsonObject("im:name").get("label").getAsString();
            String nomeArtista = musica.getAsJsonObject("im:artist").get("label").getAsString();
            String nomeAlbum = musica.getAsJsonObject("im:collection").getAsJsonObject("im:name").get("label").getAsString();
            String genero = musica.getAsJsonObject("category").getAsJsonObject("attributes").get("label").getAsString();
            int dataLancamento = Integer.parseInt(musica.getAsJsonObject("im:releaseDate").get("label").getAsString().substring(0, 4));
            String linkImagem = musica.getAsJsonArray("im:image").get(2).getAsJsonObject().get("label").getAsString();
            long id = musica.getAsJsonObject("id").getAsJsonObject("attributes").get("im:id").getAsLong();

            JsonArray links = musica.getAsJsonArray("link");
            String linkRedirect = null;
            String linkPreview = null;

            for (JsonElement linkEl : links) {
                JsonObject attrs = linkEl.getAsJsonObject().getAsJsonObject("attributes");
                String rel = attrs.get("rel").getAsString();

                if ("alternate".equals(rel)) {
                    linkRedirect = attrs.get("href").getAsString();
                } else if ("enclosure".equals(rel)) {
                    linkPreview = attrs.get("href").getAsString();
                }
            }

            musicasApple.add(new Musica(nomeMusica, nomeArtista, nomeAlbum, dataLancamento, linkPreview, id, genero, linkImagem, linkRedirect));
        }

        return musicasApple;
    }


    public ArrayList<Musica> buscaMusicasPorArtista(String artista, String generoJogo, int limite){
        ArrayList<Musica> musicasEncontradas = new ArrayList<>();
        String corpoResposta = buscaTermo(artista, limite);
        if (corpoResposta == null)
            return musicasEncontradas;

        JsonObject resposta = JsonParser.parseString(corpoResposta).getAsJsonObject();
        JsonArray resultados = resposta.getAsJsonArray("results");

        for (JsonElement el : resultados) {
            JsonObject musica = el.getAsJsonObject();

            if (!musica.has("previewUrl") || musica.get("previewUrl").isJsonNull())
                continue; // pula resultados sem preview disponível

            String nomeMusica = musica.get("trackName").getAsString();
            String nomeArtista = musica.get("artistName").getAsString();
            String nomeAlbum = musica.get("collectionName").getAsString();
            int dataLancamento = Integer.parseInt(musica.get("releaseDate").getAsString().substring(0, 4));
            String linkPreview = musica.get("previewUrl").getAsString();
            String linkImagem = musica.get("artworkUrl100").getAsString();
            String linkRedirect = musica.get("trackViewUrl").getAsString();
            long id = musica.get("trackId").getAsLong();

            musicasEncontradas.add(new Musica(nomeMusica, nomeArtista, nomeAlbum, dataLancamento, linkPreview, id, generoJogo, linkImagem, linkRedirect));
        }

        return musicasEncontradas;
    }



    public ArrayList<Musica> buscaTopMusicasDoArtista(String nomeArtista, String generoJogo, int limiteHits) {
    ArrayList<Musica> topHits = new ArrayList<>();
    
    try {
        HttpClient cliente = HttpClient.newHttpClient();

        // Passos 1: Descobrir o ID do Artista no iTunes
        String urlBuscaArtista = "https://itunes.apple.com/search?term=" 
                + URLEncoder.encode(nomeArtista, StandardCharsets.UTF_8) 
                + "&entity=musicArtist&limit=1";

        HttpRequest reqArtista = HttpRequest.newBuilder().uri(URI.create(urlBuscaArtista)).GET().build();
        HttpResponse<String> respArtista = cliente.send(reqArtista, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonArtista = JsonParser.parseString(respArtista.body()).getAsJsonObject();
        JsonArray resultadosArtista = jsonArtista.getAsJsonArray("results");

        if (resultadosArtista.isEmpty()) return topHits; // Artista não encontrado

        long artistId = resultadosArtista.get(0).getAsJsonObject().get("artistId").getAsLong();



        
        // Passo 2: Buscar as top músicas vinculadas a esse ID de artista
        String urlLookup = "https://itunes.apple.com/lookup?id=" + artistId + "&entity=song&limit=" + (limiteHits + 1);
        HttpRequest reqLookup = HttpRequest.newBuilder().uri(URI.create(urlLookup)).GET().build();
        HttpResponse<String> respLookup = cliente.send(reqLookup, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonLookup = JsonParser.parseString(respLookup.body()).getAsJsonObject();
        JsonArray resultadosLookup = jsonLookup.getAsJsonArray("results");

        for (JsonElement el : resultadosLookup) {
            JsonObject item = el.getAsJsonObject();
            
            // O primeiro resultado do lookup de artista é o próprio artista, ignoramos ele verificando wrapperType
            if (item.has("wrapperType") && "artist".equals(item.get("wrapperType").getAsString())) {
                continue;
            }

            if (!item.has("previewUrl") || item.get("previewUrl").isJsonNull()) continue;

            String nomeMusica = item.get("trackName").getAsString();
            String artista = item.get("artistName").getAsString();
            String album = item.has("collectionName") ? item.get("collectionName").getAsString() : "Single";
            int dataLancamento = Integer.parseInt(item.get("releaseDate").getAsString().substring(0, 4));
            String linkPreview = item.get("previewUrl").getAsString();
            String linkImagem = item.get("artworkUrl100").getAsString();
            String linkRedirect = item.has("trackViewUrl") ? item.get("trackViewUrl").getAsString() : "";
            long trackId = item.get("trackId").getAsLong();

            topHits.add(new Musica(nomeMusica, artista, album, dataLancamento, linkPreview, trackId, generoJogo, linkImagem, linkRedirect));
        }

    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }

    return topHits;
}

// ServicoBuscaMusicas.java

private String buscaLastFmTopTracks(String artista, int limite) {
    String url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks"
            + "&artist=" + URLEncoder.encode(artista, StandardCharsets.UTF_8)
            + "&api_key=" + Config.getLASTFMAPIKEY()
            + "&format=json&limit=" + limite;
    try {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return null;
    }
}

private Musica buscaFaixaExataNoItunes(String nomeFaixa, String nomeArtista, String generoJogo) {
    String termo = nomeFaixa + " " + nomeArtista;
    String url = "https://itunes.apple.com/search?term=" + URLEncoder.encode(termo, StandardCharsets.UTF_8)
            + "&entity=song&limit=1&country=BR";
    try {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject resposta = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray resultados = resposta.getAsJsonArray("results");

        if (resultados.isEmpty()) return null;

        JsonObject musica = resultados.get(0).getAsJsonObject();
        if (!musica.has("previewUrl") || musica.get("previewUrl").isJsonNull()) return null;

        String nomeMusica = musica.get("trackName").getAsString();
        String artistaReal = musica.get("artistName").getAsString();
        String album = musica.has("collectionName") ? musica.get("collectionName").getAsString() : "Single";
        int ano = Integer.parseInt(musica.get("releaseDate").getAsString().substring(0, 4));
        String linkPreview = musica.get("previewUrl").getAsString();
        String linkImagem = musica.get("artworkUrl100").getAsString();
        String linkRedirect = musica.has("trackViewUrl") ? musica.get("trackViewUrl").getAsString() : "";
        long trackId = musica.get("trackId").getAsLong();

        return new Musica(nomeMusica, artistaReal, album, ano, linkPreview, trackId, generoJogo, linkImagem, linkRedirect);
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return null;
    }
}

public ArrayList<Musica> buscaMusicasPopularesLastFm(String artista, String generoJogo, int quantidade) throws InterruptedException {
    ArrayList<Musica> resultado = new ArrayList<>();
    String corpo = buscaLastFmTopTracks(artista, quantidade);
    if (corpo == null) 
        return resultado;

    JsonObject resposta = JsonParser.parseString(corpo).getAsJsonObject();
    if (!resposta.has("toptracks")) {
        System.out.println("Last.fm não retornou faixas para: " + artista);
        return resultado;
    }

    JsonArray faixas = resposta.getAsJsonObject("toptracks").getAsJsonArray("track");
    for (JsonElement el : faixas) {
        String nomeFaixa = el.getAsJsonObject().get("name").getAsString();
        Musica musica = buscaFaixaExataNoItunes(nomeFaixa, artista, generoJogo);
        if (musica != null) {
            resultado.add(musica);
        }
        Thread.sleep(3000); // evita bater rate limit da iTunes Search API
    }

    return resultado;
}


    
private long buscaDeezerArtistId(String artista) {
    String url = "https://api.deezer.com/search/artist?q=" + URLEncoder.encode(artista, StandardCharsets.UTF_8) + "&limit=1";
    try {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject resposta = JsonParser.parseString(response.body()).getAsJsonObject();
        JsonArray dados = resposta.getAsJsonArray("data");

        if (dados.isEmpty()) return -1;

        return dados.get(0).getAsJsonObject().get("id").getAsLong();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return -1;
    }
}


private String buscaDeezerTopTracks(long artistId, int limite) {
    String url = "https://api.deezer.com/artist/" + artistId + "/top?limit=" + limite;
    try {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
        return null;
    }
}

public ArrayList<Musica> buscaMusicasPopularesDeezer(String artista, String generoJogo, int quantidade) throws InterruptedException {
    ArrayList<Musica> resultado = new ArrayList<>();

    long artistId = buscaDeezerArtistId(artista);
    if (artistId == -1) {
        System.out.println("Deezer não encontrou o artista: " + artista);
        return resultado;
    }

    String corpo = buscaDeezerTopTracks(artistId, quantidade);
    if (corpo == null)
        return resultado;

    JsonObject resposta = JsonParser.parseString(corpo).getAsJsonObject();
    if (!resposta.has("data")) {
        System.out.println("Deezer não retornou faixas para: " + artista);
        return resultado;
    }

    JsonArray faixas = resposta.getAsJsonArray("data");
    for (JsonElement el : faixas) {
        String nomeFaixa = el.getAsJsonObject().get("title").getAsString();
        Musica musica = buscaFaixaExataNoItunes(nomeFaixa, artista, generoJogo);
        if (musica != null) {
            resultado.add(musica);
        }
        Thread.sleep(3000); // evita bater rate limit da iTunes Search API
    }

    return resultado;
}



















}