package com.company.Chat.Client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatState implements State{
    private Main app;
    private ChatController controller;

    public ChatState(Main app) {
        this.app = app;
    }

    @Override
    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setState(this);
        controller.setUserName(app.getUserName());
        controller.setConnectedUsersList(app.getConnectedUsers());

        Scene scene = new Scene(root, 600, 400);
        app.getMainStage().setScene(scene);
        app.getMainStage().show();
    }

    @Override
    public void send(String message){
        this.app.send(message);
    }

    public void toLogin() throws IOException {
        app.setState(new LoginState(app));
        app.getState().start();
    }
    @Override
    public void handleMessage(String message) {
        String[] command = message.split("\s", 2);
        if(command[0].equals("CONNECTION_CLOSED")) {
            Platform.runLater(() -> {
                try {
                    app.setState(new LoginState(app));
                    app.getState().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (command[0].equals("ACTION") && command.length == 2) {
            String[] action = command[1].split("\s", 2);
            if(action[0].equals("USER_CONNECTED") && action.length == 2){
                app.addConnectedUser(action[1]);
            } else if (action[0].equals("USER_DISCONNECTED") && action.length ==2) {
                app.delConnectedUser(action[1]);
            }
        } else if (command[0].equals("MSG") && command.length ==2) {
            String[] msg = command[1].split("\s", 2);
            if(msg.length == 2){
                Platform.runLater(() -> controller.printMessage(msg[0], msg[1]));
            }
        }
    }
    @Override
    public void handleConnectedUserList(CopyOnWriteArrayList<String> usersList){
        Platform.runLater(() -> controller.setConnectedUsersList(usersList));
    }

    public String getUserName(){
        return this.app.getUserName();
    }
}
