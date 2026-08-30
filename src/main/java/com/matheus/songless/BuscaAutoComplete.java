package com.matheus.songless;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class BuscaAutoComplete {

    private final DataSource dataSource;

    public BuscaAutoComplete(DataSource dataSource) {
        this.dataSource = dataSource;
    }



  public ArrayList<String> buscaCompleteMusica(String termo, String modo){
    ArrayList<String> resultados = new ArrayList<>();
    if (termo.length() < 2)
        return resultados;

    String sql;
    if (modo.equals("Normal"))
        sql = "SELECT nome, artista FROM musicasApple " +
              "WHERE regexp_replace(unaccent(nome), '[^a-zA-Z0-9]', '', 'g') ILIKE '%' || regexp_replace(unaccent(?), '[^a-zA-Z0-9]', '', 'g') || '%' " +
              "OR regexp_replace(unaccent(artista), '[^a-zA-Z0-9]', '', 'g') ILIKE '%' || regexp_replace(unaccent(?), '[^a-zA-Z0-9]', '', 'g') || '%' " +
              "LIMIT 30";
    else{
        sql = "SELECT nome, artista FROM musicasArtista " +
              "WHERE regexp_replace(unaccent(nome), '[^a-zA-Z0-9]', '', 'g') ILIKE '%' || regexp_replace(unaccent(?), '[^a-zA-Z0-9]', '', 'g') || '%' " +
              "LIMIT 10";
    }
    try (Connection conexao = dataSource.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, termo);   
        if (modo.equals("Normal"))
            stmt.setString(2, termo);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultados.add(rs.getString("nome") + " - " + rs.getString("artista"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultados;
}

public ArrayList<String> buscaCompleteArtista(String termo){
    ArrayList<String> resultados = new ArrayList<>();
    if (termo.length() < 2)
        return resultados;

    String sql = "SELECT DISTINCT artista FROM musicasArtista " +
                 "WHERE regexp_replace(unaccent(artista), '[^a-zA-Z0-9]', '', 'g') ILIKE '%' || regexp_replace(unaccent(?), '[^a-zA-Z0-9]', '', 'g') || '%' " +
                 "LIMIT 30";

    try (Connection conexao = dataSource.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        stmt.setString(1, termo);   // sem os % aqui

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                resultados.add(rs.getString("artista"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultados;
}
}