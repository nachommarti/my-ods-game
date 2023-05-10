package com.myodsgame.Controllers;

import com.myodsgame.Models.Estadisticas;
import com.myodsgame.Repository.RepositorioEstadisticas;
import com.myodsgame.Repository.RepositorioEstadisticasImpl;
import com.myodsgame.Utils.EstadoJuego;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstadisticaController implements Initializable {
    @FXML
    private Label tusPuntos;

    @FXML
    private Label labelPuntos;

    @FXML
    private Button botonAtras;

    private List<Estadisticas> estadisticas;

    @FXML
    private TableView<Estadisticas> ranking;

    @FXML
    private TableColumn<Estadisticas, Integer> colPuntos = new TableColumn<Estadisticas, Integer>("Puntos");


    @FXML
    private TableColumn<Estadisticas, String> colUsuarios;

    @FXML
    private TableColumn<Estadisticas, Integer> colAciertos;

    @FXML
    private BarChart<String, Number> graficaOds;

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
        //String[] colores = {"#e30614", "#d5a200", "#309637", "#b71828", "#e8401b", "#169eda", "#fbb900", "#931533", "#ee7317", "#dd0076", "#f69c0b", "#c6972c", "#467735", "#0678b8", "#3fa535", "#004e88", "#1e3464"};

        xAxis.setLabel("ODS");
        yAxis.setLabel("Porcentaje");
        graficaOds.setLegendVisible(false);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        for(int i = 0; i < datosODS.length; i++){
            int valorODS = datosODS[i];
            serie.getData().add(new XYChart.Data<>(Integer.toString(i + 1), valorODS));
        }

        /*for(int i = 0; i < colores.length; i++){
            serie.getData().get(i).getNode().setStyle("-fx-bar-fill: " + colores[i] + ";");
        }*/
        
        graficaOds.getData().add(serie);


        estadisticas = new RepositorioEstadisticasImpl().getEstadisticas();
        ObservableList<Estadisticas> stats = FXCollections.observableList(estadisticas);
        ranking.setItems(stats);
    }
    @FXML
    void botonAtrasPulsado(ActionEvent event) throws IOException {
        ((Stage) ranking.getScene().getWindow()).close();
    }


}
