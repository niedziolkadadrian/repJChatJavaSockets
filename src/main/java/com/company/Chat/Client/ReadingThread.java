package com.company.Chat.Client;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReadingThread extends Thread{
    private final Main app;


    public ReadingThread(Main app) {
        this.app = app;
    }



    @Override
    public void run() {
        try {
            while (!app.getSocket().isClosed()) {
                String command;
                //server zamknal socket
                if((command = app.getSocketIn().readLine()) == null){
                    app.getSocket().close();
                    app.handleMessage("CONNECTION_CLOSED");
                    continue;
                }
                app.handleMessage(command);
            }
        } catch (IOException ignore) {}
    }
}
