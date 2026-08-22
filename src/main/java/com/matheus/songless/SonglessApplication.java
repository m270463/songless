package com.matheus.songless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SonglessApplication {
	public static Dotenv dotenv = Dotenv.load();
	public static void main(String[] args) {
		SpringApplication.run(SonglessApplication.class, args);
	}

}
