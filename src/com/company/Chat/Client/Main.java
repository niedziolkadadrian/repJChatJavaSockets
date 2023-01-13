package com.company.Chat.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 8189);

        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Scanner in = new Scanner(s.getInputStream());

        ReadingThread r = new ReadingThread(in);
        WritingThread w = new WritingThread(out);
        r.start();
        w.start();
    }
}