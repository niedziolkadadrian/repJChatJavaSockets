package com.company.Chat.Server;

public class WrongPasswordError extends Exception{
    public WrongPasswordError() {
        super("Nieprawdiłowe hasło!");
    }
}
