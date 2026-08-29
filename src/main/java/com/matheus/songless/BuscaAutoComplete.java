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
            
        String sql = "";
        if (modo.equals("Normal"))
            sql = "SELECT nome, artista FROM musicasApple WHERE nome ILIKE ? OR artista ILIKE ? LIMIT 30";
        else
            sql = "SELECT nome, artista FROM musicasArtista WHERE nome ILIKE ? LIMIT 10";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String termoBusca = "%" + termo + "%";
            stmt.setString(1, termoBusca);
            if (modo.equals("Normal"))
                stmt.setString(2, termoBusca);

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

        String sql = "SELECT DISTINCT artista FROM musicasArtista WHERE artista ILIKE ? LIMIT 30";

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String termoBusca = "%" + termo + "%";
            stmt.setString(1, termoBusca);
            
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