package com.myodsgame.Repository;

import com.myodsgame.Models.Palabra;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioPalabraImpl implements RepositorioPalabra{

    private Connection connection;

    public RepositorioPalabraImpl() {
        connection = DBConnection.getConnection();
    }

    @Override
    public List<Palabra> getPalabras() {
        String query = "SELECT * FROM palabras";
        return getPalabrasHelper(query);
    }

    private List<Palabra> getPalabrasHelper(String query){
        List<Palabra> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Palabra.Builder()
                        .setId(rs.getInt("id"))
                        .setPalabra(rs.getString("palabra"))
                        .setNivelDificultad(rs.getInt("nivel_dificultad"))
                        .build());
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }
}
