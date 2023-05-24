package com.myodsgame.Controllers;

import com.myodsgame.Models.Partida;
import com.myodsgame.Models.Reto;
import com.myodsgame.Models.RetoAhorcado;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
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
    //@FXML
    //private Button nextQuestionButton;
    //@FXML
    //private Button consolidarButton;
    @FXML
    private Button botonAbandonar;

    private Partida partidaActual;
    private RetoAhorcado retoActual;
    private String palabra;
    private int obtainedPoints;
    private boolean ayudaUsada;
    private Timeline timeline;
    private int timeCountdown;
    private IServices servicios;

    private MediaPlayer mediaPlayerMusic = null, mediaPlayerTicTac = null, mediaPlayerSonidos = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = new Services();
        timeline = new Timeline();
        reproducirMusica();
        timeline.setCycleCount(Timeline.INDEFINITE);
        partidaActual = EstadoJuego.getInstance().getPartida();

        if(partidaActual.getRetoActual() > 4 && partidaActual.getRetoActual() <= 7){
            while(partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 2) {
                System.out.println("wenos dias joselu");
                System.out.println("reto del array actual: " + partidaActual.getRetoQueHayQueMirarEnElArray());
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray() + 1);
                System.out.println("reto actualizado: " + EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());
            }
        }

        if(partidaActual.getRetoActual() > 7 && partidaActual.getRetoActual() <= 10){
            while(partidaActual.getRetos().get(partidaActual.getRetoQueHayQueMirarEnElArray()).getDificultad() != 3)
                EstadoJuego.getInstance().getPartida().setRetoQueHayQueMirarEnElArray(partidaActual.getRetoQueHayQueMirarEnElArray()+1);
        }

        for(int i = 1; i <= partidaActual.getRetos().size(); i++){
            System.out.println("Desde AHORCADO - Reto numero " + i + " es de tipo " + partidaActual.getRetos().get(i-1).getTipoReto());
        }

        System.out.println("Desde reto AHORCADO - reto actual: " + partidaActual.getRetoActual());
        System.out.println("Desde reto AHORCADO - tipo reto actual: " + partidaActual.getRetos().get(partidaActual.getRetoActual()-1).getTipoReto());


        retoActual = (RetoAhorcado) EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());

        timeCountdown = retoActual.getDuracion();
        palabra = retoActual.getPalabra().toUpperCase();

        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));

                            if (timeCountdown <= 0) {
                                reproducirSonido(false);
                                timeline.stop();
                                palabraMostrada.setText(palabra);
                                endTimer();

                            }

                            if (timeCountdown == 10){
                                Media TicTac = new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString());
                                mediaPlayerTicTac = new MediaPlayer(TicTac);
                                mediaPlayerTicTac.play();
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
        imagenAhorcado.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ahorcado" + retoActual.getIntentos() + ".png").toAbsolutePath().toString()));

        loadRetosState();
        loadPalabra();
        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
        }
        else if (EstadoJuego.getInstance().getPartida().getVidas() == 0)
        {
            vidas.setImage(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
        }
        //consolidarButton.setDisable(true);
        //nextQuestionButton.setDisable(true);

        if(EstadoJuego.getInstance().getPartida().getPuntuacion() >= retoActual.getPuntuacion()/2)
            ayudaButton.setDisable(false);
        else
            ayudaButton.setDisable(true);

        timeline.playFromStart();

        botonAbandonar.setVisible(partidaActual.isConsolidado());

        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);
    }

    private void loadRetosState(){
        for(int i = 0; i < partidaActual.getRetoActual(); i++){
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
        for(Node node : botones1.getChildren()) {
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

    private void checkWin() {
        if (palabraMostrada.getText().equals(palabra)) {
            if(mediaPlayerMusic != null ){mediaPlayerMusic.stop();}
            if(mediaPlayerTicTac != null){mediaPlayerTicTac.stop();}
            reproducirSonido(true);
            disableKeyboard();
            EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual()-1] = false;
            obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, true);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);
            UserUtils.saveStats(true, retoActual.getODS());
            showMessage("HAS GANADO " + obtainedPoints + " PUNTOS", true);
            ayudaButton.setDisable(true);
            timeline.stop();
            showPopUp();
        }
    }

    private void checkLose(){
        if(retoActual.getIntentos() == 0){
            if(mediaPlayerMusic != null ){mediaPlayerMusic.stop();}
            if(mediaPlayerTicTac != null){mediaPlayerTicTac.stop();}
            timeline.stop();
            reproducirSonido(false);
            disableKeyboard();
            //botonSalir.setDisable(false);
            UserUtils.saveStats(false, retoActual.getODS());
            obtainedPoints = servicios.computePoints(retoActual, ayudaUsada, false);
            int puntosPartida = EstadoJuego.getInstance().getPartida().getPuntuacion();

            EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual()-1] = true;
            int vidasPartida = EstadoJuego.getInstance().getPartida().getVidas()-1;
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
            if (EstadoJuego.getInstance().getPartida().isConsolidado())
            {
                EstadoJuego.getInstance().getPartida().setPuntuacionConsolidada(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada()
                        + obtainedPoints);
            }

            showPopUp();
            EstadoJuego.getInstance().getPartida().setPuntuacion(puntosPartida + obtainedPoints);

            //nextQuestionButton.setDisable(false);
        }
    }

    private void showMessage(String message, boolean win){
        estatusRespuesta.setText(message);
        estatusRespuesta.setTextFill(win ? Color.GREEN : Color.RED);
        partidaScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());
    }


    private void loadPalabra(){
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgb(202,184,218)");

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Usar pista");
        alert.setHeaderText("Canjear pista por " + EstadoJuego.getInstance().getPartida().getRetos().get(EstadoJuego.getInstance().getPartida().getRetoActual()).getPuntuacion() / 2 + " puntos");
        alert.setContentText("¿Deseas gastarte esos puntos en canjear esta pista?");
        ButtonType buttonType = new ButtonType("Cancelar");
        alert.getButtonTypes().add(buttonType);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
                int puntos = EstadoJuego.getInstance().getPartida().getPuntuacion() - retoActual.getPuntuacion() / 2;
                EstadoJuego.getInstance().getPartida().setPuntuacion(puntos);
                partidaScore.setText("Score: " + puntos);
                ayudaButton.setDisable(true);
                ayudaUsada = true;
            }
        }
    }

    @FXML
    void botonAbandonarPulsado(ActionEvent event) {
        UserUtils.saveUserScore(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada());
        Stage stageOld = (Stage) botonAbandonar.getScene().getWindow();
        stageOld.close();
        EstadoJuego.getInstance().getPartida().setPartidaAbandonada(true);

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/pantallaPartidas.fxml"));
        BorderPane root = null;
        try {
            root = myLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Menú Principal");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
        stage.setResizable(false);
        stage.show();
    }

    private void showPopUp() {
        try
        {

            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/com/myodsgame/popUpReto.fxml"));
            BorderPane root = myLoader.load();
            Scene scene = new Scene (root, 357, 184);
            Stage  stage = new Stage();
            stage.setScene(scene);
            if (partidaActual.getRetoActual() == 10)
            {
                stage.setTitle("¡Ganaste!");
            }
            else if (!partidaActual.getRetosFallados()[partidaActual.getRetoActual()]) {
                stage.setTitle("¡Enhorabuena!");
            } else {
                stage.setTitle("Oops!");
            }
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Path.of("", "src", "main", "resources", "images", "LogoODS.png").toAbsolutePath().toString()));
            stage.setOnCloseRequest(e -> {
                System.exit(0);
            });
            stage.show();
        }
        catch (IOException e) {System.out.println(e.getMessage());}
    }



    private void reproducirMusica(){
        mediaPlayerMusic = new MediaPlayer(new Media(new File("src/main/resources/sounds/cancion_3.mp3").toURI().toString()));
        mediaPlayerMusic.setVolume(0.15);
        mediaPlayerMusic.play();
    }

    private void reproducirSonido(boolean acertado){
        String path;
        if(acertado) path = "src/main/resources/sounds/Acierto.mp3";
        else path = "src/main/resources/sounds/Fallo.mp3";

        mediaPlayerSonidos = new MediaPlayer(new Media(new File(path).toURI().toString()));
        mediaPlayerSonidos.setVolume(0.2);
        mediaPlayerSonidos.play();
    }
}
