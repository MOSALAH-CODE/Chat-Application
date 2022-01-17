module chatapplication.chatapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens chatapplication.chat to javafx.fxml;
    exports chatapplication.chat;
}