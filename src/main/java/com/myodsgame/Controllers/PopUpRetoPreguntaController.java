package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Models.Reto;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PopUpRetoPreguntaController implements Initializable {
    @FXML
    private Label mensaje;
    @FXML
    private Button consolidarBoton;
    private Partida partidaActual;
    private int obtainedPoints;
    @FXML
    private Button abandonarBoton;
    @FXML
    private Button siguientePregunta;
    private int numeroPregunta;
    private ObservableList<Window> windows = Stage.getWindows();
    private IServices servicios;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        servicios = new Services();
        partidaActual = EstadoJuego.getInstance().getPartida();
        numeroPregunta = partidaActual.getRetoActual();
        obtainedPoints = servicios.computePoints(partidaActual.getRetos().get(partidaActual.getRetoActual()), partidaActual.getRetos().get(partidaActual.getRetoActual()).isAyudaUsada(), true);
        consolidarBoton.setDisable(partidaActual.isConsolidado());
        abandonarBoton.setDisable(!partidaActual.isConsolidado());

        if (!partidaActual.getRetosFallados()[numeroPregunta - 1])
        {
            mensaje.setText("¡Enhorabuena! Acabas de ganar " + partidaActual.getRetos().get(partidaActual.getRetoActual()).getPuntuacion() + " puntos");
        }
        else
        {
            mensaje.setText("¡Vaya! Has perdido " + partidaActual.getRetos().get(partidaActual.getRetoActual()).getPuntuacion() * 2 + " puntos");
            consolidarBoton.setDisable(true);
        }
        if (partidaActual.isPartidaPerdida())
        {
            mensaje.setText("Has perdido la partida ¡Suerte para la próxima!");
            abandonarBoton.setText("Menú principal");
            abandonarBoton.setDisable(false);
            siguientePregunta.setVisible(false);
            consolidarBoton.setVisible(false);
        }
    }

    @FXML
    private void consolidarBotonPulsado(ActionEvent event)
    {
        partidaActual.setConsolidado(true);
        consolidarBoton.setDisable(partidaActual.isConsolidado());
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
    }

    @FXML
    private void abandonarBotonPulsado(ActionEvent event)
    {
        partidaActual.setPartidaAbandonada(true);
        for (Window window : windows)
        {
            if (((Stage) window).getTitle().equals("Reto Ahorcado"))
            {
                ((Stage) window).close();
            }
        }
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void siguientePreguntaPulsado(ActionEvent event)
    {
        if(numeroPregunta == 10) {
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        for (Window window : windows)
        {
            if (((Stage) window).getTitle().equals("Reto Ahorcado") || ((Stage) window).getTitle().equals("Reto Pregunta"))
            {
                ((Stage) window).close();
            }
        }
        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
    }
}

