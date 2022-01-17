package chatapplication.chat;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientChatController implements Initializable {
    @FXML
    private Button button_send;
    @FXML
    private Label label;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vBox_messages;
    @FXML
    private ScrollPane scrollPane;

    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView imageView = new ImageView(new Image("file:image/send.png"));
        imageView.setFitHeight(15);
        imageView.setFitWidth(18);

        button_send.setGraphic(imageView);

        label.setText("Client: "+ ClientConnectController.login_name);
        try{
            this.client = new Client(new Socket("localhost", Client.port));
        }catch (IOException e){
            e.printStackTrace();
        }

        vBox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((double) newValue);
            }
        });

        client.receiveMsgFromServer(vBox_messages);

        button_send.setOnAction(event -> {
            String msgToSend = tf_message.getText();
            if (!msgToSend.isEmpty()){
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 10));

                Text text = new Text(msgToSend);
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-background-color: #DCF8C6;"  +
                        "-fx-background-radius: 20px;"
                );
                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
                vBox_messages.getChildren().add(hBox);

                client.sendMsgToServer(msgToSend);
                tf_message.clear();
            }
        });
    }

    public static void addLabel(String msgFromServer, VBox vBox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(msgFromServer);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: white;"  +
                "-fx-background-radius: 20px;"
        );
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
}
