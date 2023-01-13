package com.company.Chat.Client;

import java.util.Scanner;

public class ReadingThread extends Thread{
    private final Scanner in;

    public ReadingThread(Scanner in) {
        this.in = in;
    }

    @Override
    public void run() {
        while(true){
            if(in.hasNextLine())
                System.out.println(in.nextLine());
        }
    }
}
