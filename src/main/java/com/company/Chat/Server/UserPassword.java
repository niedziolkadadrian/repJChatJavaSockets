package com.company.Chat.Server;

import java.io.Serializable;

public class UserPassword implements Serializable {
    private byte[] salt;
    private byte[] hashPassword;

    public UserPassword(byte[] salt, byte[] hashPassword) {
        this.salt = salt;
        this.hashPassword = hashPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHashPassword() {
        return hashPassword;
    }
}
