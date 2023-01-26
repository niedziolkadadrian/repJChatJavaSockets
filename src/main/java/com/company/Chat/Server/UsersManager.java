package com.company.Chat.Server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UsersManager {
    boolean loggedIn;
    private String userName;
    static private HashMap<String,UserPassword>  users= new HashMap<> ();

    private MessageDigest messageDigest = null;


    public UsersManager() {
        loadFromFile();
        loggedIn = false;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Nie udało się stworzyć algorytmu hashującego!");
        }
    }

    public void login(String userName, String password) throws NoSuchUserError, WrongPasswordError {
        //Nie ma takiego uzytkownika
        if(!users.containsKey(userName))
            throw new NoSuchUserError();

        byte[] hashPassword = hashPassword(users.get(userName).getSalt(), password);
        //Nieprawidłowe hasło
        if(!Arrays.equals(hashPassword, users.get(userName).getHashPassword()))
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
        if(users.containsKey(userName))
            throw new UserAlreadyExistError();

        byte[] salt = generateSalt();
        byte[] hashPassword = hashPassword(salt, password);
        users.put(userName, new UserPassword(salt,hashPassword));

        saveToFile();
    }
    private void loadFromFile(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt"));
            users = (HashMap<String, UserPassword>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nie udało się wczytać pliku.");
        }
    }
    private void saveToFile(){
        try {
            // Write the map to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.txt"));
            out.writeObject(users);
            out.close();
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać uzytkowników");
        }
    }
    private byte[] hashPassword(byte[] salt, String password){
        if(messageDigest == null)
            return password.getBytes(StandardCharsets.UTF_8);

        messageDigest.update(salt);
        return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private byte[] generateSalt(){
        byte[] salt = new byte[16];
        Random r = new Random();
        r.nextBytes(salt);

        return salt;
    }
}
