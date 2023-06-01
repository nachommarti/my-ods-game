package com.myodsgame.Services;

import com.myodsgame.Models.*;
import com.myodsgame.Repository.*;
import com.myodsgame.Utils.EstadoJuego;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;


public class Services implements IServices {

    public List<Integer> stringToIntList(String string) {
        String[] array = string.split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            list.add(i, Integer.parseInt(array[i]));
        }
        return list;
    }

    public String intListToString(List<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i));
            if (i < list.size() - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    public int[] stringToIntArray(String string) {
        String[] split = string.split(",");
        int[] array = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Integer.parseInt(split[i]);
        }
        return array;
    }

    public String intArrayToString(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]);
            if (i < array.length - 1) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
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
    public void updateUser(Usuario user, String oldUsername) {
        RepositorioUsuarioImpl repositorioUsuario = new RepositorioUsuarioImpl();
        repositorioUsuario.update(user, oldUsername);
    }

    public int computePoints(Reto retoActual, boolean retoAcertado) {
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

   public int getNumeroTotalRetos(){
        RepositorioPreguntaImpl<RetoPregunta> repositorioPregunta = new RepositorioPreguntaImpl<>();
        RepositorioPalabraImpl<RetoPregunta> repositorioPalabra = new RepositorioPalabraImpl<>();
        RepositorioFraseImpl<RetoPregunta> repositorioFrase = new RepositorioFraseImpl<>();
        return repositorioPregunta.getNumeroTotalRetos() + repositorioPalabra.getNumeroTotalRetos() + repositorioFrase.getNumeroTotalRetos();
   }

   public int[] getNumeroTotalRetosPorODS(){
       RepositorioPreguntaImpl<RetoPregunta> repositorioPregunta = new RepositorioPreguntaImpl<>();
       RepositorioPalabraImpl<RetoPregunta> repositorioPalabra = new RepositorioPalabraImpl<>();
       RepositorioFraseImpl<RetoPregunta> repositorioFrase = new RepositorioFraseImpl<>();
       int[] retosPorODS = new int[17];
       for(int i = 0; i < retosPorODS.length; i++){
           retosPorODS[i] = repositorioPregunta.getNumeroTotalRetosPorODS(i+1)
                   + repositorioPalabra.getNumeroTotalRetosPorODS(i+1) + repositorioFrase.getNumeroTotalRetosPorODS(i+1);
       }
       return retosPorODS;
   }

   public void guardarPuntosDiarios(int puntos){
       Repositorio<Estadisticas, String> repositorioEstadisticas = new RepositorioEstadisticasImpl();
       Repositorio<Estadisticas, PuntuacionDiariaPK> repositorioPuntosFecha = new RepositorioPuntosFechaImpl();
       Estadisticas estadisticas = repositorioEstadisticas.findById(EstadoJuego.getInstance().getUsuario().getUsername());
       PuntuacionDiariaPK pk = new PuntuacionDiariaPK(estadisticas.getUsuario());
       repositorioPuntosFecha.insert(estadisticas, pk);
   }

    public Set<Integer> parsearStringASet(String input){
        Set<Integer> output = new HashSet<>();
        String sinCorchetes = input.substring(1, input.length() - 1);
        if(!sinCorchetes.equals("")) {
            String[] idEnString = sinCorchetes.split(", ");
            int[] ids = new int[idEnString.length];

            for (int i = 0; i < idEnString.length; i++) {
                ids[i] = Integer.parseInt(idEnString[i]);
            }

            for (int id : ids) {
                output.add(id);
            }
        }
        return output;
    }
}
