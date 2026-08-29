package com.matheus.songless;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController          
public class AutoCompleteController {
    private final BuscaAutoComplete buscaAutoComplete;

    public AutoCompleteController(BuscaAutoComplete buscaAutoComplete){
        this.buscaAutoComplete = buscaAutoComplete;
    }

    @GetMapping("/api/autocomplete")   
    public ArrayList<String> autocomplete(@RequestParam String termo, @RequestParam String campo,@RequestParam String modo) {
        if (campo.equals("Resposta"))
            return buscaAutoComplete.buscaCompleteMusica(termo,modo);
        else
            return buscaAutoComplete.buscaCompleteArtista(termo);
    }
}