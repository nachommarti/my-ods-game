package com.myodsgame.Repository;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Reto;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.TipoReto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioRetos<T extends Reto> implements Repositorio<T, Integer>{
    protected final Connection connection;
    protected final Services services;
    private final Repositorio<T, Integer> repositorioPregunta;
    private final Repositorio<T, Integer> repositorioPalabra;
    private final Repositorio<T, Integer> repositorioFrase;
    public RepositorioRetos() {
        connection = DBConnection.getConnection();
        services = new Services();
        repositorioPregunta = new RepositorioPreguntaImpl<>();
        repositorioPalabra = new RepositorioPalabraImpl<>();
        repositorioFrase = new RepositorioFraseImpl<>();
    }

    @Override
    public void create(T entidad) {
        if (entidad.getTipoReto() == TipoReto.PREGUNTA)  repositorioPregunta.create(entidad);
        else if (entidad.getTipoReto() == TipoReto.AHORACADO)  repositorioPalabra.create(entidad);
        else if (entidad.getTipoReto() == TipoReto.FRASE) repositorioFrase.create(entidad);
        else throw new IllegalArgumentException("Tipo de reto inválido.");
    }

    @Override
    public T findById(Integer integer) {
        throw new IllegalStateException("Repositorio incorrecto.");
    }

    @Override
    public List<T> findAll() {
        List<T> retos = new ArrayList<>();
        String sql =
                "SELECT * FROM preguntas" +
                "UNION ALL" +
                "SELECT * FROM palabras" +
                "UNION ALL" +
                "SELECT * FROM frases";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) retos.add(mapResultSetToReto(resultSet));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retos;
    }

    @Override
    public List<T> findByLimit(Integer valor1, Integer valor2) {
        throw new IllegalStateException("Repositorio incorrecto.");
    }

    @Override
    public void insert(T entidad, Integer id) {
        if (entidad.getTipoReto() == TipoReto.PREGUNTA)  repositorioPregunta.insert(entidad, id);
        else if (entidad.getTipoReto() == TipoReto.AHORACADO)  repositorioPalabra.insert(entidad, id);
        else if (entidad.getTipoReto() == TipoReto.FRASE) repositorioFrase.insert(entidad, id);
        else throw new IllegalArgumentException("Tipo de reto inválido.");
    }

    @Override
    public void update(T entidad, Integer id) {
        if (entidad.getTipoReto() == TipoReto.PREGUNTA)  repositorioPregunta.update(entidad, id);
        else if (entidad.getTipoReto() == TipoReto.AHORACADO)  repositorioPalabra.update(entidad, id);
        else if (entidad.getTipoReto() == TipoReto.FRASE) repositorioFrase.update(entidad, id);
        else throw new IllegalArgumentException("Tipo de reto inválido.");
    }

    @Override
    public void delete(T entidad) {
        if (entidad.getTipoReto() == TipoReto.PREGUNTA)  repositorioPregunta.delete(entidad);
        else if (entidad.getTipoReto() == TipoReto.AHORACADO)  repositorioPalabra.delete(entidad);
        else if (entidad.getTipoReto() == TipoReto.FRASE) repositorioFrase.delete(entidad);
        else throw new IllegalArgumentException("Tipo de reto inválido.");
    }

    @Override
    public void deleteById(Integer integer) {
        throw new IllegalStateException("Repositorio incorrecto.");
    }

    public int getNumeroTotalRetos(){
        String query = "SELECT" +
                "        (SELECT COUNT(*) FROM preguntas) + " +
                "        (SELECT COUNT(*) FROM palabras) + " +
                "        (SELECT COUNT(*) FROM frases) AS total_filas";
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
        String query = "SELECT" +
                "        (SELECT COUNT(*) FROM preguntas WHERE ODS = ?) + " +
                "        (SELECT COUNT(*) FROM palabras WHERE ODS = ?) + " +
                "        (SELECT COUNT(*) FROM frases WHERE ODS = ?) AS total_filas";
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

    protected T mapResultSetToReto(ResultSet resultSet) throws SQLException{
        ResultSetMetaData metaData = resultSet.getMetaData();
        String tableName = metaData.getTableName(1);
        if (tableName.equals("pregunta")) return mapResultSetToRetoPregunta(resultSet);
        else if (tableName.equals("palabras")) return mapResultSetToRetoAhorcado(resultSet);
        else return mapResultSetToRetoFrase(resultSet);
    }
    protected T mapResultSetToRetoPregunta(ResultSet resultSet) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("enunciado", resultSet.getString("enunciado"));
        map.put("respuesta1", resultSet.getString("respuesta1"));
        map.put("respuesta2", resultSet.getString("respuesta2"));
        map.put("respuesta3", resultSet.getString("respuesta3"));
        map.put("respuesta4", resultSet.getString("respuesta4"));
        map.put("respuesta_correcta", resultSet.getString("respuesta_correcta"));
        List<Integer> Ods = services.stringToIntList(resultSet.getString("ODS"));

        return (T) RetoFactory.crearReto(resultSet.getInt("id"), false, 30, 10,
                resultSet.getInt("nivel_dificultad"), resultSet.getInt("nivel_dificultad")*100,
                Ods, TipoReto.PREGUNTA, map);
    }
    protected T mapResultSetToRetoAhorcado(ResultSet resultSet) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("palabra", resultSet.getString("palabra"));
        map.put("pista", resultSet.getString("pista"));
        List<Integer> Ods = services.stringToIntList(resultSet.getString("ODS"));

        return (T) RetoFactory.crearReto(resultSet.getInt("id"), false, 120, 20,
                resultSet.getInt("nivel_dificultad"), resultSet.getInt("nivel_dificultad")*100,
                Ods, TipoReto.AHORACADO, map);
    }
    protected T mapResultSetToRetoFrase(ResultSet resultSet) throws SQLException {
        HashMap<String, String> map = new HashMap<>();
        map.put("frase", resultSet.getString("frase"));
        map.put("pista", resultSet.getString("pista"));
        List<Integer> Ods = services.stringToIntList(resultSet.getString("ODS"));

        return (T) RetoFactory.crearReto(resultSet.getInt("id"), false, 120, 20,
                resultSet.getInt("nivel_dificultad"), resultSet.getInt("nivel_dificultad")*100,
                Ods, TipoReto.FRASE, map);
    }
    protected ResultSet findBylimitHelper(String query, int numFacil, int numResto) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, numFacil);
        pstmt.setInt(2, numResto);
        pstmt.setInt(3, numResto);
        return pstmt.executeQuery();
    }
    protected void deleteHelper(T reto, String sql) {
        try {
            if (reto == null) throw new IllegalArgumentException("Tipo erróneo.");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, reto.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void deleteByIDHelper(int id, String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
