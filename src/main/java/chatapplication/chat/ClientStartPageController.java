package chatapplication.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientStartPageController implements Initializable {

    @FXML
    private Button bt_register;

    @FXML
    private Button bt_Sign_in;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_register.setOnAction(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                Stage stage = new Stage();
                fxmlLoader.setLocation((getClass().getResource("Register.fxml")));
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setResizable(false);
                stage.setTitle("Register");
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        bt_Sign_in.setOnAction(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                Stage stage = new Stage();
                fxmlLoader.setLocation((getClass().getResource("SignIn.fxml")));
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setResizable(false);
                stage.setTitle("Sign In");
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}
