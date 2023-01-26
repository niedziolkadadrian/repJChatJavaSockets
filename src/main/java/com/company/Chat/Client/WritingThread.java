package com.company.Chat.Client;

import java.io.PrintWriter;
import java.util.Scanner;

public class WritingThread extends Thread{
    private final PrintWriter out;

    public WritingThread(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while(true){
            out.println(scanner.nextLine());
        }
    }
}
