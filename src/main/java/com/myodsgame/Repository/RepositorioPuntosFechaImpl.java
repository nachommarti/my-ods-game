package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.PuntuacionDiariaPK;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.EstadoJuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPuntosFechaImpl implements Repositorio<Estadisticas, PuntuacionDiariaPK> {

    private final Connection connection;

    public RepositorioPuntosFechaImpl() {connection = DBConnection.getConnection();}

    @Override
    public void create(Estadisticas estadisticas) {
        String sql = "INSERT INTO puntuacionDiaria (usuario, puntos, fecha) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.setInt(2, EstadoJuego.getInstance().getPartida().getPuntuacion());
            statement.setString(3, LocalDate.now().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Estadisticas findById(PuntuacionDiariaPK puntuacionDiariaPK) {
        String sql = "SELECT * FROM puntuacionDiaria WHERE usuario = ? AND fecha = ?";
        Estadisticas estadisticas = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, puntuacionDiariaPK.getUsuario());
            statement.setString(2, puntuacionDiariaPK.getFecha().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                estadisticas = new Estadisticas();
                estadisticas.setUsuario(resultSet.getString("usuario"));
                estadisticas.setPuntosTotales(resultSet.getInt("puntos"));
                estadisticas.setPosicion(0);
                estadisticas.setAciertos_individual_ods(null);
                estadisticas.setFallos_individual_ods(null);
                estadisticas.setPartidasJugadas(0);
                estadisticas.setNumeroAciertos(0);
                estadisticas.setNumeroFallos(0);
                estadisticas.setNivel(0);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }

    @Override
    public List<Estadisticas> findAll() {
        List<Estadisticas> stats = null;
        try{
            String query = "SELECT * FROM puntuacionDiaria ORDER BY puntos DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            stats = fillEstadisticas(rs, "puntos");
        }catch(SQLException e){
            System.err.println("Error al obtener los Puntos Diarios: " + e);
        }
        return stats;
    }

    @Override
    public List<Estadisticas> findByLimit(Integer valor1, Integer valor2) {
        List<Estadisticas> stats = null;
        try{
            String query = "SELECT * FROM puntuacionDiaria ORDER BY RAND() LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, valor1+valor2);
            ResultSet rs = statement.executeQuery();
            stats = fillEstadisticas(rs, "puntos");
        }catch(SQLException e){
            System.err.println("Error al obtener los Puntos Diarios: " + e);
        }
        return stats;
    }

    @Override
    public void insert(Estadisticas estadisticas, PuntuacionDiariaPK pk) {
        if (findById(pk) == null) create(estadisticas);
        else update(estadisticas, pk);
    }

    @Override
    public void update(Estadisticas estadisticas, PuntuacionDiariaPK pk) {
        try {
            String insertQuery = "UPDATE puntuacionDiaria SET usuario = ?, puntos = ?, fecha = ? " +
                                 "WHERE usuario = ? AND fecha = ?";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, estadisticas.getUsuario());
            insertStatement.setInt(2, findById(pk).getPuntosTotales() + EstadoJuego.getInstance().getPartida().getPuntuacion());
            insertStatement.setString(3, LocalDate.now().toString());
            insertStatement.setString(4, pk.getUsuario());
            insertStatement.setString(5, pk.getFecha().toString());
            insertStatement.executeUpdate();
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Estadisticas estadisticas) {
        String sql = "DELETE FROM puntuacionDiaria WHERE username = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estadisticas.getUsuario());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(PuntuacionDiariaPK puntuacionDiariaPK) {
        String sql = "DELETE FROM puntuacionDiaria WHERE username = ? AND fecha = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, puntuacionDiariaPK.getUsuario());
            statement.setString(2, puntuacionDiariaPK.getFecha().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Estadisticas> getPuntosDiarios(){
        List<Estadisticas> stats = null;
        try {
            String query = "SELECT * FROM puntuacionDiaria WHERE fecha = ? ORDER BY puntos DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, LocalDate.now().toString());
            ResultSet rs = statement.executeQuery();
            stats = fillEstadisticas(rs, "puntos");

        } catch(SQLException e){
            System.err.println("Error al obtener los Puntos Diarios: " + e);
        }
        return stats;
    }

    public List<Estadisticas> getPuntosSemanales(){
        List<Estadisticas> stats = null;
        try {
            String query = "SELECT usuario, SUM(puntos) AS total_puntos FROM puntuacionDiaria WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY usuario ORDER BY total_puntos DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            stats = fillEstadisticas(rs, "total_puntos");

        } catch(SQLException e){
            System.err.println("Error al obtener los Puntos Semanales: " + e);
        }
        return stats;
    }

    private List<Estadisticas> fillEstadisticas(ResultSet rs, String nombrePuntos) throws SQLException {
        List<Estadisticas> stats = new ArrayList<>();
        int posicion = 1;
        while(rs.next()) {
            Estadisticas estadisticas = new Estadisticas();
            estadisticas.setUsuario(rs.getString("usuario"));
            estadisticas.setPuntosTotales(rs.getInt(nombrePuntos));
            estadisticas.setPosicion(posicion);
            posicion++;
            estadisticas.setAciertos_individual_ods(null);
            estadisticas.setFallos_individual_ods(null);
            estadisticas.setPartidasJugadas(0);
            estadisticas.setNumeroAciertos(0);
            estadisticas.setNumeroFallos(0);
            estadisticas.setNivel(0);
            stats.add(estadisticas);
        }
        return stats;
    }
}
