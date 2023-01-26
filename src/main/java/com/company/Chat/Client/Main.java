package com.company.Chat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {
    private State state;
    private Stage mainStage;

    private Socket socket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    private ReadingThread readingThread;

    public static void main(String[] args){
        launch(args);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketOut = new PrintWriter(socket.getOutputStream(), true);

        readingThread = new ReadingThread(this);
        readingThread.start();
    }

    public BufferedReader getSocketIn() {
        return socketIn;
    }

    public PrintWriter getSocketOut() {
        return socketOut;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chat Application");
        this.mainStage = stage;

        this.state = new LoginState(this);
        this.state.start();
    }

    public void handleMessage(String message){
        this.state.handleMessage(message);
    }

    @Override
    public void stop() throws Exception {
        if(socket != null){
            socket.close();
        }
    }
}