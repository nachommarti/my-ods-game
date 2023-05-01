package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Palabra;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        List<Palabra> palabras = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                palabras.add(new Palabra(
                        rs.getString("palabra"),
                        rs.getInt("nivelDificultad")
                ));
            }
            return palabras;
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
            return null;
        }
    }
}
