package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PopUpRetoPreguntaController implements Initializable {
    @FXML
    private Label mensaje;
    @FXML
    private Button consolidarBoton;
    private Partida partidaActual = EstadoJuego.getInstance().getPartida();
    private int obtainedPoints = UserUtils.computePoints(EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()), EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).isAyudaUsada(), true);
    private int numeroPregunta = partidaActual.getRetoActual();
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        mensaje.setText("¡Enhorabuena! Acabas de ganar " + EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).getPuntuacion());
    }

    @FXML
    private void consolidarBotonPulsado(ActionEvent event)
    {
        consolidarBoton.setDisable(true);
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
    }

    @FXML
    private void abandonarBotonPulsado(ActionEvent event){}

    @FXML
    private void siguientePreguntaPulsado(ActionEvent event)
    {
        if(numeroPregunta == 10){
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
    }
}

