package com.company.Chat.Client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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
        this.app.getSocketOut().println(message);
    }

    @Override
    public void handleMessage(String message) {
        String[] command = message.split("\s", 2);
        if(command[0].equals("CONNECTION_CLOSED")){
            this.controller.setErrorBoxVisibility(true);
        } else if (command[0].equals("LOGIN_SUCCESS")) {
            this.app.setState(new ChatState(app));
            try {
                this.app.getState().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("LOGIN STATE: "+message);
    }
}
