package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPalabraImpl implements RepositorioRetos {

    private final Connection connection;
    private final Services services;

    public RepositorioPalabraImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
    }

    public List<Reto> getRetosPorNivelDificultadInicial(int nivelDificultad, int numFacil, int numResto) {
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + ++nivelDificultad +
                        " ORDER BY RAND() LIMIT ?) "
                ;
        return getPalabrasHelper(query, numFacil, numResto);
    }

    public List<Reto> getPalabrasHelper(String query, int numFacil, int numResto) {
        List<Reto> palabras = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, numFacil);
            pstmt.setInt(2, numResto);
            pstmt.setInt(3, numResto);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("palabra", rs.getString("palabra"));
                map.put("pista", rs.getString("pista"));
                List<Integer> Ods = services.buildOds(rs.getString("ODS"));

                palabras.add(RetoFactory.crearReto(false, 120, 20,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        Ods, TipoReto.AHORACADO, map));
            }
            return palabras;
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
            return null;
        }
    }

    public int getNumeroTotalRetos(){
        String query = "SELECT COUNT(*) FROM palabras";
        int result = -1;
        try {
            PreparedStatement repo = connection.prepareStatement(query);
            ResultSet rs = repo.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
            return result;

        }catch (SQLException e){
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
            return result;
        }
    }

    public int getNumeroTotalRetosPorODS(int ods){
        String query = "SELECT COUNT(*) FROM palabras p WHERE p.ODS = ?";
        int result = 0;
        try{
            PreparedStatement repo = connection.prepareStatement(query);
            repo.setInt(1, ods);
            ResultSet rs = repo.executeQuery();

            if(rs.next()){
                result = rs.getInt(1);
            }
            return result;
        }catch(SQLException e){
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
            return -1;
        }
    }
}
