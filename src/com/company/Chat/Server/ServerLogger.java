package com.company.Chat.Server;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger {
    public static void log(String message){
        System.out.println("["+
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                "]: "+message);
    }
}
