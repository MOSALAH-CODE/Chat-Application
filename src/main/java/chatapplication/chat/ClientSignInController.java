package chatapplication.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientSignInController implements Initializable {
    @FXML
    private TextField tf_userName;
    @FXML
    private PasswordField pf_password;
    @FXML
    private Button bt_submit;
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_submit.setOnAction(event -> {
            try {
                boolean isSignIn = false;
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-chat", "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from user");
                if (tf_userName.getText().isEmpty() || pf_password.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill in the blanks");
                    alert.show();
                }else {
                    while (resultSet.next()) {
                        if (Objects.equals(tf_userName.getText(), resultSet.getString("username")) &&
                                Objects.equals(pf_password.getText(), resultSet.getString("password"))) {

                            Client client = new Client(resultSet.getString("name"),tf_userName.getText(), pf_password.getText());

                            try{
                                ClientConnectController.login_name = client.name;

                                FXMLLoader fxmlLoader = new FXMLLoader();
                                Stage stageConnect = new Stage();
                                fxmlLoader.setLocation((getClass().getResource("Connect.fxml")));
                                stageConnect.setResizable(false);
                                stageConnect.setScene(new Scene(fxmlLoader.load()));
                                stageConnect.setTitle("Connect");
                                stageConnect.show();

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setContentText("Successfully Sign in");
                            alert.show();

                            stage = (Stage) scenePane.getScene().getWindow();
                            stage.close();
                            isSignIn = true;
                            break;
                        }
                    }
                    if (!isSignIn){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Invalid username or password");
                        alert.show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
