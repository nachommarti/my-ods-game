package com.myodsgame.Models;

import java.util.Set;

public class Estadisticas {

    private int [] aciertos_individual_ods = new int[17];
    private int [] fallos_individual_ods = new int[17];
    private int puntosTotales;
    private int partidasJugadas;
    private int numeroAciertos;
    private int numeroFallos;
    private String usuario;
    private int nivel;
    private int posicion;

    private Set<Integer> preguntasAcertadas;
    private Set<Integer> palabrasAcertadas;
    private Set<Integer> frasesAcertadas;


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int[] getAciertos_individual_ods() {
        return aciertos_individual_ods;
    }

    public void setAciertos_individual_ods(int[] aciertos_individual_ods) {
        this.aciertos_individual_ods = aciertos_individual_ods;
    }

    public int[] getFallos_individual_ods() {
        return fallos_individual_ods;
    }

    public void setFallos_individual_ods(int[] fallos_individual_ods) {
        this.fallos_individual_ods = fallos_individual_ods;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public int getNumeroAciertos() {
        return numeroAciertos;
    }

    public void setNumeroAciertos(int numeroAciertos) {
        this.numeroAciertos = numeroAciertos;
    }

    public int getNumeroFallos() {
        return numeroFallos;
    }

    public void setNumeroFallos(int numeroFallos) {
        this.numeroFallos = numeroFallos;
    }

    public String getUsuario() {return usuario;}
    public void setUsuario(String user){this.usuario = user;}

    public int getPosicion() {return posicion;}

    public void setPosicion(int pos){posicion = pos;}

    public Set<Integer> getPalabrasAcertadas(){return palabrasAcertadas;}
    public Set<Integer> getPreguntasAcertadas(){return preguntasAcertadas;}
    public Set<Integer> getFrasesAcertadas(){return frasesAcertadas;}

    public void setPreguntasAcertadas(Set<Integer> preguntasAcertadas){this.preguntasAcertadas = preguntasAcertadas;}

    public void setPalabrasAcertadas(Set<Integer> palabrasAcertadas) {this.palabrasAcertadas = palabrasAcertadas;}

    public void setFrasesAcertadas(Set<Integer> frasesAcertadas) {this.frasesAcertadas = frasesAcertadas;}

}
