package com.myodsgame.Services;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.Usuario;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.TipoReto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Services implements IServices {
    RepositorioUsuario repositorioUsuario = new RepositorioUsuarioImpl();
    @Override
    public List<Reto> getPreguntasHelper(Connection connection, String query) {
        List<Reto> preguntas = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("enunciado", rs.getString("enunciado"));
                map.put("respuesta1", rs.getString("respuesta1"));
                map.put("respuesta2", rs.getString("respuesta2"));
                map.put("respuesta3", rs.getString("respuesta3"));
                map.put("respuesta4", rs.getString("respuesta4"));
                map.put("respuesta_correcta", rs.getString("respuesta_correcta"));
                List<Integer> Ods = buildOds(rs.getString("ODS"));

                preguntas.add(RetoFactory.crearReto(false, 30, 10,
                        rs.getInt("nivel_dificultad"), rs.getInt("nivel_dificultad")*100,
                        Ods, TipoReto.PREGUNTA, map));
            }
            return preguntas;
        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reto> getPalabrasHelper(Connection connection, String query) {
        List<Reto> palabras = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                map.put("palabra", rs.getString("palabra"));
                map.put("pista", rs.getString("pista"));
                List<Integer> Ods = buildOds(rs.getString("ODS"));

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

    public List<Integer> buildOds(String OdsString) {
        String[] OdsArray = OdsString.split(",");
        List<Integer> Ods = new ArrayList<>();
        for (int i = 0; i < OdsArray.length; i++) {
            int odsNumber = Integer.parseInt(OdsArray[i]);
            Ods.add(i, odsNumber);
        }
        return Ods;
    }

    @Override
    public void reorderRetos(List<Reto> retos, int inicio, List<Integer> randomIndices) {
        for (int i = 0; i < randomIndices.size(); i++) {
            int oldIndex = randomIndices.get(i);
            int newIndex = inicio + i;
            Collections.swap(retos, oldIndex, newIndex);
        }
    }

    @Override
    public List<Estadisticas> getEstadisticas(Connection connection, String query){
        List<Estadisticas> estadisticas = new ArrayList<>();

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Estadisticas est = new Estadisticas();

                String aIndOds = rs.getString("aciertos_individual_ods");
                String[] aciertosIndOds = aIndOds.split(",");
                int[] aciertosIndividualOds = new int[aciertosIndOds.length];
                for(int i = 0; i < aciertosIndOds.length; i++) {
                    aciertosIndividualOds[i] = Integer.parseInt(aciertosIndOds[i].trim());
                }
                est.setAciertos_individual_ods(aciertosIndividualOds);

                String fIndOds = rs.getString("fallos_individual_ods");
                String[] fallosIndOds = fIndOds.split(",");
                int[] fallosIndividualOds = new int[fallosIndOds.length];
                for(int i = 0; i < fallosIndOds.length; i++) {
                    fallosIndividualOds[i] = Integer.parseInt(fallosIndOds[i].trim());
                }
                est.setFallos_individual_ods(fallosIndividualOds);

                est.setPuntosTotales(rs.getInt("puntos_totales"));
                est.setNumeroAciertos(rs.getInt("numero_aciertos"));
                est.setNumeroFallos(rs.getInt("numero_fallos"));
                est.setUsuario(rs.getString("username"));

                estadisticas.add(est);
            }
            return estadisticas;
        }catch(SQLException e){
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateUser(String newUser, String oldUser, String email) {
        repositorioUsuario.updateUsuario(newUser, oldUser, email);
    }

    public int computePoints(Reto retoActual, boolean ayudaUsada, boolean retoAcertado) {
        int obtainedPoints;

        if (retoAcertado) {
            if (ayudaUsada)
                obtainedPoints = retoActual.getPuntuacion() / 2;
            else
                obtainedPoints = retoActual.getPuntuacion();
        } else {
            obtainedPoints = -retoActual.getDificultad()*2*50;
            if (EstadoJuego.getInstance().getPartida().getPuntuacion() + obtainedPoints < 0)
                obtainedPoints = 0;

        }
        return obtainedPoints;
    }
}
