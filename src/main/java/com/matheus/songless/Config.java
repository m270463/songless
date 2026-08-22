package com.matheus.songless;


public class Config {
    public static String getUSERDB(){
        return SonglessApplication.dotenv.get("USERDB");
    }

    public static String getSENHADB(){
        return SonglessApplication.dotenv.get("SENHADB");
    }

    public static String getURLDB(){
        return SonglessApplication.dotenv.get("URLDB");
    }
}
