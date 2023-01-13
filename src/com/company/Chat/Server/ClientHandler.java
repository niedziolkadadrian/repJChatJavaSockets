package com.company.Chat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" podłączył się.");

        while(!socket.isClosed()){

            String message = null;
            try {
                if((message = in.readLine()) == null){
                    closeSocket();
                    continue;
                }
                ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" "+message);
                out.println("ECHO: "+message);
            } catch (IOException e) {
                closeSocket();
            }
        }
    }

    private void closeSocket(){
        ServerLogger.log(socket.getInetAddress()+":"+socket.getPort()+" rozłączył się.");
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
