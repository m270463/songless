package com.matheus.songless;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {
    @GetMapping("/api/musica")
    public Musica sortear(){
        String linkAudio = "https://interactive-examples.mdn.mozilla.net/media/cc0-audio/t-rex-roar.mp3";
        return new Musica("Somebody to Love", "Queen", "A Night at the Opera", 1976, linkAudio);
    }
}
