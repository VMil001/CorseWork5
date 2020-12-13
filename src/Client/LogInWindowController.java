package Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class LogInWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button AuthSignUpButton;

    @FXML
    private Button LoginSignUpButton;

    @FXML
    private CheckBox AdminLogInWindow;

    @FXML
    private CheckBox UserLogInWindow;

    @FXML
    private Label CautionLabel;

    public String message;

    @FXML
    void initialize()
    {
        Client.Connect();

        AuthSignUpButton.setOnAction(event -> {
            String loginText = LoginField.getText().trim();
            String loginPassword = PasswordField.getText().trim();
            String clm = "isBlocked," + loginText;
            try {
                Client.os.writeObject(clm);
                message = (String)Client.is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (message.equals("no")) {
                if (UserLogInWindow.isSelected() && !AdminLogInWindow.isSelected()) {
                    String clientMessage = "checkLoginClient," + loginText + "," + loginPassword;

                    try {
                        Client.os.writeObject(clientMessage);
                        message = (String) Client.is.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (message.equals("successClient")) {
                        String clM = "sendData," + loginText;
                        try {
                            Client.os.writeObject(clM);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        openNewScene("/Client/ClientMainWindow.fxml");
                    } else if (message.equals("fail"))
                        CautionLabel.setText("Такого пользователя не существует!");
                } else if (AdminLogInWindow.isSelected() && !UserLogInWindow.isSelected()) {
                    String clientMessage = "checkLoginAdmin," + loginText + "," + loginPassword;

                    try {
                        Client.os.writeObject(clientMessage);
                        message = (String) Client.is.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (message.equals("successAdmin")) {
                        String clM = "sendData," + loginText;
                        try {
                            Client.os.writeObject(clM);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        openNewScene("/Client/AdMainWin.fxml");
                    } else if (message.equals("fail"))
                        CautionLabel.setText("Такого администратора не существует!");
                } else
                    CautionLabel.setText("Пожалуйста, выберите администратор либо пользователь!");
            }
            else AuthSignUpButton.getScene().getWindow().hide();
        });

        LoginSignUpButton.setOnAction(event ->
        {
            openNewScene("/Client/RegisterWindow.fxml");
        });
    }

    public void openNewScene(String window)
    {
        LoginSignUpButton.getScene().getWindow().hide();

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

