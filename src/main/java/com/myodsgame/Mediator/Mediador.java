package com.myodsgame.Mediator;

import com.myodsgame.Controllers.WindowShower;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Mediador {
    WindowShower windowShower = new WindowShower();
    IServices servicios = new Services();
    public Optional<ButtonType> ayudaClicked(Reto retoActual, Label currentScore, Button ayuda) {
        Optional<ButtonType> result = windowShower.showAyuda();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int puntos =  EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion()/2;
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
            currentScore.setText("Score: " + puntos);
            ayuda.setDisable(true);
            EstadoJuego.getInstance().getPartida().
                    setAyudasRestantes(EstadoJuego.getInstance().getPartida().getAyudasRestantes() - 1);
        }
        return result;
    }

    public void botonAbandonarPulsado(MediaPlayer mediaPlayerTicTac, MediaPlayer mediaPlayerMusic, Button abandonarBoton, Timeline timeline) {
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada());
        servicios.guardarPuntosDiarios(EstadoJuego.getInstance().getPartida().getPuntuacion());
        if(mediaPlayerTicTac != null) mediaPlayerTicTac.stop();
        if(mediaPlayerMusic != null) mediaPlayerMusic.stop();
        Stage stageOld = (Stage) abandonarBoton.getScene().getWindow();
        stageOld.close();
        EstadoJuego.getInstance().getPartida().setPartidaAbandonada(true);
        timeline.stop();
    }

    public int checkWin(MediaPlayer mediaPlayerTicTac, MediaPlayer mediaPlayerMusic, Reto retoActual, Partida partidaActual, Button ayuda) {
        if (mediaPlayerMusic != null) mediaPlayerMusic.stop();
        if (mediaPlayerTicTac != null) mediaPlayerTicTac.stop();
        ayuda.setDisable(true);
        UserUtils.saveStats(true, retoActual.getODS(), partidaActual.getObjetoRetoActual(partidaActual.getRetoActual()).getId(), retoActual.getTipoReto().toString());
        EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual()-1] = false;
        int obtainedPoints = servicios.computePoints(retoActual, true);
        int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
        EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
        return obtainedPoints;
    }

    public int[] checkLose(MediaPlayer mediaPlayerTicTac, MediaPlayer mediaPlayerMusic, Reto retoActual, Partida partidaActual, Button ayuda) {
        if (mediaPlayerMusic != null) mediaPlayerMusic.stop();
        if (mediaPlayerTicTac != null) mediaPlayerTicTac.stop();

        UserUtils.saveStats(false, retoActual.getODS(), 0, "null");
        int obtainedPoints = servicios.computePoints(retoActual, false);
        int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
        EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual() - 1] = true;
        ayuda.setDisable(true);
        if (EstadoJuego.getInstance().getPartida().isConsolidado()) {
            EstadoJuego.getInstance().getPartida().setPuntuacionConsolidada(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada()
                    + obtainedPoints);
        }
        EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);

        int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas() - 1;
        if (vidasPartida == 0) {
            EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
            UserUtils.aumentarPartidasJugadas();
        }
        EstadoJuego.getInstance().getPartida().setVidas(vidasPartida);
        return new int[]{obtainedPoints*-1, vidasPartida};
    }

    public MediaPlayer musicaSetter() {
        Media musica = EstadoJuego.getInstance().getPartida().getMusica();
        MediaPlayer mediaPlayerMusic = new MediaPlayer(musica);
        mediaPlayerMusic.setVolume(0.15);
        return mediaPlayerMusic;
    }
    public MediaPlayer sonidoSetter(String sonidoPath, double volumen) {
        MediaPlayer mediaPlayerSonidos = new MediaPlayer(new Media(new File(sonidoPath).toURI().toString()));
        mediaPlayerSonidos.setVolume(volumen);
        return mediaPlayerSonidos;
    }

    public Stage showPopUp(Partida partidaActual) {
        Stage stage = null;
        try {
            stage = windowShower.showPopUp(partidaActual);
        }
        catch (IOException e) {System.out.println(e.getMessage());}
        return stage;
    }
}
