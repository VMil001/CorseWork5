package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class PieChartController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart dayTimeStat;

    @FXML
    void initialize() {
        String clm = "getDataForPie,";
        int cm = 0;
        int cd = 0;
        int ce = 0;
        int cn = 0;
        try {
            Client.os.writeObject(clm);
            String data = (String) Client.is.readObject();
            String[] count = data.split(",");
            cm = Integer.parseInt(count[0]);
            cd = Integer.parseInt(count[1]);
            ce = Integer.parseInt(count[2]);
            cn = Integer.parseInt(count[3]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Весна", cm),
                new PieChart.Data("Лето", cd),
                new PieChart.Data("Осень", ce),
                new PieChart.Data("Зима", cn)
        );
        dayTimeStat.setData(pieChartData);

    }
}
