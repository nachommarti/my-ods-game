package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Repository.RepositorioEstadisticasImpl;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class EstadisticaController implements Initializable {
    @FXML
    private Label tusPuntos;

    @FXML
    private Label labelPuntos;

    @FXML
    private TableView<Estadisticas> ranking = new TableView<>();

    @FXML
    private BarChart<String, Number> graficaOds;

    private List<Estadisticas> estadisticas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Estadisticas est = EstadoJuego.getInstance().getUsuario().getEstadistica();
        labelPuntos.setText(String.valueOf(est.getPuntosTotales()));

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        int[] datosODS = new int[17];
        for (int i = 0; i < est.getAciertos_individual_ods().length; i++) {
            int aciertos = est.getAciertos_individual_ods()[i];
            int fallos = est.getFallos_individual_ods()[i];

            if (aciertos == 0) datosODS[i] = 0;
            else if (fallos == 0) datosODS[i]= 100;
            else{
                double res = ((double) aciertos / (aciertos+fallos) ) * 100.0;
                datosODS[i] = (int) Math.round(res);
            }
        }

        xAxis.setLabel("ODS");
        yAxis.setLabel("Porcentaje");
        graficaOds.setLegendVisible(false);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for(int i = 0; i < datosODS.length; i++){
            int valorODS = datosODS[i];
            serie.getData().add(new XYChart.Data<>(Integer.toString(i + 1), valorODS));
        }

        graficaOds.getData().add(serie);

        TableColumn<Estadisticas, Integer> colPosicion = new TableColumn<>("Posición");
        TableColumn<Estadisticas, Integer> colPuntos = new TableColumn<>("Puntos");
        TableColumn<Estadisticas, String> colUsuarios = new TableColumn<>("Usuario");

        colPosicion.setCellValueFactory(data -> {
            int position = ranking.getItems().indexOf(data.getValue()) + 1;
            return new SimpleIntegerProperty(position).asObject();
        });

        colPuntos.setCellValueFactory(data -> {
            Estadisticas stat = data.getValue();
            IntegerProperty integerProperty = new SimpleIntegerProperty(stat.getPuntosTotales());
            return integerProperty.asObject();
        });

        colUsuarios.setCellValueFactory(data -> {
            Estadisticas stats = data.getValue();
            return new SimpleStringProperty(stats.getUsuario());
        });

        ranking.getColumns().addAll(colPosicion, colPuntos, colUsuarios);

        estadisticas = new RepositorioEstadisticasImpl().getEstadisticas();
        ObservableList<Estadisticas> stats = FXCollections.observableList(estadisticas);
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


}
