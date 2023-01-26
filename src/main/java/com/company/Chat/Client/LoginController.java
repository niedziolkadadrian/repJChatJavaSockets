package com.company.Chat.Client;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class LoginController {
    private LoginState state;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private HBox errorBox;

    public void setState(LoginState state) {
        this.state = state;
    }

    @FXML
    private void handleToRegister(){
        try {
            this.state.toRegister();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin(){
        String login = loginField.getText();
        String password = passwordField.getText();
        this.state.send("LOGIN "+login+" "+password);
    }

    public void setErrorBoxVisibility(Boolean visibility){
        errorBox.setVisible(visibility);
    }

    @FXML void handleRetry(){
        this.state.connect();
    }
}
