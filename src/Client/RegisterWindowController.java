package Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField SignUpName;

    @FXML
    private TextField SignUpSurname;

    @FXML
    private CheckBox SignUpCheckBoxMale;

    @FXML
    private CheckBox SignUpCheckBoxFemale;

    @FXML
    private TextField SignUpCountry;

    @FXML
    private CheckBox SignUpCheckBoxOther;

    @FXML
    void initialize()
    {
        SignUpButton.setOnAction(event ->
        {
            String Gender;
            if (SignUpCheckBoxMale.isSelected()) Gender = "Мужской";
            else if (SignUpCheckBoxFemale.isSelected()) Gender = "Женский";
            else Gender = "Общий";
            String Name = SignUpName.getText();
            String Surname = SignUpSurname.getText();
            String userName = LoginField.getText();
            String Password = PasswordField.getText();
            String Country = SignUpCountry.getText();

            String clM = "checkUserInDB," + userName;
            try {
                Client.Connect();
                Client.os.writeObject(clM);
                String mes = (String)Client.is.readObject();
                if(mes.equals("success"))
                {
                    String clientMessage = "addClient," + Name + "," + Surname  + "," + Gender + "," + Country + "," + userName + "," + Password;
                    try {
                        Client.os.writeObject(clientMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String sendlM = "sendData," + userName;
                    try {
                        Client.os.writeObject(sendlM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    openNewScene("/Client/ClientMainWindow.fxml");
                }
                else if (mes.equals("fail"))
                {
                    LoginField.clear();
                    LoginField.setPromptText("Пользователь с таким логином уже существует");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
        //Client.Disconnect();
    }

    public void openNewScene(String window)
    {
        SignUpButton.getScene().getWindow().hide();

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
