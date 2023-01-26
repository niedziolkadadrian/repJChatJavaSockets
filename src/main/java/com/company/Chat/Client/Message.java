package com.company.Chat.Client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty text = new SimpleStringProperty();

    public Message(String user, String message) {
        this.username.set(user);
        this.text.set(message);
    }

    public String getUsername() {
        return username.get();
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public void setUsername(String username) {
        this.username.set(username);
    }
    public String getText() {
        return text.get();
    }
    public StringProperty textProperty() {
        return text;
    }
    public void setText(String text) {
        this.text.set(text);
    }
}
