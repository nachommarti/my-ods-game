package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
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

public class RepositorioPalabraImpl<T extends Reto> implements Repositorio<T, Integer> {
    private final Connection connection;
    private final IServices services;
    private final PuntosManager puntosManager;
    public RepositorioPalabraImpl() {
        connection = DBConnection.getConnection();
        services = new Services();
        puntosManager = new PuntosManager();
    }
    @Override
    public void create(T entidad) {
        String sql = "INSERT INTO palabras (id, palabra, pista, ODS, nivel_dificultad) " +
                "VALUES (?, ?, ?, ?, ?)";
        RetoAhorcado reto = null;
        if (entidad instanceof RetoAhorcado) reto = (RetoAhorcado) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.setString(2, reto.getPalabra());
            statement.setString(3, reto.getPista());
            statement.setString(4, services.intListToString(reto.getODS()));
            statement.setInt(5, reto.getDificultad());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T findById(Integer id) {
        T retoAhorcado = null;
        try {
            String sql = "SELECT * FROM preguntas WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) retoAhorcado = mapResultSetToRetoAhorcado(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retoAhorcado;
    }

    @Override
    public List<T> findAll() {
        List<T> retos = new ArrayList<>();
        String sql =
                "SELECT * FROM palabras";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) retos.add(mapResultSetToRetoAhorcado(resultSet));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retos;
    }

    @Override
    public List<T> findByLimit(Integer numFacil, Integer numResto) {
        List<T> palabras = new ArrayList<>();
        String query =
                "SELECT * FROM " +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 1 +
                        " ORDER BY RAND() LIMIT ?) AS dificultad_incial " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 2 +
                        " ORDER BY RAND() LIMIT ?) " +
                        "UNION ALL" +
                        "(SELECT * FROM palabras WHERE nivel_dificultad = " + 3 +
                        " ORDER BY RAND() LIMIT ?) "
                ;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, numFacil);
            pstmt.setInt(2, numResto);
            pstmt.setInt(3, numResto);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                palabras.add(mapResultSetToRetoAhorcado(resultSet));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener palabras: " + e.getMessage());
        }
        return palabras;
    }

    @Override
    public void insert(T reto, Integer id) {
        if (findById(id) == null) create(reto);
        else update(reto, id);
    }

    @Override
    public void update(T entidad, Integer id) {
        String sql = "UPDATE palabras SET palabra = ?, pista = ?, ODS = ?, nivel_dificultad = ? WHERE id = ? ";
        RetoAhorcado reto = null;
        if (entidad instanceof RetoAhorcado) reto = (RetoAhorcado) entidad;

        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, reto.getPalabra());
            statement.setString(2, reto.getPista());
            statement.setString(3, services.intListToString(reto.getODS()));
            statement.setInt(4, reto.getDificultad());
            statement.setInt(5, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T reto) {
        String sql = "DELETE FROM palabras WHERE id = ?";
        if (reto.getTipoReto() != TipoReto.AHORACADO) throw new IllegalArgumentException("Tipo de reto incorrecto");
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
        String sql = "DELETE FROM palabras WHERE id = ?";
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
        String query = "SELECT COUNT(*) FROM palabras";
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
        String query = "SELECT COUNT(*) FROM palabras WHERE ODS = ?";
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

    private T mapResultSetToRetoAhorcado(ResultSet resultSet) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("palabra", resultSet.getString("palabra"));
        map.put("pista", resultSet.getString("pista"));
        List<Integer> Ods = services.stringToIntList(resultSet.getString("ODS"));

        return (T) RetoFactory.crearReto(resultSet.getInt("id"), false, 120,
                20, resultSet.getInt("nivel_dificultad"),
                puntosManager.SetPuntosStrategy(resultSet.getInt("nivel_dificultad")),
                Ods, TipoReto.AHORACADO, map);
    }
}
