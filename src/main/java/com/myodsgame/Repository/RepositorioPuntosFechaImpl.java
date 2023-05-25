package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.PuntuacionDiaria;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;
import com.myodsgame.Utils.EstadoJuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPuntosFechaImpl implements RepositorioPuntosFecha {

    private final Connection connection;
    private Services services = new Services();

    public RepositorioPuntosFechaImpl() {connection = DBConnection.getConnection();}

    public void guardarPuntos(PuntuacionDiaria puntuacionDiaria){
        try {
            String query = "SELECT * FROM puntuacionDiaria WHERE usuario = ? AND fecha = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, puntuacionDiaria.getUsuario());
            statement.setString(2, puntuacionDiaria.getFecha().toString());

            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                int puntosTotales = rs.getInt("puntos") + puntuacionDiaria.getPuntos();
                String updateQuery = "UPDATE puntuacionDiaria SET puntos = ? WHERE usuario = ? AND fecha = ?";

                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

                updateStatement.setInt(1, puntosTotales);
                updateStatement.setString(2, puntuacionDiaria.getUsuario());
                updateStatement.setString(3, puntuacionDiaria.getFecha().toString());

                updateStatement.executeUpdate();
                updateStatement.close();
            }
            else{
                String insertQuery = "INSERT INTO puntuacionDiaria (usuario, puntos, fecha) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                insertStatement.setString(1, puntuacionDiaria.getUsuario());
                insertStatement.setInt(2, puntuacionDiaria.getPuntos());
                insertStatement.setString(3, puntuacionDiaria.getFecha().toString());

                insertStatement.executeUpdate();
                insertStatement.close();
            }

        }catch(SQLException e){
            System.err.println("Error al obtener objeto: " + e);
        }
    }
    public List<Estadisticas> getPuntosDiarios(){
        try{
            List<Estadisticas> stats = new ArrayList<>();
            int posicion = 1;

            String query = "SELECT * FROM puntuacionDiaria WHERE fecha = ? ORDER BY puntos DESC";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, LocalDate.now().toString());

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Estadisticas estadisticas = new Estadisticas();
                estadisticas.setUsuario(rs.getString("usuario"));
                estadisticas.setPuntosTotales(rs.getInt("puntos"));
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

        }catch(SQLException e){
            System.err.println("Error al obtener los Puntos Diarios: " + e);
            return null;
        }
    }

    public List<Estadisticas> getPuntosSemanales(){
        try{
            List<Estadisticas> stats = new ArrayList<>();
            int posicion = 1;

            String query = "SELECT usuario, SUM(puntos) AS total_puntos FROM puntuacionDiaria WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) GROUP BY usuario ORDER BY total_puntos";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Estadisticas estadisticas = new Estadisticas();
                estadisticas.setUsuario(rs.getString("usuario"));
                estadisticas.setPuntosTotales(rs.getInt("total_puntos"));
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

        }catch(SQLException e){
            System.err.println("Error al obtener los Puntos Semanales: " + e);
            return null;
        }
    }
}
