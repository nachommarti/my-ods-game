package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.util.Random;
import java.util.ResourceBundle;

public class RetoAhorcadoController implements Initializable {

    @FXML
    private ImageView vidas;

    @FXML
    private Button ayudaButton;

    @FXML
    private Label timer;
    @FXML
    private Label frasePista;
    @FXML
    private ImageView imagenODS;
    @FXML
    private ImageView imagenAhorcado;
    @FXML
    private Label palabraOculta;

    @FXML
    private Label consolidatedScore;
    @FXML
    private Label estatusRespuesta;
    @FXML
    private Label partidaScore;

    @FXML
    private HBox botones1;
    @FXML
    private HBox botones2;
    @FXML
    private HBox botones3;
    @FXML
    private Label palabraMostrada;
    @FXML
    private HBox labelArray;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private Button consolidarButton;
    @FXML
    private Button botonSalir;

    private Partida partidaActual;
    private RetoAhorcado retoActual;
    private String palabra;
    private int numeroPregunta;
    private int obtainedPoints;
    private boolean ayudaUsada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ayudaButton.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        setKeyBoardListeners(botones1);
        setKeyBoardListeners(botones2);
        setKeyBoardListeners(botones3);

        partidaActual = EstadoJuego.getInstance().getPartida();
        retoActual = (RetoAhorcado) EstadoJuego.getInstance().getPartida().getRetos().get(partidaActual.getRetoActual()-1);
        palabra = retoActual.getPalabra().toUpperCase();
        this.numeroPregunta = partidaActual.getRetoActual();

        loadRetosState();
        loadPalabra();

    }

    private void loadRetosState(){
        for(int i = 0; i < numeroPregunta; i++){
            ((Label) labelArray.getChildren().get(i))
                    .setStyle(partidaActual.getRetosFallados()[i] ? "-fx-background-color: rgb(255,25,25); " : "-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }

    private void setKeyBoardListeners(HBox botones){

        for(Node node : botones.getChildren()){
            Button button = (Button) node;
            button.setOnAction(eventHandler);
        }
    }

    EventHandler<ActionEvent> eventHandler = event -> {
        // Handle the button click
       Button pressedButton = (Button) event.getSource();
       char selectedChar = pressedButton.getText().charAt(0);

       if(palabra.contains(selectedChar+"")) {
           pressedButton.setTextFill(Color.GREEN);
           loadChar(selectedChar);
           checkWin();
       }
       else {
           pressedButton.setTextFill(Color.RED);
           retoActual.setIntentos(retoActual.getIntentos()-1);
           checkLose();
           imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() +".png").toAbsolutePath().toString()));
       }
       pressedButton.setDisable(true);

    };

    private void disableKeyboard(){
        for(Node node : botones1.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }

        for(Node node : botones2.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }

        for(Node node : botones3.getChildren()){
            Button button = (Button) node;
            button.setDisable(true);
        }
    }

    private void checkWin(){
        if(palabraMostrada.getText().equals(palabra)) {
            disableKeyboard();
            nextQuestionButton.setDisable(false);
            obtainedPoints = UserUtils.computePoints(retoActual, ayudaUsada, true);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            showMessage("HAS GANADO " + obtainedPoints + " PUNTOS", true);
            ayudaButton.setDisable(true);
        }


    }

    private void checkLose(){
        if(retoActual.getIntentos() == 0){
            disableKeyboard();
            botonSalir.setDisable(false);
            obtainedPoints = UserUtils.computePoints(retoActual, ayudaUsada, false);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = true;
            int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas()-1;
            showMessage("HAS PERDIDO " + obtainedPoints + " PUNTOS", false);
            if(vidasPartida == 0){
                showMessage("HAS PERDIDO LA PARTIDA Y " + obtainedPoints + " PUNTOS!", false);
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
            }
            EstadoJuego.getInstance().getPartida().setVidas(vidasPartida);
            ayudaButton.setDisable(true);
        }
    }

    private void showMessage(String message, boolean win){
        estatusRespuesta.setText(message);
        estatusRespuesta.setTextFill(win ? Color.GREEN : Color.RED);
        partidaScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
    }


    private void loadPalabra(){
        ((Label) labelArray.getChildren().get(numeroPregunta - 1)).setStyle("-fx-background-color: rgb(202,184,218)");

        for(int i = 0; i < palabra.length(); i++)
            palabraMostrada.setText(palabraMostrada.getText() + "_");

        partidaScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());


    }

    private void loadChar(char selectedChar){
        for(int i = 0; i < palabra.length(); i++) {
            if(palabra.charAt(i) == selectedChar) {
                StringBuilder sb = new StringBuilder(palabraMostrada.getText());
                sb.setCharAt(i, selectedChar);
                palabraMostrada.setText(sb.toString());
            }
        }
    }

    @FXML
    void ayudaButtonClicked(ActionEvent event) {
        boolean notFoundChar = true;
        while (notFoundChar) {
            char randomChar = palabra.charAt(new Random().nextInt(palabra.length()));
            System.out.println("Random char is: " + randomChar);
            if (!palabraMostrada.getText().contains("" + randomChar)) {

                for (Node node : botones1.getChildren()) {
                    Button button = (Button) node;
                    if (button.getText().equals("" + randomChar)) {
                        button.setDisable(true);
                        loadChar(randomChar);
                        checkWin();
                        notFoundChar = false;
                        break;

                    }

                }

                for (Node node : botones2.getChildren()) {
                    Button button = (Button) node;
                    if (button.getText().equals("" + randomChar)) {
                        button.setDisable(true);
                        loadChar(randomChar);
                        checkWin();
                        notFoundChar = false;
                        break;
                    }
                }

                for (Node node : botones3.getChildren()) {
                    Button button = (Button) node;
                    if (button.getText().equals("" + randomChar)) {
                        button.setDisable(true);
                        loadChar(randomChar);
                        checkWin();
                        notFoundChar = false;
                        break;
                    }
                }
            }

            ayudaButton.setDisable(true);
            ayudaUsada = true;
        }
    }

    @FXML
    void consolidarButtonClicked(ActionEvent event) {

        consolidarButton.setDisable(true);
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
    }

    @FXML
    void botonSalirPulsado(ActionEvent event) {

    }

    @FXML
    void siguientePreguntaClicked(ActionEvent event) {
        if(numeroPregunta == 10){
            UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacion());
        }

        EstadoJuego.getInstance().getPartida().setRetoActual(numeroPregunta+1);
        Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
        stage.close();
    }

}
