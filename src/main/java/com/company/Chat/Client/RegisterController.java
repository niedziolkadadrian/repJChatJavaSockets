package com.company.Chat.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class RegisterController {
    private RegisterState state;
    @FXML
    private Label errorMsg;
    @FXML
    private HBox errorBox;

    @FXML
    private TextField userField;
    @FXML
    private PasswordField password1Field;
    @FXML
    private PasswordField password2Field;

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

    @FXML
    private void handleRetry(){
        this.state.connect();
    }

    @FXML
    private void handleRegister(){
        String user = userField.getText();
        String password1 = password1Field.getText();
        String password2 = password2Field.getText();
        if(password1.equals(password2)){
            this.state.send("REGISTER "+user+" "+password1);
        }
        else{
            this.setErrorMsg("Podane hasła są różne!");
        }
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg.setText(errorMsg);
    }

    public void setErrorBoxVisibility(Boolean visibility){
        errorBox.setVisible(visibility);
    }
}
