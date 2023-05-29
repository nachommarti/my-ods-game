package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Repository.*;
import com.myodsgame.Services.IServices;
import com.myodsgame.Services.Services;
import com.myodsgame.Utils.EstadoJuego;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

public class EstadisticaController implements Initializable {
    @FXML
    private Label labelAciertos;

    @FXML
    private Label labelPuntos;

    @FXML
    private Label labelTipoRanking;

    @FXML
    private TableView<Estadisticas> ranking = new TableView<>();

    @FXML
    private BarChart<String, Number> graficaOds;

    private List<Estadisticas> estadisticas;
    private List<Estadisticas> top10;

    private Repositorio<Estadisticas, String> repositorioEstadisticas;
    private RepositorioPuntosFechaImpl repositorioPuntosFecha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IServices servicios = new Services();
        repositorioEstadisticas = new RepositorioEstadisticasImpl();
        repositorioPuntosFecha = new RepositorioPuntosFechaImpl();
        Estadisticas est = EstadoJuego.getInstance().getUsuario().getEstadistica();
        int numeroAciertosTotales = est.getPalabrasAcertadas().size() + est.getPreguntasAcertadas().size() + est.getFrasesAcertadas().size();

        double porcentajeAciertos = (double) numeroAciertosTotales / servicios.getNumeroTotalRetos();
        labelPuntos.setText(String.valueOf(est.getPuntosTotales()));
        labelAciertos.setText(String.valueOf(String.format("%.2f",porcentajeAciertos*100) +" %"));
        labelTipoRanking.setText("Ranking Global");


        //Gráfica Estadística
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        int[] datosODS = new int[17];
        int[] retosTotalesPorODS = servicios.getNumeroTotalRetosPorODS();

        for (int i = 0; i < est.getAciertos_individual_ods().length; i++) {
            int aciertos = est.getAciertos_individual_ods()[i];
            int total = retosTotalesPorODS[i];

            if (aciertos == 0) datosODS[i] = 0;
            else{
                double res = ((double) aciertos / total) * 100.0;
                if(res > 100){
                    datosODS[i] = 100;
                }
                else {
                    datosODS[i] = (int) Math.round(res);
                }
            }
        }

        xAxis.setLabel("ODS");
        yAxis.setLabel("Porcentaje");
        graficaOds.setLegendVisible(false);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for(int i = 0; i < datosODS.length; i++){
            serie.getData().add(new XYChart.Data<>(Integer.toString(i + 1), datosODS[i]));
        }

        graficaOds.getData().add(serie);


        //RANKING
        TableColumn<Estadisticas, Integer> colPosicion = new TableColumn<>("Posición");
        TableColumn<Estadisticas, Integer> colPuntos = new TableColumn<>("Puntos");
        TableColumn<Estadisticas, String> colUsuarios = new TableColumn<>("Usuario");

        colPosicion.setCellValueFactory(data -> {
            Estadisticas stats = data.getValue();
            IntegerProperty integerProperty = new SimpleIntegerProperty(stats.getPosicion());
            return integerProperty.asObject();
        });

        colPuntos.setCellValueFactory(data -> {
            Estadisticas stats = data.getValue();
            IntegerProperty integerProperty = new SimpleIntegerProperty(stats.getPuntosTotales());
            return integerProperty.asObject();
        });

        colUsuarios.setCellValueFactory(data -> {
            Estadisticas stats = data.getValue();
            return new SimpleStringProperty(stats.getUsuario());
        });

