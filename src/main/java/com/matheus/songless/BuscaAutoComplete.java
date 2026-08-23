package com.matheus.songless;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class BuscaAutoComplete {

    public ArrayList<String> buscaComplete(String termo){
        ArrayList<String> resultados = new ArrayList<>();
        if (termo.length() < 2)
            return resultados;

        String sql = "SELECT nome, artista FROM musicasApple WHERE nome ILIKE ? OR artista ILIKE ? LIMIT 10";

        try (Connection conexao = DriverManager.getConnection(Config.getURLDB(), Config.getUSERDB(), Config.getSENHADB());
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            String termoBusca = "%" + termo + "%";
            stmt.setString(1, termoBusca);
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
} 

