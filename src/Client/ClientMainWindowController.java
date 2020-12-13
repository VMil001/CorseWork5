package Client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ResourceBundle;

public class ClientMainWindowController {

    @FXML
    private TextField fromField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker FlyOutDateTF;

    @FXML
    private ComboBox<String> PassengerAgeComboBox;

    @FXML
    private TextField FlyToAirportField;

    @FXML
    private Button LetsFlyButton;

    @FXML
    private ComboBox<String> LaggageComboBox;

    @FXML
    private Label UsernameTF;

    @FXML
    private Text informationText;

    @FXML
    private Button exitButton;

    @FXML
    private Button AllFlyButton;

    @FXML
    private Button cabinetButton;

    private History history = History.getInstance();

    public static int allFly;


    public void openNewScene(String window)
    {
        LetsFlyButton.getScene().getWindow().hide();

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
    void initialize()
    {
        try {
            UsernameTF.setText((String) Client.is.readObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PassengerAgeComboBox.getItems().addAll("Взpослые(16+ лет)", "Подpостки(12-15 лет)", "Дети(2-14 лет)", "Младенцы(0-2 года)");
        LaggageComboBox.getItems().addAll("Pучная кладь", "Дополнительная pучная кладь", "Основной багаж", "Дополнительный основной багаж", "Кpупногабаритный багаж");

        LetsFlyButton.setOnAction(event -> {
            String airport = FlyToAirportField.getText().trim();
            String date = null;
            try {
                date = new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(FlyOutDateTF.getValue().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String age = PassengerAgeComboBox.getValue();
            String laggage = LaggageComboBox.getValue();
            String userName = UsernameTF.getText();
            String from = this.fromField.getCharacters().toString();

            ChronoLocalDate dt = LocalDate.from(ZonedDateTime.now());

            if(!FlyOutDateTF.getValue().isAfter(dt) || date == null){
                informationText.setText("Неверная дата");
                return;
            }

            String data = age + "," + laggage + "," + userName+ "," + from+",";
            String clientMessage1 = "addInfoToUserData," + data;
            try {
                Client.os.writeObject(clientMessage1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fl = airport + "," + date ;
            String clM = "сheckAvFl," + fl;
            System.out.println(clM);
            try {
                Client.os.writeObject(clM);
                String str = (String)Client.is.readObject();
                System.out.println("Данные получены на mainwindow " + str);
                if (str.equals("success"))
                {
                    String clM1 = "getAvFlID," + fl + "," + from;
                    Client.os.writeObject(clM1);
                    System.out.println("Посылаем для получения ID " + clM1);
                    String ids = (String)Client.is.readObject();
                    System.out.println("Получение ID + count " + ids);
                    if(!ids.equals("fail")) {
                        informationText.setText("");
                        String clM2 = "senddata," + ids + "," + UsernameTF.getText();
                        System.out.println(clM2);
                        Client.os.writeObject(clM2);
                        this.history.setPreviousPath("AdMainWin.fxml");
                        allFly=0;
                        openNewScene("/Client/AvailableFlightsWindow.fxml");
                    }
                    else
                        informationText.setText("Полётов не найдено");
                }
                else if (str.equals("fail"))  informationText.setText("Полётов не найдено");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cabinetButton.setOnAction(actionEvent -> {
            String clM2 = "sendData," + UsernameTF.getText();


            System.out.println(clM2);
            try {
                Client.os.writeObject(clM2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            openNewScene("/Client/cabinet.fxml");
        });

        AllFlyButton.setOnAction(event -> {
            try {
                String clM1 = "getAllFlID";
                Client.os.writeObject(clM1);

                String ids = (String)Client.is.readObject();
                if(!ids.equals("fail")) {
                    informationText.setText("");
                    String clM2 = "senddata," + ids + "," + UsernameTF.getText();
                    System.out.println(clM2);
                    Client.os.writeObject(clM2);
                    this.history.setPreviousPath("AdMainWin.fxml");
                    allFly=1;
                    openNewScene("/Client/AvailableFlightsWindow.fxml");
                }
                else
                    informationText.setText("Полётов не найдено");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        exitButton.setOnAction(actionEvent -> {
            openNewScene("/Client/LoginWindow.fxml");
        });

    }
}
