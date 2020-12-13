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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdMainWinController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu pieStatDayTime;

    @FXML
    private MenuItem addFlMenu;

    @FXML
    private MenuItem addAdmin;

    @FXML
    private MenuItem deleteFl;

    @FXML
    private MenuItem deleteAdmin;

    @FXML
    private TextField FirstTF;

    @FXML
    private TextField SecondTF;

    @FXML
    private TextField ThirdTf;

    @FXML
    private TextField SixthTF;

    @FXML
    private TextField FourthTF;

    @FXML
    private TextField FifthTF;

    @FXML
    private MenuItem tableOpen;

    @FXML
    private Label KeyLabel;

    @FXML
    private Button KeyButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button statistics;

    @FXML
    private Text adminlabel;

    @FXML
    private MenuItem blockUser;

    @FXML
    private Text textInformation;

    @FXML
    private TextField SevenTF;

    private History history = History.getInstance();

    private Stage stage = null;

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

    private ObservableList<Flight> flData = FXCollections.observableArrayList();

    private boolean isTable = false;

    String login;


    @FXML
    void initialize() {
        try {
            login = (String) Client.is.readObject();
            String NS = "getClientNameByLogin," + login;
            Client.os.writeObject(NS);
            String ns = (String) Client.is.readObject();
            adminlabel.setText("Логин: " + login + "\n"+ ns);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Visibility(false, false, false, false, false, false, false, false, false);
        addFlMenu.setOnAction(event -> {
            clearText();
            textInformation.setText("");
            Visibility(true, true, true, true, true, true, true, true, true);
            setText("Аэропорт прибытия", "Дата", "Время вылета", "Время прибытия", "Цена", "Количество мест", "Аэропорт отправления", "Добавить", "Введите данные полета:");
            KeyButton.setOnAction(event1 -> {
                String clm = "addFl," + FirstTF.getText() + "," + SecondTF.getText() + "," + ThirdTf.getText() + "," + FourthTF.getText() + "," + FifthTF.getText() + "," + SixthTF.getText() + "," + SevenTF.getText();
                try {
                    Client.os.writeObject(clm);

                    textInformation.setText("Полёт добавлен");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        });
        addAdmin.setOnAction(event -> {
            clearText();
            textInformation.setText("");
            Visibility(false, false, true, true, false, false, false, true,true);
            setText("", "", "Логин", "Пароль", "", "", "", "Добавить", "Введите данные администратора:");
            KeyButton.setOnAction(event1 -> {
                String clm1 = "addAdmin," + ThirdTf.getText() + "," + FourthTF.getText();
                try {
                    Client.os.writeObject(clm1);
                    String result = (String) Client.is.readObject();
                    if(result.equals("success"))
                        textInformation.setText("Учётная запись администратора добавлена");
                    else
                        textInformation.setText("Такой логин уже существует");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });

        FlIDColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("id"));
        inAirport.setCellValueFactory(new PropertyValueFactory<Flight, String>("Airport"));
        date.setCellValueFactory(new PropertyValueFactory<Flight, String>("date"));
        outTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("outTime"));
        inTime.setCellValueFactory(new PropertyValueFactory<Flight, String>("inTime"));
        seatsAmount.setCellValueFactory(new PropertyValueFactory<Flight, String>("seatsAmount"));
        price.setCellValueFactory(new PropertyValueFactory<Flight, String>("price"));
        transfer.setCellValueFactory(new PropertyValueFactory<Flight, String>("transfer"));

        tableOpen.setOnAction(actionEvent -> {
            if(!isTable) {
                flData.clear();
                isTable = true;
                AvailableFlyTableView.setVisible(isTable);
                try {
                    String clM1 = "getAllFlID";
                    Client.os.writeObject(clM1);

                    String ids = (String)Client.is.readObject();
                    if(!ids.equals("fail")) {
                        String clM2 = "senddata," + ids + "," + login;
                        System.out.println(clM2);
                        Client.os.writeObject(clM2);

                        String iduser = "";
                        String id = (String)Client.is.readObject();
                        String[] mp = id.split(",");
                        iduser = mp[2];
                        String id1 = mp[0] + "," + mp[1] + "," + 1;
                        String clm = "getAvFl," + id1;
                        Client.os.writeObject(clm);
                        String data = (String)Client.is.readObject();
                        //String[] count = id.split(",");
                        String[] messParts = data.split(";");
                        for (int i = 0; i < Integer.parseInt(mp[0]); i++)
                        {
                            String[] mesParts = messParts[i].split(",");
                            if (!mesParts[5].equals("0")) {
                                flData.add(new Flight(mesParts[0], mesParts[1], mesParts[2], mesParts[3], mesParts[4], mesParts[5], mesParts[6], mesParts[7]));
                            }
                        }
                        AvailableFlyTableView.setItems(flData);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                isTable = false;
                AvailableFlyTableView.setVisible(isTable);
            }
        });

        exitButton.setOnAction(actionEvent -> {
            openNewScene("/Client/LoginWindow.fxml", true);
        });

        deleteFl.setOnAction(event -> {
            clearText();
            textInformation.setText("");
            Visibility(false, false, true, false, false, false, false, true, true);
            setText("", "", "id полёта", "", "", "", "", "Удалить", "Введите данные полета:");
            KeyButton.setOnAction(event1 -> {
                String clm2 = "deleteFl," + ThirdTf.getText();
                try {
                    Client.os.writeObject(clm2);
                    String result = (String) Client.is.readObject();
                    if(result.equals("success"))
                        textInformation.setText("Полёт удалён");
                    else
                        textInformation.setText("Полёт не найден");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
        deleteAdmin.setOnAction(event -> {
            clearText();
            textInformation.setText("");
            Visibility(false, false, true, false, false, false, false, true, true);
            setText("", "", "Логин", "", "", "", "", "Удалить", "Введите логин администратора:");
            KeyButton.setOnAction(event1 -> {
                String clm3 = "deleteAdmin," + ThirdTf.getText();
                try {
                    Client.os.writeObject(clm3);
                    String result = (String) Client.is.readObject();
                    if(result.equals("success"))
                        textInformation.setText("Администратор удалён");
                    else
                        textInformation.setText("Администратор не найден");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
        blockUser.setOnAction(event -> {
            clearText();
            textInformation.setText("");
            Visibility(false, false, true, false, false, false, false, true, true);
            setText("", "", "Логин", "", "", "",  "","Блокиpовать", "Введите логин пользователя:");
            KeyButton.setOnAction(event1 -> {
                String clm4 = "blockUser," + ThirdTf.getText();
                try {
                    Client.os.writeObject(clm4);
                    String result = (String) Client.is.readObject();
                    if(result.equals("success"))
                        textInformation.setText("Пользователь заблокирован");
                    else
                        textInformation.setText("Пользователь не найден");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        });
        statistics.setOnAction(event -> {
            openNewScene("/Client/PieChart.fxml", false);
        });

    }

    public void Visibility (Boolean one, Boolean two, Boolean three, Boolean four, Boolean five, Boolean six, Boolean seven, Boolean butt, Boolean lab)
    {
        SixthTF.setVisible(six);
        FifthTF.setVisible(five);
        FourthTF.setVisible(four);
        ThirdTf.setVisible(three);
        SecondTF.setVisible(two);
        FirstTF.setVisible(one);
        SevenTF.setVisible(seven);
        KeyButton.setVisible(butt);
        KeyLabel.setVisible(lab);
    }

    public void clearText()
    {
        FirstTF.setText("");
        SecondTF.setText("");
        ThirdTf.setText("");
        FourthTF.setText("");
        FifthTF.setText("");
        SixthTF.setText("");
        SevenTF.setText("");
    }

    public void setText(String one, String two, String three, String four, String five, String six, String seven, String butt, String lab)
    {
        FirstTF.setPromptText(one);
        SecondTF.setPromptText(two);
        ThirdTf.setPromptText(three);
        FourthTF.setPromptText(four);
        FifthTF.setPromptText(five);
        SixthTF.setPromptText(six);
        SevenTF.setPromptText(seven);
        KeyButton.setText(butt);
        KeyLabel.setText(lab);
    }

    public void openNewScene(String window, boolean close)
    {
        if(stage != null && stage.getScene().getWindow().isShowing())
            return;

        if(close)
            statistics.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
