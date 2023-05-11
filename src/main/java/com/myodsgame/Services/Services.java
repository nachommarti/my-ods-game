package com.myodsgame.Services;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;
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
    public void updateUser(String newUser, String oldUser, String email, String avatar) {
        repositorioUsuario.updateUsuario(newUser, oldUser, email, avatar);
    }

    public int computePoints(Reto retoActual, boolean ayudaUsada, boolean retoAcertado) {
        int obtainedPoints;

        if (retoAcertado) {
                obtainedPoints = retoActual.getPuntuacion();
        } else {
            obtainedPoints = -retoActual.getDificultad()*2*50;
            if (EstadoJuego.getInstance().getPartida().getPuntuacion() + obtainedPoints < 0)
                obtainedPoints = 0;

        }
        return obtainedPoints;
    }
}
