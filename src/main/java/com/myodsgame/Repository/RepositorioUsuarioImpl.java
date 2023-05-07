package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Pregunta;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarioImpl implements RepositorioUsuario{

    private Connection connection;

    public RepositorioUsuarioImpl() {
        connection = DBConnection.getConnection();
    }

    public Usuario getUsuarioPorUsernameYContraseña(String username, String password)  {
        Usuario user = null;
        Estadisticas estadisticas = null;

        try {
            String sql = "SELECT u.username, u.email, u.password, u.birthdate, " +
                    "e.puntos_totales, e.partidas_jugadas, e.numero_aciertos, " +
                    "e.numero_fallos, e.progreso_total_ods, e.progreso_individual_ods " +
                    "FROM usuarios u " +
                    "INNER JOIN estadisticas e ON u.username = e.username " +
                    "WHERE u.username = ? AND u.password = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute query and retrieve results
            ResultSet result = statement.executeQuery();

            // Parse results and create User and Statistics objects
            if (result.next()) {
                user = new Usuario();
                user.setUsername(result.getString("username"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setBirthdate(result.getDate("birthdate"));

                estadisticas = new Estadisticas();
                estadisticas.setPuntosTotales(result.getInt("puntos_totales"));
                estadisticas.setPartidasJugadas(result.getInt("partidas_jugadas"));
                estadisticas.setNumeroAciertos(result.getInt("numero_aciertos"));
                estadisticas.setNumeroFallos(result.getInt("numero_fallos"));
                estadisticas.setProgresoTotalOds(result.getInt("progreso_total_ods"));

                // Parse progresoIndividualOds from the database result
                String progresoIndividualOdsString = result.getString("progreso_individual_ods");
                String[] progresoIndividualOdsArray = progresoIndividualOdsString.split(",");
                int[] progresoIndividualOds = new int[18];
                for (int i = 0; i < progresoIndividualOdsArray.length; i++) {
                    int odsNumber = Integer.parseInt(progresoIndividualOdsArray[i]);
                    progresoIndividualOds[odsNumber] = odsNumber;
                }
                estadisticas.setProgresoIndividualOds(progresoIndividualOds);
                user.setEstadistica(estadisticas);
            }

            // Close JDBC objects
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }

        return user;
    }

    @Override
    public boolean checkIfUserExists(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from usuarios where username = ?");
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            return result.next();
        }catch (SQLException e){
            System.err.println("Error al obtener usuario: " + e.getMessage());
        }
        return false;
    }


    public void saveUsuario(Usuario user) {
        try {
            // Insert statement for the usuario table
            String usuarioInsert = "INSERT INTO usuarios (username, email, password, birthdate) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement usuarioStatement = connection.prepareStatement(usuarioInsert);
            usuarioStatement.setString(1, user.getUsername());
            usuarioStatement.setString(2, user.getEmail());
            usuarioStatement.setString(3, user.getPassword());
            usuarioStatement.setDate(4, new java.sql.Date(user.getBirthdate().getTime()));
            usuarioStatement.executeUpdate();
            usuarioStatement.close();

            // Insert statement for the estadisticas table
            String estadisticasInsert = "INSERT INTO estadisticas (username, puntos_totales, partidas_jugadas, " +
                    "numero_aciertos, numero_fallos, progreso_total_ods, progreso_individual_ods) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement estadisticasStatement = connection.prepareStatement(estadisticasInsert);
            estadisticasStatement.setString(1, user.getUsername());
            estadisticasStatement.setInt(2, user.getEstadistica().getPuntosTotales());
            estadisticasStatement.setInt(3, user.getEstadistica().getPartidasJugadas());
            estadisticasStatement.setInt(4, user.getEstadistica().getNumeroAciertos());
            estadisticasStatement.setInt(5, user.getEstadistica().getNumeroFallos());
            estadisticasStatement.setInt(6, user.getEstadistica().getProgresoTotalOds());

            // Convert progresoIndividualOds array to a comma-separated string
            int[] progresoIndividualOds = user.getEstadistica().getProgresoIndividualOds();
            StringBuilder progresoIndividualOdsBuilder = new StringBuilder();
            for (int i = 1; i < progresoIndividualOds.length; i++) {
                progresoIndividualOdsBuilder.append(progresoIndividualOds[i]);
                if (i < progresoIndividualOds.length - 1) {
                    progresoIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(7, progresoIndividualOdsBuilder.toString());

            estadisticasStatement.executeUpdate();
            estadisticasStatement.close();

            // Close JDBC objects
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    public void updateUsuarioEstadisticas(Usuario user) {
        try {
            // Update statement for the estadisticas table
            String estadisticasUpdate = "UPDATE estadisticas SET puntos_totales=?, partidas_jugadas=?, " +
                    "numero_aciertos=?, numero_fallos=?, progreso_total_ods=?, progreso_individual_ods=? " +
                    "WHERE username=?";

            PreparedStatement estadisticasStatement = connection.prepareStatement(estadisticasUpdate);
            estadisticasStatement.setInt(1, user.getEstadistica().getPuntosTotales());
            estadisticasStatement.setInt(2, user.getEstadistica().getPartidasJugadas());
            estadisticasStatement.setInt(3, user.getEstadistica().getNumeroAciertos());
            estadisticasStatement.setInt(4, user.getEstadistica().getNumeroFallos());
            estadisticasStatement.setInt(5, user.getEstadistica().getProgresoTotalOds());

            // Convert progresoIndividualOds array to a comma-separated string
            int[] progresoIndividualOds = user.getEstadistica().getProgresoIndividualOds();
            StringBuilder progresoIndividualOdsBuilder = new StringBuilder();
            for (int i = 1; i < progresoIndividualOds.length; i++) {
                progresoIndividualOdsBuilder.append(progresoIndividualOds[i]);
                if (i < progresoIndividualOds.length - 1) {
                    progresoIndividualOdsBuilder.append(",");
                }
            }
            estadisticasStatement.setString(6, progresoIndividualOdsBuilder.toString());

            estadisticasStatement.setString(7, user.getUsername());
            estadisticasStatement.executeUpdate();
            estadisticasStatement.close();

            // Close JDBC objects
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

}
