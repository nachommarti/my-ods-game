package com.myodsgame.Models;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class Partida {

    private int puntuacion;
    private int vidas;
    private int retoActual;
    private boolean consolidado;
    private int ayudasRestantes = 3;
    private Reto[] retos;
    private Media musica, sonidoAyuda, sonidoAcierto, sondioFallo, sonidoVictoria, sonidoDerrota, diezsecs;
    private Image imagenVidas, imagenOds, imagenFondo;
    
    public Partida(int puntuacion, int vidas, int retoActual, boolean consolidado, int ayudasRestantes, Reto[] retos, Media musica, Media sonidoAyuda, Media sonidoAcierto, Media sondioFallo, Media sonidoVictoria, Media sonidoDerrota, Media diezsecs, Image imagenVidas, Image imagenFondo) {
        this.puntuacion = puntuacion;
        this.vidas = vidas;
        this.retoActual = retoActual;
        this.consolidado = consolidado;
        this.ayudasRestantes = ayudasRestantes;
        this.retos = retos;
        this.musica = musica;
        this.sonidoAyuda = sonidoAyuda;
        this.sonidoAcierto = sonidoAcierto;
        this.sondioFallo = sondioFallo;
        this.sonidoVictoria = sonidoVictoria;
        this.sonidoDerrota = sonidoDerrota;
        this.diezsecs = diezsecs;
        this.imagenVidas = imagenVidas;
        this.imagenFondo = imagenFondo;
    }
    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getRetoActual() {
        return retoActual;
    }

    public void setRetoActual(int retoActual) {
        this.retoActual = retoActual;
    }

    public boolean isConsolidado() {
        return consolidado;
    }

    public void setConsolidado(boolean consolidado) {
        this.consolidado = consolidado;
    }

    public int getAyudasRestantes() {
        return ayudasRestantes;
    }

    public void setAyudasRestantes(int ayudasRestantes) {
        this.ayudasRestantes = ayudasRestantes;
    }

    public Reto[] getRetos() {
        return retos;
    }

    public void setRetos(Reto[] retos) {
        this.retos = retos;
    }

    public Media getMusica() {
        return musica;
    }

    public void setMusica(Media musica) {
        this.musica = musica;
    }

    public Media getSonidoAyuda() {
        return sonidoAyuda;
    }

    public void setSonidoAyuda(Media sonidoAyuda) {
        this.sonidoAyuda = sonidoAyuda;
    }

    public Media getSonidoAcierto() {
        return sonidoAcierto;
    }

    public void setSonidoAcierto(Media sonidoAcierto) {
        this.sonidoAcierto = sonidoAcierto;
    }

    public Media getSondioFallo() {
        return sondioFallo;
    }

    public void setSondioFallo(Media sondioFallo) {
        this.sondioFallo = sondioFallo;
    }

    public Media getSonidoVictoria() {
        return sonidoVictoria;
    }

    public void setSonidoVictoria(Media sonidoVictoria) {
        this.sonidoVictoria = sonidoVictoria;
    }

    public Media getSonidoDerrota() {
        return sonidoDerrota;
    }

    public void setSonidoDerrota(Media sonidoDerrota) {
        this.sonidoDerrota = sonidoDerrota;
    }

    public Media getDiezsecs() {
        return diezsecs;
    }

    public void setDiezsecs(Media diezsecs) {
        this.diezsecs = diezsecs;
    }

    public Image getImagenVidas() {
        return imagenVidas;
    }

    public void setImagenVidas(Image imagenVidas) {
        this.imagenVidas = imagenVidas;
    }

    public Image getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(Image imagenFondo) {
        this.imagenFondo = imagenFondo;
    }
}