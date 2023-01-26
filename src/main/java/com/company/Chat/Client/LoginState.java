package com.company.Chat.Client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoginState implements State{
    private Main app;
    private LoginController controller;

    public LoginState(Main app) {
        this.app = app;
    }

    @Override
    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setState(this);

        Scene scene = new Scene(root, 600, 400);
        app.getMainStage().setScene(scene);
        app.getMainStage().show();

        if(this.app.getSocket() == null)
            this.connect();
    }

    public void toRegister() throws IOException {
        app.setState(new RegisterState(app));
        app.getState().start();
    }

    public void connect(){
        try{
            Socket s = new Socket("localhost", 8188);
            app.setSocket(s);
            this.controller.setErrorBoxVisibility(false);
        } catch (IOException e) {
            this.controller.setErrorBoxVisibility(true);
        }
    }

    @Override
    public void send(String message){
        this.app.send(message);
    }

    @Override
    public void handleMessage(String message) {
        String[] command = message.split("\s", 2);
        if(command[0].equals("CONNECTION_CLOSED")){
            this.controller.setErrorBoxVisibility(true);
        } else if (command[0].equals("LOGIN_SUCCESS") && command.length == 2 && !command[1].isBlank()) {
            app.clearConnectedUsers();
            app.setUserName(command[1]);
            Platform.runLater(() -> {
                try {
                app.setState(new ChatState(app));
                app.getState().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (command[0].equals("ACTION") && command.length == 2 && !command[1].isBlank()) {
            String[] action = command[1].split("\s", 2);
            if(action[0].equals("USER_CONNECTED") && action.length == 2){
                app.addConnectedUser(action[1]);
            } else if (action[0].equals("USER_DISCONNECTED") && action.length ==2) {
                app.delConnectedUser(action[1]);
            }
        } else if (command[0].equals("ERROR") && command.length == 2 && !command[1].isBlank()) {
            Platform.runLater(() -> this.controller.setErrorMsg(command[1]));
        }
    }

    @Override
    public void handleConnectedUserList(CopyOnWriteArrayList<String> usersList) {}
}
