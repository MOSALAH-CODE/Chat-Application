package chatapplication.chat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientConnectController implements Initializable {
    @FXML
    private TextField tf_port;
    @FXML
    private Button bt_connect;
    @FXML
    private Label label;
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    static String login_name;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("Client: " + login_name);

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
                Client.port = Integer.parseInt(tf_port.getText());

                FXMLLoader fxmlLoader = new FXMLLoader();
                Stage stageClientChat = new Stage();
                fxmlLoader.setLocation((getClass().getResource("ClientChat.fxml")));
                stageClientChat.setScene(new Scene(fxmlLoader.load()));
                stageClientChat.setResizable(false);
                stageClientChat.setTitle("Client: " + ClientConnectController.login_name);
                stageClientChat.show();

                stage = (Stage) scenePane.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