        ranking.setRowFactory(tv -> {
            TableRow<Estadisticas> row = new TableRow<>();
            row.itemProperty().addListener((obs, prevStats, currStats) -> {
                if (currStats != null && currStats.getUsuario().equals(EstadoJuego.getInstance().getUsuario().getUsername())) {
                    row.setStyle("-fx-background-color: yellow;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        ranking.getColumns().addAll(colPosicion, colPuntos, colUsuarios);
        estadisticas = repositorioEstadisticas.findAll();
        top10 = estadisticas.subList(0, Math.min(10, estadisticas.size()));
        Estadisticas estadisticasUsuarioLogeado = EstadoJuego.getInstance().getUsuario().getEstadistica();

        Optional<Estadisticas> statsUserLoged = estadisticas.stream().filter(stat -> stat.getUsuario().equals(EstadoJuego.getInstance().getUsuario().getUsername())).findFirst();
        if(statsUserLoged.isPresent()){
            estadisticasUsuarioLogeado = statsUserLoged.get();
            String nombreUsuarioLogeado = estadisticasUsuarioLogeado.getUsuario();
            boolean contieneNombre = top10.stream().anyMatch(item -> item.getUsuario().equals(nombreUsuarioLogeado));
            if(!contieneNombre){
                top10.add(estadisticasUsuarioLogeado);
            }
        }

        ObservableList<Estadisticas> stats = FXCollections.observableList(top10);
        ranking.setItems(stats);

    }



    @FXML
    void botonAtrasPulsado(ActionEvent event) {
        ((Stage) ranking.getScene().getWindow()).close();
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


    @FXML
    void rankingGlobalPulsado(ActionEvent event){
        labelTipoRanking.setText("Ranking Global");
        estadisticas = repositorioEstadisticas.findAll();
        top10 = estadisticas.subList(0, Math.min(10, estadisticas.size()));
        Estadisticas estadisticasUsuarioLogeado = EstadoJuego.getInstance().getUsuario().getEstadistica();

        Optional<Estadisticas> statsUserLoged = estadisticas.stream().filter(stat -> stat.getUsuario().equals(EstadoJuego.getInstance().getUsuario().getUsername())).findFirst();
        if(statsUserLoged.isPresent()){
            estadisticasUsuarioLogeado = statsUserLoged.get();
            String nombreUsuarioLogeado = estadisticasUsuarioLogeado.getUsuario();
            boolean contieneNombre = top10.stream().anyMatch(item -> item.getUsuario().equals(nombreUsuarioLogeado));
            if(!contieneNombre){
                top10.add(estadisticasUsuarioLogeado);
            }
        }

        ObservableList<Estadisticas> stats = FXCollections.observableList(top10);
        ranking.setItems(stats);
    }

    @FXML
    void rankingDiarioPulsado(ActionEvent event){
        labelTipoRanking.setText("Ranking Diario");
        List<Estadisticas> estadisticas = repositorioPuntosFecha.getPuntosDiarios();
        top10 = estadisticas.subList(0, Math.min(10, estadisticas.size()));
        Estadisticas estadisticasUsuarioLogeado = EstadoJuego.getInstance().getUsuario().getEstadistica();

        Optional<Estadisticas> statsUserLoged = estadisticas.stream().filter(stat -> stat.getUsuario().equals(EstadoJuego.getInstance().getUsuario().getUsername())).findFirst();
        if(statsUserLoged.isPresent()){
            estadisticasUsuarioLogeado = statsUserLoged.get();
            String nombreUsuarioLogeado = estadisticasUsuarioLogeado.getUsuario();
            boolean contieneNombre = top10.stream().anyMatch(item -> item.getUsuario().equals(nombreUsuarioLogeado));
            if(!contieneNombre){
                top10.add(estadisticasUsuarioLogeado);
            }
        }


        ObservableList<Estadisticas> stats = FXCollections.observableList(top10);
        ranking.setItems(stats);
    }

    @FXML
    void rankingSemanalPulsado(ActionEvent event){
        labelTipoRanking.setText("Ranking Semanal");
        estadisticas = repositorioPuntosFecha.getPuntosSemanales();
        top10 = estadisticas.subList(0, Math.min(10, estadisticas.size()));
        Estadisticas estadisticasUsuarioLogeado = EstadoJuego.getInstance().getUsuario().getEstadistica();

        Optional<Estadisticas> statsUserLoged = estadisticas.stream().filter(stat -> stat.getUsuario().equals(EstadoJuego.getInstance().getUsuario().getUsername())).findFirst();
        if(statsUserLoged.isPresent()){
            estadisticasUsuarioLogeado = statsUserLoged.get();
            String nombreUsuarioLogeado = estadisticasUsuarioLogeado.getUsuario();
            boolean contieneNombre = top10.stream().anyMatch(item -> item.getUsuario().equals(nombreUsuarioLogeado));
            if(!contieneNombre){
                top10.add(estadisticasUsuarioLogeado);
            }
        }

        ObservableList<Estadisticas> stats = FXCollections.observableList(top10);
        ranking.setItems(stats);

    }
}
