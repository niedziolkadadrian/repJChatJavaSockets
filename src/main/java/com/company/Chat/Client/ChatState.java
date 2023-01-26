package com.company.Chat.Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

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

        Scene scene = new Scene(root, 600, 400);
        app.getMainStage().setScene(scene);
        app.getMainStage().show();
    }

    @Override
    public void send(String message){
        this.app.getSocketOut().println(message);
    }

    @Override
    public void handleMessage(String message) {
        System.out.println("CHAT STATE: "+message);
    }
}
