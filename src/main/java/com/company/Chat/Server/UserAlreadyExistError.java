package com.company.Chat.Server;

public class UserAlreadyExistError extends Exception{
    public UserAlreadyExistError() {
        super("Taki użytkownik już istnieje!");
    }
}
