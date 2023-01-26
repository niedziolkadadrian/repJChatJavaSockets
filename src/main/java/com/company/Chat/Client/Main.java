package com.company.Chat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
        /*Socket s = new Socket("localhost", 8188);

        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Scanner in = new Scanner(s.getInputStream());

        ReadingThread r = new ReadingThread(in);
        WritingThread w = new WritingThread(out);
        r.start();
        w.start();*/
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Chat Application");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}