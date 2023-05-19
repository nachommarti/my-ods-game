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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PopUpRetoPreguntaController implements Initializable {
    @FXML
    private Label mensaje;
    @FXML
    private Button consolidarBoton;
    private Partida partidaActual;
    private Reto retoActual;
    private int obtainedPoints;
    @FXML
    private Button abandonarBoton;
    @FXML
    private Button siguientePregunta;
    @FXML
    private Hyperlink link;
    private ObservableList<Window> windows = Stage.getWindows();
    private IServices servicios;

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        servicios = new Services();
        partidaActual = EstadoJuego.getInstance().getPartida();
        retoActual = partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray());
        obtainedPoints = servicios.computePoints(retoActual, retoActual.isAyudaUsada(), true);
        consolidarBoton.setDisable(partidaActual.isConsolidado());

        abandonarBoton.setVisible(false);

        if (!partidaActual.getRetosFallados()[partidaActual.getRetoActual()-1])
        {
            mensaje.setText("¡Enhorabuena! Acabas de ganar " + partidaActual.getRetos().get(partidaActual.getRetoActual()).getPuntuacion() + " puntos");
            if (partidaActual.getRetoActual() >= 10)
            {
                mensaje.setText("Ya has terminado la partida. Felicidades, has ganado");
                abandonarBoton.setText("Menú principal");
                abandonarBoton.setVisible(true);
                siguientePregunta.setVisible(false);
                consolidarBoton.setVisible(false);
            }
        }
        else
        {
            int puntosPerdidos = Math.min((partidaActual.getRetos().get(partidaActual.getRetoActual()).getPuntuacion() * 2), partidaActual.getPuntuacion());
            mensaje.setText("¡Vaya! Has perdido " + puntosPerdidos + " puntos");
            consolidarBoton.setDisable(true);
            EstadoJuego.getInstance().getPartida().setRetoActual(partidaActual.getRetoActual()-1);
        }
        if (partidaActual.isPartidaPerdida())
        {
            mensaje.setText("Has perdido la partida ¡Suerte para la próxima!");
            abandonarBoton.setText("Menú principal");
            abandonarBoton.setVisible(true);
            siguientePregunta.setVisible(false);
            consolidarBoton.setVisible(false);
        }
        //EstadoJuego.getInstance().getPartida().getRetos().remove(numeroPregunta - 1);

    }

    @FXML
    private void consolidarBotonPulsado(ActionEvent event)
    {
        partidaActual.setConsolidado(true);
        consolidarBoton.setDisable(true);
        EstadoJuego.getInstance().getPartida().setPuntuacionConsolidada(EstadoJuego.getInstance().getPartida().getPuntuacion());
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
    }

    @FXML
    private void abandonarBotonPulsado(ActionEvent event)
    {
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion() - EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada());
        partidaActual.setPartidaAbandonada(true);
        for (Window window : windows)
        {
            if (((Stage) window).getTitle().equals("Reto Ahorcado") || ((Stage) window).getTitle().equals("Reto Pregunta"))
            {
                ((Stage) window).close();
            }
        }
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
try{FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
    BorderPane root = myLoader.load();

    Scene scene = new Scene (root);
    Stage stage2 = new Stage();
    stage2.setScene(scene);
    stage2.setTitle("Menú");
    stage2.initModality(Modality.WINDOW_MODAL);
    stage2.setResizable(false);
    stage2.show();}
        catch(IOException e){}
    }

    @FXML
    private void siguientePreguntaPulsado(ActionEvent event)
    {
        if(partidaActual.getRetoActual() == 10) {
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        for (Window window : windows)
        {
            if (((Stage) window).getTitle().equals("Reto Ahorcado") || ((Stage) window).getTitle().equals("Reto Pregunta"))
            {
                ((Stage) window).close();
            }
        }
        EstadoJuego.getInstance().getPartida().setRetoActual(partidaActual.getRetoActual()+1);
        EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray()+1);
        System.out.println("reto actual: " + partidaActual.getRetoActual());
        System.out.println("reto a elegir del array: " + partidaActual.getRetoQueHayQueMirarEnElArray());
        Stage stage = (Stage) consolidarBoton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void linkClicked(ActionEvent e) {
        servicios.linkClicked(retoActual);
    }
}

