package com.myodsgame.Repository;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.PuntuacionDiaria;
import java.util.List;

public interface RepositorioPuntosFecha {

    List<Estadisticas> getPuntosDiarios();

    List<Estadisticas> getPuntosSemanales();

    void guardarPuntos(PuntuacionDiaria puntuacionDiaria);
}
