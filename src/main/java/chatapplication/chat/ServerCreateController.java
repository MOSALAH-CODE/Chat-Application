package chatapplication.chat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerCreateController implements Initializable {

    @FXML
    private Button bt_connect;
    @FXML
    private TextField tf_port;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tf_port.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tf_port.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        bt_connect.setOnAction(event -> {
            try {
                Server.port = Integer.parseInt(tf_port.getText());
                tf_port.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader();
                Stage stage = new Stage();
                fxmlLoader.setLocation((getClass().getResource("ServerChat.fxml")));
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setResizable(false);
                stage.setTitle("Server: port " + Server.port );
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
