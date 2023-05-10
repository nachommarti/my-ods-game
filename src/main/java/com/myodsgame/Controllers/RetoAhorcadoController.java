package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoAhorcado;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    private Timeline timeline;
    private int timeCountdown;
    private IServices servicios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = new Services();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        partidaActual = EstadoJuego.getInstance().getPartida();
        retoActual = (RetoAhorcado) EstadoJuego.getInstance().getPartida().getRetos().get(partidaActual.getRetoActual()-1);
        timeCountdown = retoActual.getDuracion();
        palabra = retoActual.getPalabra().toUpperCase();
        this.numeroPregunta = partidaActual.getRetoActual();
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));

                            if (timeCountdown <= 0) {
                                timeline.stop();
                                palabraMostrada.setText(palabra);
                                endTimer();

                            }
                        })
        );


        ayudaButton.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));
        setKeyBoardListeners(botones1);
        setKeyBoardListeners(botones2);
        setKeyBoardListeners(botones3);

        if (retoActual.getODS().size() == 1) imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_"+retoActual.getODS().get(0)+".jpg").toAbsolutePath().toString()));
        else imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_0.jpg").toAbsolutePath().toString()));

        frasePista.setText(retoActual.getPista());

        loadRetosState();
        loadPalabra();
        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        else if (EstadoJuego.getInstance().getPartida().getVidas() == 0)
        {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        }
        consolidarButton.setDisable(true);
        nextQuestionButton.setDisable(true);

        if(EstadoJuego.getInstance().getPartida().getPuntuacion() >= retoActual.getPuntuacion()/2)
            ayudaButton.setDisable(false);
        else
            ayudaButton.setDisable(true);

        timeline.playFromStart();

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
       Background background = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
       if(palabra.contains(selectedChar+"")) {
           background = new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
           pressedButton.setBackground(background);
           loadChar(selectedChar);
           checkWin();
       }
       else {
           pressedButton.setBackground(background);
           retoActual.setIntentos(retoActual.getIntentos()-1);
           checkLose();
           imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() +".png").toAbsolutePath().toString()));
       }
       pressedButton.setDisable(true);

    };

    private void endTimer(){
        disableKeyboard();
        retoActual.setIntentos(0);
        imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() +".png").toAbsolutePath().toString()));
        checkLose();

    }

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
            obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, true);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            UserUtils.saveStats(true, retoActual.getODS());
            try
            {
                FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/popUpRetoPregunta.fxml"));
                BorderPane root = myLoader.load();
                Scene scene = new Scene (root, 357, 184);
                Stage  stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("¡Enhorabuena!");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setResizable(false);
                stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
                stage.setOnCloseRequest(e -> {
                    System.exit(0);
                });
                stage.show();
            }
            catch (IOException e){}
            nextQuestionButton.setDisable(false);
            if(!EstadoJuego.getInstance().getPartida().isConsolidado()) {
                EstadoJuego.getInstance().getPartida().setConsolidado(true);
                consolidarButton.setDisable(false);
            }
            showMessage("HAS GANADO " + obtainedPoints + " PUNTOS", true);
            ayudaButton.setDisable(true);
        }


    }

    private void checkLose(){
        if(retoActual.getIntentos() == 0){
            disableKeyboard();
            botonSalir.setDisable(false);
            UserUtils.saveStats(false, retoActual.getODS());
            obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, false);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            EstadoJuego.getInstance().getPartida().getRetosFallados()[numeroPregunta-1] = true;
            int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas()-1;
            showMessage("HAS PERDIDO " + obtainedPoints + " PUNTOS", false);
            if(vidasPartida == 0){
                showMessage("HAS PERDIDO LA PARTIDA Y " + obtainedPoints + " PUNTOS!", false);
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
                UserUtils.aumentarPartidasJugadas();
            }
            EstadoJuego.getInstance().getPartida().setVidas(vidasPartida);

            if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
                vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            }
            else if (EstadoJuego.getInstance().getPartida().getVidas() == 0)
            {
                vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            }
            ayudaButton.setDisable(true);
            nextQuestionButton.setDisable(false);
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
            int puntos =  EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion()/2;
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
            partidaScore.setText("Score: " + puntos);
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
