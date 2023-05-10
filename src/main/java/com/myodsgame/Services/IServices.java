package com.myodsgame.Services;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;

import java.sql.Connection;
import java.util.List;

public interface IServices {
    List<Reto> getPreguntasHelper(Connection connection, String query);
    List<Reto> getPalabrasHelper(Connection connection, String query);
    void reorderRetos(List<Reto> retos, int inicio, List<Integer> randomIndices);
    List<Estadisticas> getEstadisticas(Connection connection, String query);
    void updateUser(String newUser, String oldUser, String email, String avatar);
    int computePoints(Reto retoActual, boolean ayudaUsada, boolean retoAcertado);
}
