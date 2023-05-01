package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
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
    public List<RetoAhorcado> getPalabras() {
        String query = "SELECT * FROM palabras";
        return getPalabrasHelper(query);
    }

    private List<RetoAhorcado> getPalabrasHelper(String query){
        List<RetoAhorcado> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new RetoAhorcado(
                        false,30,
                        rs.getInt("dificultad"),
                        rs.getInt("dificultad") * 100,
                        rs.getString("tipo"),
                        rs.getString("palabra"), new ArrayList<>(), 6
                ));
            }
            return list;
        } catch (SQLException e) {
            System.err.println("Error al obtener personas: " + e.getMessage());
            return null;
        }
    }
}
