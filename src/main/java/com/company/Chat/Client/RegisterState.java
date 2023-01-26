package com.company.Chat.Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class RegisterState implements State{
    private Main app;
    private RegisterController controller;

    public RegisterState(Main app) {
        this.app = app;
    }

    @Override
    public void start() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setState(this);

        Scene scene = new Scene(root, 600, 400);
        app.getMainStage().setScene(scene);
        app.getMainStage().show();
    }

    public void toLogin()throws IOException {
        app.setState(new LoginState(app));
        app.getState().start();
    }

    @Override
    public void send(String message){
        this.app.getSocketOut().println(message);
    }

    @Override
    public void handleMessage(String message) {
        System.out.println("REGISTER STATE: "+message);
    }
}
