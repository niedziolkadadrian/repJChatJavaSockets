package com.company.Chat.Server;

import java.util.ArrayList;
import java.util.List;

public class UsersManager {
    boolean loggedIn;
    private String userName;
    static private final List<String> tempUserNames = new ArrayList<>();
    static private final List<String> tempPasswords = new ArrayList<>();

    public UsersManager() {
        loggedIn = false;

        tempUserNames.add("User1");
        tempUserNames.add("User2");
        tempUserNames.add("User3");
        tempUserNames.add("admin");

        tempPasswords.add("1234");
        tempPasswords.add("1234");
        tempPasswords.add("1234");
        tempPasswords.add("admin");
    }

    public void login(String userName, String password) throws NoSuchUserError, WrongPasswordError {
        //Nie ma takiego uzytkownika
        if(!tempUserNames.contains(userName))
            throw new NoSuchUserError();
        //Nieprawidłowe hasło
        if(!tempPasswords.get(tempUserNames.indexOf(userName)).equals(password))
            throw new WrongPasswordError();
        //Login i hasło prawidłowe
        this.userName = userName;
        this.loggedIn = true;
    }

    public String getUserName() {
        return userName;
    }

    public void logOut(ClientHandler connection){
        if(loggedIn){
            connection.getServer().delUserConn(this.userName, connection);
            this.loggedIn = false;
            this.userName = null;
        }
    }

    public void register(String userName, String password) throws UserAlreadyExistError {
        if(tempUserNames.contains(userName))
            throw new UserAlreadyExistError();

        tempUserNames.add(userName);
        tempPasswords.add(password);
    }
}
