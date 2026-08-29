package com.matheus.songless;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

@Repository
public class BuscaArtista {

    private final DataSource dataSource;

    public BuscaArtista(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean validaArtista(String termo){
        String sql = "SELECT artista FROM musicasArtista WHERE artista = ?";
        try (Connection conexao = dataSource.getConnection();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, termo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}