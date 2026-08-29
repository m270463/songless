package com.matheus.songless;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ArtistaController {
    private final BuscaArtista buscaArtista;

    public ArtistaController(BuscaArtista buscaArtista){
        this.buscaArtista = buscaArtista;
    }

    @GetMapping("/api/artista")
    public boolean validarArtista(@RequestParam String artista){
        return  buscaArtista.validaArtista(artista);      
    }
}
