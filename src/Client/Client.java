package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Application {
    public static String clientMessage;
    public static Socket socket;
    public static ObjectOutputStream os;
    public static ObjectInputStream is;
    public static boolean connected = false;
    public static FXMLLoader loader;

    public static void Connect()
    {
        clientMessage = "";

        try {
            socket = new Socket(InetAddress.getLocalHost(), 8070);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
            connected = true;
        } catch (UnknownHostException var1) {
            connected = false;
            System.err.println("Address not available" + var1);
        } catch (IOException var2) {
            connected = false;
            System.err.println("I/О thread error" + var2);
        }
    }

    public static void Disconnect() {
        try {
            if (is != null) {
                is.close();
            }

            if (os != null) {
                os.close();
            }

            if (socket != null) {
                socket.close();
            }

            connected = false;
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LogInWindow.fxml"));
        primaryStage.setTitle("airport");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
