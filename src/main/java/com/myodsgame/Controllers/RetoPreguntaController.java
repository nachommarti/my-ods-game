package com.myodsgame.Controllers;

import com.myodsgame.Mediator.Mediador;
import com.myodsgame.Mediator.MediadorPregunta;
import com.myodsgame.Models.Partida;
import com.myodsgame.Models.RetoPregunta;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import com.myodsgame.Utils.UserUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.*;

public class RetoPreguntaController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label enunciadoPregunta;

    @FXML
    private Button ayuda;

    @FXML
    private Button respuesta1;

    @FXML
    private Button respuesta2;

    @FXML
    private Button respuesta3;

    @FXML
    private Button respuesta4;

    @FXML
    private Label currentScore;

    @FXML
    private Label timer;
    @FXML
    private Button abandonarBoton;
    @FXML
    private ImageView vidas;
    @FXML
    private Label puntosPorAcertar;

    @FXML
    private Label estatusRespuesta;
    @FXML
    private ImageView imagenODS;
    @FXML
    private HBox labelArray;
    private List<Button> respuestas;
    private String respuestaCorrecta;
    private boolean ayudaPulsada;
    private boolean respuestaCorrectaSeleccionada;
    public int obtainedPoints = 0;
    private boolean consolidated;
    private String currentStyleButton;
    private String currentStyleLabel;
    private String initialStyle;
    private Timeline timeline;
    private int timeCountdown;
    private boolean perdido = false;
    private int contAbandonar = 1;
    private MediaPlayer mediaPlayerSonidos = null, mediaPlayerMusic = null, mediaPlayerTicTac = new MediaPlayer(new Media(new File("src/main/resources/sounds/10S_tick.mp3").toURI().toString()));

    private Partida partidaActual;

    private RetoPregunta retoActual;
    private boolean fallado;

    private IServices servicios;
    private Mediador mediador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicios = new Services();
        mediador = new MediadorPregunta();
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.partidaActual = EstadoJuego.getInstance().getPartida();
        BackgroundImage fill = new BackgroundImage(EstadoJuego.getInstance().getPartida().getImagenFondo(), null,
                null,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        Background background = new Background(fill);
        borderPane.setBackground(background);


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

        System.out.println("reto que hay que coger que se va a usar: " + EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());
        this.retoActual = (RetoPregunta) partidaActual.getRetos().get(EstadoJuego.getInstance().getPartida().getRetoQueHayQueMirarEnElArray());
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeCountdown--;
                            // update timerLabel
                            timer.setText(Integer.toString(timeCountdown));
                            if (timeCountdown == retoActual.getTiempoTicTac()){
                                mediaPlayerTicTac.play();
                                mediaPlayerMusic.setVolume(0.05);
                            }
                            if (timeCountdown <= 0) {
                                timeline.stop();
                                mediaPlayerTicTac.stop();
                                endTimer();
                                mediaPlayerMusic.stop();
                                reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
                            }
                        })
        );
        reproducirMusica();
        this.initialStyle = "-fx-background-color:  rgba(255, 255, 255, 1); -fx-background-radius: 10; -fx-border-color: black; -fx-border-radius: 10";
        ayuda.setGraphic(new ImageView(new Image(Path.of("", "src", "main", "resources", "images", "ayuda.png").toAbsolutePath().toString())));

        loadRetosState();
        loadQuestion(retoActual);

        currentScore.setText("Score:" + EstadoJuego.getInstance().getPartida().getPuntuacion());

        if(EstadoJuego.getInstance().getPartida().getPuntuacion() >= retoActual.getPuntuacion()/2)
            ayuda.setDisable(false);
        else
            ayuda.setDisable(true);

        abandonarBoton.setVisible(partidaActual.isConsolidado());

        if (EstadoJuego.getInstance().getPartida().getAyudasRestantes() <= 0)
        {
            ayuda.setDisable(true);
        }

        for(int i = 1; i <= partidaActual.getRetos().size(); i++){
            System.out.println("Desde PREGUNTA - Reto numero " + i + " es de tipo " + partidaActual.getRetos().get(i-1).getTipoReto());
        }
        System.out.println("Desde reto PREGUNTA - reto actual: " + partidaActual.getRetoActual());
        System.out.println("Desde reto PREGUNTA - tipo reto actual: " + partidaActual.getRetos().get(partidaActual.getRetoActual()-1).getTipoReto());



        Rectangle clip = new Rectangle(imagenODS.getFitWidth(), imagenODS.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imagenODS.setClip(clip);
        puntosPorAcertar.setText("Puntos por acertar: " + retoActual.getDificultad()*100);
        vidas.setImage(partidaActual.getImagenVidas());
    }

    private void loadRetosState(){
        for(int i = 0; i < partidaActual.getRetoActual(); i++){
            ((Label) labelArray.getChildren().get(i)).setStyle("-fx-background-color: rgba(184, 218, 186, 1)");
        }
    }


    private void loadQuestion(RetoPregunta retoPreguntaActual) {
        enunciadoPregunta.setText(retoPreguntaActual.getEnunciado());
        respuesta1.setText(retoPreguntaActual.getRespuesta1());
        respuesta2.setText(retoPreguntaActual.getRespuesta2());
        respuesta3.setText(retoPreguntaActual.getRespuesta3());
        respuesta4.setText(retoPreguntaActual.getRespuesta4());
        this.respuestaCorrecta = retoPreguntaActual.getRespuestaCorrecta();
        this.respuestas = List.of(respuesta1, respuesta2, respuesta3, respuesta4);
        timeCountdown = retoPreguntaActual.getDuracion();
        if (retoPreguntaActual.getODS().size() == 1) imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_"+retoPreguntaActual.getODS().get(0)+".jpg").toAbsolutePath().toString()));
        else imagenODS.setImage(new Image(Path.of("", "src", "main", "resources", "images", "ODS_0.jpg").toAbsolutePath().toString()));
        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual() - 1)).setStyle("-fx-background-color: rgb(202,184,218)");
        timeline.playFromStart();

        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
        }
        else if (EstadoJuego.getInstance().getPartida().getVidas() == 0) {
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
        }
    }

    @FXML

    void ayudaClicked(ActionEvent event) {
        Optional<ButtonType> result = mediador.ayudaClicked(retoActual, currentScore, ayuda);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            int removedElements = 0;
            Set<Button> seenElement = new HashSet<>();
            while (removedElements < 2) {
                Button respuesta = respuestas.get(new Random().nextInt(respuestas.size()));
                if (!respuesta.getText().equals(respuestaCorrecta) && !seenElement.contains(respuesta)) {
                    respuesta.setDisable(true);
                    seenElement.add(respuesta);
                    removedElements++;
                }
            }
            ayudaPulsada = true;
            reproducirSonido("src/main/resources/sounds/pista_larga.mp3", 0.5);
            mediaPlayerMusic.play();
        }
    }
    @FXML
    void abandonarBotonPulsado(ActionEvent event) {
        mediador.botonAbandonarPulsado(mediaPlayerTicTac, mediaPlayerMusic, abandonarBoton, timeline);
    }

    @FXML
    void respuesta1Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta2Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta3Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);
    }

    @FXML
    void respuesta4Clicked(ActionEvent event) {
        Button respuestaSeleccionada = (Button) event.getSource();
        checkAnswers(respuestaSeleccionada);

    }

    private void checkAnswers(Button respuestaSeleccionada) {
        timeline.stop();
        if(mediaPlayerMusic !=null)mediaPlayerMusic.stop();
        if(mediaPlayerTicTac !=null) mediaPlayerTicTac.stop();
        currentStyleLabel = labelArray.getStyle();

        if (respuestaSeleccionada.getText().equals(respuestaCorrecta)) {
            ((Label) labelArray.getChildren().get(partidaActual.getRetoActual()-1)).setStyle("-fx-background-color: rgba(184, 218, 186, 1); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(partidaActual.getRetoActual()-1)).setTextFill(Color.WHITE);
            respuestaCorrectaSeleccionada = true;
            reproducirSonido("src/main/resources/sounds/Acierto.mp3", 0.15);
            UserUtils.saveStats(true, retoActual.getODS());
            EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual()-1] = false;
        }
        else {
            ((Label) labelArray.getChildren().get(partidaActual.getRetoActual()-1)).setStyle("-fx-background-color: rgb(255,25,25); " + currentStyleLabel);
            ((Label) labelArray.getChildren().get(partidaActual.getRetoActual()-1)).setTextFill(Color.WHITE);
            EstadoJuego.getInstance().getPartida().setVidas(partidaActual.getVidas()-1);
            reproducirSonido("src/main/resources/sounds/Fallo.mp3", 0.5);
            UserUtils.saveStats(false, retoActual.getODS());
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
            EstadoJuego.getInstance().getPartida().getRetosFallados()[partidaActual.getRetoActual()-1] = true;
            if (EstadoJuego.getInstance().getPartida().getVidas() == 0) {
                if(mediaPlayerSonidos!=null) mediaPlayerSonidos.stop();
                lostGame();
                EstadoJuego.getInstance().getPartida().setPartidaPerdida(true);
                reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);
                EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
                vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
            }

        }

        for (Button respuesta : respuestas) {
            currentStyleButton = respuesta.getStyle();
            int index = currentStyleButton.indexOf(";");
            if (respuesta.getText().equals(respuestaCorrecta)) {
                respuesta.setStyle("-fx-background-color: rgba(184, 218, 186, 1)" + currentStyleButton.substring(index));
                respuesta.setDisable(true);
            } else if (!respuestaCorrectaSeleccionada) {
                respuestaSeleccionada.setStyle("-fx-background-color: rgba(204, 96, 56, 1)" + currentStyleButton.substring(index));
                respuestaSeleccionada.setDisable(true);
            }
            respuesta.setDisable(true);

        }

        ayuda.setDisable(true);
        computePoints();
        showPopUp();
        EstadoJuego.getInstance().getPartida().setPuntuacion(EstadoJuego.getInstance().getPartida().getPuntuacion() + obtainedPoints);
        currentScore.setText("Score: " + EstadoJuego.getInstance().getPartida().getPuntuacion());

    }

    private void computePoints() {
        obtainedPoints = servicios.computePoints(retoActual, ayudaPulsada, respuestaCorrectaSeleccionada);
        if (!respuestaCorrectaSeleccionada && EstadoJuego.getInstance().getPartida().isConsolidado())
        {
            EstadoJuego.getInstance().getPartida().setPuntuacionConsolidada(EstadoJuego.getInstance().getPartida().getPuntuacionConsolidada()
                    + obtainedPoints);
        }
    }

    private void endTimer() {
        respuestas.forEach(respuesta -> respuesta.setDisable(true));


        EstadoJuego.getInstance().getPartida().setVidas(partidaActual.getVidas()-1);
        if (EstadoJuego.getInstance().getPartida().getVidas() == 1) {
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidaMitad.png").toAbsolutePath().toString()));
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
        }
        if (EstadoJuego.getInstance().getPartida().getVidas() == 2) {
            if(mediaPlayerSonidos!=null) mediaPlayerSonidos.stop();
            EstadoJuego.getInstance().getPartida().setImagenVidas(new Image(Path.of("", "src", "main", "resources", "images", "vidasAgotadas.png").toAbsolutePath().toString()));
            vidas.setImage(EstadoJuego.getInstance().getPartida().getImagenVidas());
            lostGame();
            reproducirSonido("src/main/resources/sounds/Partida_Perdida.mp3", 0.5);

        }

        ((Label) labelArray.getChildren().get(partidaActual.getRetoActual()-1)).setTextFill(Color.RED);
        ayuda.setDisable(true);
        this.timeCountdown = 30;
    }



    private void lostGame() {
        perdido = true;
        estatusRespuesta.setText("¡INCORRECTO! " + "Has perdido");
        estatusRespuesta.setTextFill(Color.RED);
        respuesta1.setDisable(true);
        respuesta2.setDisable(true);
        respuesta3.setDisable(true);
        respuesta4.setDisable(true);
        ayuda.setDisable(true);
        UserUtils.aumentarPartidasJugadas();
    }

    private void reproducirSonido(String sonidoPath, double volumen){
        mediaPlayerMusic.pause();
        mediaPlayerSonidos = mediador.sonidoSetter(sonidoPath, volumen);
        mediaPlayerSonidos.play();
    }

    private void reproducirMusica(){
        mediaPlayerMusic = mediador.musicaSetter();
        mediaPlayerMusic.play();
    }

    private void showPopUp() {
        Stage stage = mediador.showPopUp(partidaActual);
        stage.show();
    }
}
