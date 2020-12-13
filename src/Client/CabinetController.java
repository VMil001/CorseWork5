package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Server.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CabinetController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label UsernameTF;

    @FXML
    private Button exitButton;

    @FXML
    private Button dellFly;

    @FXML
    private TableView<Cabinet> tableBilet;

    @FXML
    private TableColumn<Cabinet, String> columnAirport;

    @FXML
    private TableColumn<Cabinet, String> columnDate;

    @FXML
    private TableColumn<Cabinet, String> columnTimeOut;

    @FXML
    private TableColumn<Cabinet, String> columnTimeIn;

    private ObservableList<Cabinet> flData = FXCollections.observableArrayList();

    String user;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        String id = (String)Client.is.readObject();
        columnAirport.setCellValueFactory(new PropertyValueFactory<Cabinet, String>("Airport"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Cabinet, String>("Date"));
        columnTimeOut.setCellValueFactory(new PropertyValueFactory<Cabinet, String>("timeOut"));
        columnTimeIn.setCellValueFactory(new PropertyValueFactory<Cabinet, String>("timeOut"));

        user = id;
        String clOutput = "getBilet," + id;

        Client.os.writeObject(clOutput);

        String result = (String)Client.is.readObject();

        String []arr = result.split(";");

        for(int i=0; i < arr.length; i++)
        {
            String []temp = arr[i].split(",");
            if(temp.length > 0  && temp[0].matches("^[0-9]+$"))
                flData.add(new Cabinet(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4]));
        }

        tableBilet.setItems(flData);

        //flData.add()


        dellFly.setOnAction(actionEvent -> {
            if(tableBilet.getSelectionModel().getSelectedItem() == null)
                return;

            String out = "delBilet," + tableBilet.getSelectionModel().getSelectedItem().getId();

            try {
                Client.os.writeObject(out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableBilet.getItems().remove(tableBilet.getSelectionModel().getSelectedIndex());
        });

        exitButton.setOnAction(actionEvent -> {
            String clM = "sendData,"+user;
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/ClientMainWindow.fxml");
        });

    }

    public void openNewScene(String window)
    {
        exitButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
