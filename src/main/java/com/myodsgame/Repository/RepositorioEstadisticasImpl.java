package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.DBConnection;

import java.sql.Connection;
import java.util.List;
public class RepositorioEstadisticasImpl implements RepositorioEstadisticas{

    private final Connection connection;

    private Services services = new Services();

    public RepositorioEstadisticasImpl() {connection = DBConnection.getConnection();}

    public List<Estadisticas> getEstadisticas(){
        String query = "SELECT * FROM estadisticas ORDER BY puntos_totales DESC;";
        return services.getEstadisticas(connection, query);
    };
}
