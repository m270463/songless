package com.matheus.songless;


public class Config {
    public static String getUSERDB(){
        return SonglessApplication.dotenv.get("USER_DB");
    }

    public static String getSENHADB(){
        return SonglessApplication.dotenv.get("SENHA_DB");
    }

    public static String getURLDB(){
        return SonglessApplication.dotenv.get("URL_DB");
    }
}
