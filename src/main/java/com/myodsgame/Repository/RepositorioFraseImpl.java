package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoFrase;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Strategy.PuntosManager;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioFraseImpl<T extends Reto> implements Repositorio<T, Integer>{
    private final Connection connection;
    private final IServices services;
    private final PuntosManager puntosManager;
    public RepositorioFraseImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
        puntosManager = new PuntosManager();
    }
    @Override
    public void create(T entidad) {
        String sql = "INSERT INTO frases (id, frase, pista, nivel_dificultad, ODS) " +
                "VALUES (?, ?, ?, ?, ?)";
        RetoFrase reto = null;
        if (entidad instanceof RetoFrase) reto = (RetoFrase) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.setString(2, reto.getFrase());
            statement.setString(3, reto.getPista());
            statement.setInt(4, reto.getDificultad());
            statement.setString(5, services.intListToString(reto.getODS()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        List<T> retos = new ArrayList<>();
        String sql =
                "SELECT * FROM frases";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) retos.add(mapResultSetToRetoFrase(resultSet));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retos;
    }

    @Override
    public T findById(Integer id) {
        T retoFrase = null;
        try {
            String sql = "SELECT * FROM frases WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) retoFrase = mapResultSetToRetoFrase(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retoFrase;
    }

    @Override
    public List<T> findByLimit(Integer numFacil, Integer numResto) {
        List<T> frases = new ArrayList<>();
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 1 +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 2 +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM frases WHERE nivel_dificultad = " + 3 +
                        " ORDER BY RAND() LIMIT ?) "
                ;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, numFacil);
            pstmt.setInt(2, numResto);
            pstmt.setInt(3, numResto);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                frases.add(mapResultSetToRetoFrase(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener frases: " + e.getMessage());
        }
        return frases;
    }

    @Override
    public void insert(T reto, Integer id) {
        if (findById(id) == null) create(reto);
        else update(reto, id);
    }

    @Override
    public void update(T entidad, Integer id) {
        String sql = "UPDATE frases SET frase = ?, pista = ?, nivel_dificultad = ?, ODS = ? WHERE id = ? ";
        RetoFrase reto = null;
        if (entidad instanceof RetoFrase) reto = (RetoFrase) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, reto.getFrase());
            statement.setString(2, reto.getPista());
            statement.setInt(3, reto.getDificultad());
            statement.setString(4, services.intListToString(reto.getODS()));
            statement.setInt(5, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T reto) {
        String sql = "DELETE FROM frases WHERE id = ?";
        if (reto.getTipoReto() != TipoReto.FRASE) throw new IllegalArgumentException("Tipo de reto incorrecto");
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM frases WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumeroTotalRetos(){
        String query = "SELECT COUNT(*) FROM frases";
        int result = 0;
        try {
            PreparedStatement repo = connection.prepareStatement(query);
            ResultSet rs = repo.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
        }
        return result;
    }

    public int getNumeroTotalRetosPorODS(int ods){
        String query = "SELECT COUNT(*) FROM frases WHERE ODS = ?";
        int result = 0;
        try {
            PreparedStatement repo = connection.prepareStatement(query);
            repo.setString(1, String.valueOf(ods));
            ResultSet rs = repo.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        } catch(SQLException e) {
            System.err.println("Error al obtener numero de retos: " + e.getMessage());
        }
        return result;
    }

    private T mapResultSetToRetoFrase(ResultSet resultSet) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("frase", resultSet.getString("frase"));
        map.put("pista", resultSet.getString("pista"));
        List<Integer> Ods = services.stringToIntList(resultSet.getString("ODS"));

        return (T) RetoFactory.crearReto(resultSet.getInt("id"), false, 120,
                20, resultSet.getInt("nivel_dificultad"),
                puntosManager.SetPuntosStrategy(resultSet.getInt("nivel_dificultad")),
                Ods, TipoReto.FRASE, map);
    }
}
