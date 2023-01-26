package com.company.Chat.Server;

public class NoSuchUserError extends Exception{
    public NoSuchUserError() {
        super("Nie ma takiego użytkownika!");
    }
}
