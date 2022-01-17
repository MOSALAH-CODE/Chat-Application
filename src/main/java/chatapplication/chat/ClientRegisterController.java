package chatapplication.chat;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientRegisterController implements Initializable {
    @FXML
    private TextField tf_name;
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
                boolean error = false;
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc-chat", "root", "root");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from user");
                if (tf_name.getText().isEmpty() || tf_userName.getText().isEmpty() || pf_password.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please fill in the blanks");
                    alert.show();
                }else {
                    while (resultSet.next()) {
                        if (Objects.equals(tf_userName.getText(), resultSet.getString("username"))) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("User name already exists");
                            alert.show();
                            error = true;
                        }
                    }
                    if (!error) {
                        Statement statementInsert = connection.createStatement();
                        statementInsert.execute("INSERT INTO USER VALUES('" + tf_name.getText() + "', '" + tf_userName.getText() + "', '" + pf_password.getText() + "');");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setContentText("Successfully registered");
                        alert.show();
                        stage = (Stage) scenePane.getScene().getWindow();
                        stage.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}