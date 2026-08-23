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
    private String buscaTermo(String termo, int limite){
        String url = "https://itunes.apple.com/search?term=" + URLEncoder.encode(termo, StandardCharsets.UTF_8)
                + "&entity=song&limit=" + limite;
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


}