package com.company.Chat.Client;

import javafx.fxml.FXML;

import java.io.IOException;

public class RegisterController {
    private RegisterState state;

    public void setState(RegisterState state) {
        this.state = state;
    }

    @FXML
    private void handleToLogin(){
        try {
            this.state.toLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
