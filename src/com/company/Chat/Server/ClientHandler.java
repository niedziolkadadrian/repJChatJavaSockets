package com.company.Chat.Server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    Socket socket;
    ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Podłączył się: ");
        System.out.println(socket.getInetAddress());
        System.out.println(socket.getPort());

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
