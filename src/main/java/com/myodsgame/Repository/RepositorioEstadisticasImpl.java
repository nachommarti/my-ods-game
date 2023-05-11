package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class RepositorioEstadisticasImpl implements RepositorioEstadisticas{

    private final Connection connection;

    private Services services = new Services();

    public RepositorioEstadisticasImpl() {connection = DBConnection.getConnection();}

    public List<Estadisticas> getEstadisticas(){
        String query = "SELECT * FROM estadisticas ORDER BY puntos_totales DESC;";
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
    };
}
