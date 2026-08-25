package com.matheus.songless;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class MusicaRepositorio {
    private final String url = Config.getURLDB();
    private final String user = Config.getUSERDB();
    private final String password = Config.getSENHADB();

    public void salvarMusica(Musica musica){
        String sql = "INSERT INTO musicasApple (nome, artista, album, anoLancamento, linkAudio, genero, appleId, linkImagem, linkRedirecionamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                stmt.setString(1, musica.getNome());
                stmt.setString(2, musica.getArtista());
                stmt.setString(3, musica.getAlbum());
                stmt.setInt(4, musica.getAnoLancamento());
                stmt.setString(5, musica.getLinkAudio());
                stmt.setString(6, musica.getGenero());
                stmt.setLong(7, musica.getAppId());
                stmt.setString(8,musica.getLinkImagem());
                stmt.setString(9,musica.getLinkRedirecionamento());
                stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()){
                while(rs.next()){
                    musica.setId(rs.getInt(1));
                }
            }

        }catch(SQLException e){
            System.out.print(e.getMessage());
        };
    
    }

    public Musica escolherAleatoria(String opcao){
        String sql = "";
        if (opcao.equals("Todos"))
            sql = "SELECT * FROM musicasApple ORDER BY RANDOM() LIMIT 1";
        if (opcao.equals("Rock")){
            sql = "SELECT * FROM musicasApple WHERE genero = 'Rock' ORDER BY RANDOM() LIMIT 1";
        }
        if (opcao.equals("MPB")){
             sql = "SELECT * FROM musicasApple WHERE genero = 'MPB' ORDER BY RANDOM() LIMIT 1";
        }

        if (opcao.equals("Matuê")){
            sql = "SELECT * FROM musicasApple WHERE genero = 'Matuê' ORDER BY RANDOM() LIMIT 1";
        }
        
        try (Connection conexao = DriverManager.getConnection(url,user,password);
            Statement stmt = conexao.createStatement()){
                try (ResultSet rs = stmt.executeQuery(sql)){
                    while (rs.next()) {
                        Musica musica = new Musica(rs.getString("nome"),
                                        rs.getString("artista"),
                                        rs.getString("album"),
                                        rs.getInt("anoLancamento"),
                                        rs.getString("linkAudio"),
                                        rs.getLong("appleId"),
                                        rs.getString("genero"),
                                        rs.getString("linkImagem"),
                                        rs.getString("linkRedirecionamento"));
                        musica.setId(rs.getInt("id"));
                        musica.setUltimaAtualizacao(rs.getTimestamp("ultimaAtualizacao").toLocalDateTime());
                        return musica;
                    }

                }


        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        
        
        return null;
    }   
}
