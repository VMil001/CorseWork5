package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

public class TicketWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label inAirLab;

    @FXML
    private Label dateLab;

    @FXML
    private Label outTimeLab;

    @FXML
    private Label inTimeLab;

    @FXML
    private Label fullPriceLab;

    @FXML
    private Label ClName;

    @FXML
    private Label ClSurname;

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


    @FXML
    private Button backButton;

    private String user;

    @FXML
    void initialize() {
        try {
            String ids = (String)Client.is.readObject();
            String[] mparts = ids.split(";");
            String idf = "getTicketInfo," + mparts[1];
            user = mparts[3];
            System.out.println(mparts[0]);
            System.out.println(mparts);
            Client.os.writeObject(idf);
            String tickInfo = (String)Client.is.readObject();
            String[] mp = tickInfo.split(",");
            inAirLab.setText(mp[0]);
            dateLab.setText(mp[1]);
            outTimeLab.setText(mp[2]);
            inTimeLab.setText(mp[3]);
            String NS = "getClientName," + mparts[0];
            Client.os.writeObject(NS);
            String ns = (String)Client.is.readObject();
            String[] namesurname = ns.split(",");
            ClName.setText(namesurname[0]);
            ClSurname.setText(namesurname[1]);
            String IDs = "CalculateFullPrice," + mparts[0] + "," + mparts[2] + "," + ClientMainWindowController.allFly;
            Client.os.writeObject(IDs);
            String fullprice = (String)Client.is.readObject();
            fullPriceLab.setText(fullprice);
            String clm = "addInfoToTicket," + mparts[0] + "," + fullprice;
            Client.os.writeObject(clm);
            String addBilet = "addBilet," + mp[0] + "," + mp[1] + "," + mp[2] + "," + mp[3] + "," + namesurname[0] + "," + namesurname[1];
            System.out.println(mparts[0]);
            System.out.println(mparts);
            Client.os.writeObject(addBilet);
            saveButton.setOnAction(event -> {
                String save = "saveTicketToFile," + tickInfo + "," + fullprice;
                try {
                    Client.os.writeObject(save);
                    String del = "deleteUserData," + mparts[0];
                    Client.os.writeObject(del);
                    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Билет успешно сохранён");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        backButton.setOnAction(actionEvent -> {
            String clM = "sendData,"+user;
            try {
                Client.os.writeObject(clM);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/ClientMainWindow.fxml");
        });

        exitButton.setOnAction(actionEvent -> {
            openNewScene("/Client/LoginWindow.fxml");
        });


    }
}
