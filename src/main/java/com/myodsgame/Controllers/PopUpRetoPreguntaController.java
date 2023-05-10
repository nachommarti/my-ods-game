package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
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
    @FXML
    private Button abandonarBoton;
    @FXML
    private Button siguientePreguntaBoton;
    private Partida partidaActual = EstadoJuego.getInstance().getPartida();
    private int obtainedPoints = UserUtils.computePoints(EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()), EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).isAyudaUsada(), true);
    private int numeroPregunta = partidaActual.getRetoActual();
    private ObservableList<Window> windows = Stage.getWindows();
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mensaje.setText("¡Enhorabuena! Acabas de ganar " + EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).getPuntuacion() + " puntos");
        consolidarBoton.setDisable(partidaActual.isConsolidado());
        abandonarBoton.setDisable(!partidaActual.isConsolidado());
        if (numeroPregunta == 10)
        {
            abandonarBoton.setDisable(false);
            abandonarBoton.setText("Volver al menú");
            consolidarBoton.setDisable(true);
            siguientePreguntaBoton.setDisable(true);
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
        if (numeroPregunta == 10)
        {
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        for (Window window : windows)
        {
            if (((Stage) window).getTitle().equals("Reto Ahorcado"))
            {
                ((Stage) window).close();
            }
        }
        //TODO: VER EL NOMBRE DEL RETO
        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
    }
}

