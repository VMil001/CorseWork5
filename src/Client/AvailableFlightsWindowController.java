package Client;

import Server.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AvailableFlightsWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SowButton;

    @FXML
    private TableColumn<Flight, String> transfer;


    @FXML
    private TableView<Flight> AvailableFlyTableView;

    @FXML
    private TableColumn<Flight, String> inAirport;

    @FXML
    private TableColumn<Flight, String> date;

    @FXML
    private TableColumn<Flight, String> outTime;

    @FXML
    private TableColumn<Flight, String> FlIDColumn;

    @FXML
    private TableColumn<Flight, String> inTime;

    @FXML
    private TableColumn<Flight, String> seatsAmount;

    @FXML
    private TableColumn<Flight, String> price;

    @FXML
    private TextField entertext1;

    @FXML
    private Button BuyTicketButton;

    @FXML
    private Button backButton;

    @FXML
    private RadioButton btnAll;

    @FXML
    private RadioButton btnFiltred;

    private ObservableList<Flight> flData = FXCollections.observableArrayList();

    private ObservableList<Flight> filterlData = FXCollections.observableArrayList();

    private History history = History.getInstance();

    @FXML
    void initialize() {
        entertext1.setEditable(true);
        String iduser = "";
        try {
            String id = (String)Client.is.readObject();
            String[] mp = id.split(",");
            iduser = mp[2];
            String id1 = mp[0] + "," + mp[1] + "," + ClientMainWindowController.allFly;
            String clm = "getAvFl," + id1;
            System.out.println("Действия на окне полетов" + clm);
            Client.os.writeObject(clm);
            String data = (String)Client.is.readObject();
            System.out.println("GetFlights " + data);
            //String[] count = id.split(",");
            String[] messParts = data.split(";");
            for (int i = 0; i < Integer.parseInt(mp[0]); i++)
            {
                String[] mesParts = messParts[i].split(",");
                if (!mesParts[5].equals("0")) {
                    flData.add(new Flight(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4], mesParts[5], mesParts[6], mesParts[7]));
                }

                if(mesParts[7].equals("-"))
                    filterlData.add(new Flight(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4], mesParts[5], mesParts[6], mesParts[7]));
            }
            FlIDColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("id"));
            inAirport.setCellValueFactory(new PropertyValueFactory<Flight, String>("Airport"));
            date.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
            outTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("outTime"));
            inTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("inTime"));
            seatsAmount.setCellValueFactory(new PropertyValueFactory<Flight, String>("seatsAmount"));
            price.setCellValueFactory(new PropertyValueFactory<Flight, String>("price"));
            transfer.setCellValueFactory(new PropertyValueFactory<Flight, String>("transfer"));
            if(ClientMainWindowController.allFly == 1)
                transfer.setText("Вылет");

            AvailableFlyTableView.setItems(flData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String finaliduser = iduser;

        btnAll.setOnAction(actionEvent -> {
            AvailableFlyTableView.setItems(flData);
        });

        btnFiltred.setOnAction(actionEvent -> {
            AvailableFlyTableView.setItems(filterlData);
        });

        backButton.setOnAction(event->{
            String clM = "sendData,"+finaliduser;
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/ClientMainWindow.fxml");
        });


        BuyTicketButton.setOnAction(event -> {
            String FlID = entertext1.getText().trim();
            String price = "0";
            for(int i=0; i < flData.size(); i++){
                if(flData.get(i).getId().equals(FlID))
                    price = flData.get(i).getPrice();
            }
            String idud = "";
            String clMess = "CountFreeSeatsAmount," + FlID;
            try {
                Client.os.writeObject(clMess);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String clm2 = "addIDFtoUD," + FlID + "," + finaliduser;
            try {
                Client.os.writeObject(clm2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String clm3 = "getIDUserD," + FlID;
            try {
                Client.os.writeObject(clm3);
                idud = (String)Client.is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String clm4 = "sendData," + idud + ";" + FlID + ";" + price + ";" + finaliduser;
            try {
                Client.os.writeObject(clm4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/TicketWindow.fxml");
        });

    }

    public void openNewScene(String window)
    {
        BuyTicketButton.getScene().getWindow().hide();

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

