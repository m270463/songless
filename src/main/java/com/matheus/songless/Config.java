package com.matheus.songless;


public class Config {
    public static String getUSERDB(){
        return ImportaMusicas.dotenv.get("USER_DB");
    }

    public static String getSENHADB(){
        return ImportaMusicas.dotenv.get("SENHA_DB");
    }

    public static String getURLDB(){
        return ImportaMusicas.dotenv.get("URL_DB");
    }
}
