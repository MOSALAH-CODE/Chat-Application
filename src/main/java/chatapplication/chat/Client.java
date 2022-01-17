package chatapplication.chat;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client extends Server{
    protected String name;
    private String username;
    private String password;

    public Client(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Client(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error creating Client");
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void sendMsgToServer(String msgToServer) {
        try{
            bufferedWriter.write(ClientConnectController.login_name + ": " + msgToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to server");
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void receiveMsgFromServer(VBox vBox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String msgFromServer = bufferedReader.readLine();
                        ServerChatController.addLabel(msgFromServer, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from the client");
                        closeEverything(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
