package com.matheus.songless;

import java.util.ArrayList;

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
    public Musica sortear(@RequestParam String opcao,@RequestParam String artista, @RequestParam (required = false) String excluir){
        ArrayList<Integer> idsExcluidos = new ArrayList<>();
                if (excluir != null && !excluir.isBlank()) {
            for (String id : excluir.split(",")) {
                idsExcluidos.add(Integer.parseInt(id.trim()));
            }
        }
        return repositorio.escolherAleatoria(opcao, artista, idsExcluidos);

    }
}
