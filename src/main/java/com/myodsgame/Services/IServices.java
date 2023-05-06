package com.myodsgame.Services;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public interface IServices {
    public List<Reto> getPreguntasHelper(Connection connection, String query);
    public List<Reto> getPalabrasHelper(Connection connection, String query);
    public static void reorderRetos(List<Reto> retos, int inicio, List<Integer> randomIndices) {
        for (int i = 0; i < randomIndices.size(); i++) {
            int oldIndex = randomIndices.get(i);
            int newIndex = inicio + i;
            Collections.swap(retos, oldIndex, newIndex);
        }
    }
    public List<Estadisticas> getEstadisticas(Connection connection, String query);

}
