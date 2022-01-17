package chatapplication.chat;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected BufferedWriter bufferedWriter;
    static int port;

    public  Server(){}

    public Server(ServerSocket serverSocket) {
        try{
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            System.out.println("Error creating server");
            e.printStackTrace();
        }
    }

    public void sendMsgToClient(String msgToClient){
        try{
            bufferedWriter.write("Server: " + msgToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to client");
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    public void receiveMsgFromClient(VBox vBox){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String msgFromClient = bufferedReader.readLine();
                        ClientChatController.addLabel(msgFromClient, vBox);
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

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try{
            if (bufferedReader != null)
                bufferedReader.close();

            if (bufferedWriter != null)
                bufferedWriter.close();

            if (socket != null)
                socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
