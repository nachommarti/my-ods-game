package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Palabra;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RepositorioPalabraImpl implements RepositorioPalabra{

    private Connection connection;

    public RepositorioPalabraImpl() {
        connection = DBConnection.getConnection();
    }

    @Override
    public List<Reto> getPalabras() {
        String query = "SELECT * FROM palabras";
        return getPalabrasHelper(query);
    }

    private List<Reto> getPalabrasHelper(String query){
        List<Reto> palabras = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("palabra", rs.getString("palabra"));
                map.put("intentos", 0);
                palabras.add(RetoFactory.crearReto(false, 30,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        TipoReto.AHORACADO, map));
            }
            return palabras;
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
            return null;
        }
    }
}
