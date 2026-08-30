package com.matheus.songless;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MusicaRepositorio {
    private final String url = Config.getURLDB();
    private final String user = Config.getUSERDB();
    private final String password = Config.getSENHADB();

    private Connection conexao;
    private final DataSource dataSource;

    // Construtor usado pelo ImportaMusicas (script local, fora do Spring).
    // Abre uma única conexão via DriverManager, como antes.
    public MusicaRepositorio() {
        this.dataSource = null;
        try {
            this.conexao = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco Railway", e);
        }
    }

    // Construtor usado pelo Spring em produção: recebe o DataSource/HikariCP
    // gerenciado pelo container, sem abrir conexão nenhuma aqui.
    @Autowired
    public MusicaRepositorio(DataSource dataSource) {
        this.dataSource = dataSource;
    }










    public void salvarMusica(Musica musica){
        String sql = "INSERT INTO musicasArtista (nome, artista, album, anoLancamento, linkAudio, genero, appleId, linkImagem, linkRedirecionamento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
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
            e.printStackTrace();
        };
    
    }

public Musica escolherAleatoria(String opcao, String artista, ArrayList<Integer> idsExcluidos) {
    boolean temArtista = artista != null && !artista.isBlank() && !artista.equalsIgnoreCase("null");
    boolean temExclusao = idsExcluidos != null && !idsExcluidos.isEmpty();

    String sql;
    boolean temWhere;
    if (temArtista) {
        sql = "SELECT * FROM musicasArtista WHERE artista = ?";
        temWhere = true;
    } else if (opcao.equals("Rock") || opcao.equals("MPB") || opcao.equals("Pop")) {
        sql = "SELECT * FROM musicasApple WHERE genero = ?";
        temWhere = true;
    } else {
        sql = "SELECT * FROM musicasApple";
        temWhere = false;
    }

    if (temExclusao) {
        String excluidos = (temWhere ? " AND id NOT IN (" : " WHERE id NOT IN (");
        int count = 0;
        for (Integer id : idsExcluidos) {
            if (count == idsExcluidos.size() - 1) {
                excluidos += (String.valueOf(id) + ")");
                continue;
            }
            excluidos += (String.valueOf(id) + ",");
            count++;
        }
        sql += excluidos;
    }

    sql += " ORDER BY RANDOM() LIMIT 1";

    try (Connection conexao = dataSource.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        if (temArtista) {
            stmt.setString(1, artista);
        } else if (opcao.equals("Rock") || opcao.equals("MPB") || opcao.equals("Pop")) {
            stmt.setString(1, opcao);
        }

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
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

    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }

    return null;
}
}