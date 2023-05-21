package com.myodsgame.Services;

import com.myodsgame.Factory.RetoFactory;
import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Models.Reto;
import com.myodsgame.Repository.RepositorioUsuario;
import com.myodsgame.Repository.RepositorioUsuarioImpl;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.TipoReto;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

    @Override
    public void linkClicked(Reto retoActual) {
        StringBuilder sb = new StringBuilder("https://www.un.org/sustainabledevelopment/es/");
        List<Integer> ods = retoActual.getODS();
        if (ods.size() == 1) {
            switch(ods.get(0)) {
                case 1:
                    sb.append("poverty/");
                    break;
                case 2:
                    sb.append("hunger/");
                    break;
                case 3:
                    sb.append("health/");
                    break;
                case 4:
                    sb.append("education/");
                    break;
                case 5:
                    sb.append("gender-equality/");
                    break;
                case 6:
                    sb.append("water-and-sanitation/");
                    break;
                case 7:
                    sb.append("energy/");
                    break;
                case 8:
                    sb.append("economic-growth/");
                    break;
                case 9:
                    sb.append("infrastructure/");
                    break;
                case 10:
                    sb.append("inequality/");
                    break;
                case 11:
                    sb.append("cities/");
                    break;
                case 12:
                    sb.append("sustainable-consumption-production/");
                    break;
                case 13:
                    sb.append("climate-change-2/");
                    break;
                case 14:
                    sb.append("oceans/");
                    break;
                case 15:
                    sb.append("biodiversity/");
                    break;
                case 16:
                    sb.append("peace-justice/");
                    break;
                case 17:
                    sb.append("globalpartnerships/");
                    break;
            }
        }
        else sb.append("sustainable-development-goals//");
        try {
            Desktop.getDesktop().browse(new URI(sb.toString()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    public void levelUp()
    {
        int nivelActual = EstadoJuego.getInstance().getUsuario().getEstadistica().getNivel();
        EstadoJuego.getInstance().getUsuario().getEstadistica().setNivel(nivelActual + 1);
    }
}
