package com.myodsgame.Services;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.Usuario;

import java.sql.Connection;
import java.util.List;

public interface IServices {
    public List<Reto> getPreguntasHelper(Connection connection, String query);
    public List<Reto> getPalabrasHelper(Connection connection, String query);
    public void reorderRetos(List<Reto> retos, int inicio, List<Integer> randomIndices);
    public List<Estadisticas> getEstadisticas(Connection connection, String query);
    public void saveUser(Usuario user);
}
