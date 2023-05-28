package com.myodsgame.Services;

import com.myodsgame.Models.Reto;
import com.myodsgame.Models.Usuario;

import java.util.List;

public interface IServices {
    List<Integer> stringToIntList(String string);
    String intListToString(List<Integer> list);
    void reorderRetos(List<Reto> retos, int inicio, List<Integer> randomIndices);
    void updateUser(Usuario user, String oldUsername);
    int computePoints(Reto retoActual, boolean ayudaUsada, boolean retoAcertado);
    void linkClicked(Reto retoActual);
    void levelUp();
    int getNumeroTotalRetos();
    int[] getNumeroTotalRetosPorODS();
    void guardarPuntosDiarios(int puntos);
}
