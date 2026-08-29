package com.matheus.songless;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    private final MusicaRepositorio repositorio;
    
    public MusicController(MusicaRepositorio repositorio){
        this.repositorio = repositorio;
    }
    
    

    @GetMapping("/api/musica")
    public Musica sortear(@RequestParam String opcao,@RequestParam String artista){
        return repositorio.escolherAleatoria(opcao,artista);
    }
}
