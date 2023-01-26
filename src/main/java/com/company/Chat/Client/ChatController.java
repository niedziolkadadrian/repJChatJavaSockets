package com.company.Chat.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatController {
    private ChatState state;
    @FXML
    private ListView<String> connectedUsersListView;

    @FXML
    private ListView<Message> messages;
    @FXML
    private TextField messageField;
    @FXML
    private Label userNameLabel;

    private final ObservableList<Message> chatMessages = FXCollections.observableArrayList(new ArrayList<>());

    @FXML
    private void initialize() {
        messages.setItems(chatMessages);
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSendMessage();
            }
        });


        messages.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new ListCell<Message>() {
                    private VBox messageBox;
                    private Label userName;
                    private Label message;
                    {
                        messageBox = new VBox();
                        messageBox.setPrefWidth(1);
                        userName = new Label();
                        userName.setStyle("-fx-font-weight:bold; -fx-text-fill: #dedcd7; -fx-text-fill-selected: #dedcd7;");
                        message = new Label();

                        message.setWrapText(true);
                        message.setMinWidth(1);
                        message.setStyle("-fx-text-fill: #ffffff; -fx-text-fill-selected: #ffffff;");
                        messageBox.getChildren().addAll(userName, message);
                    }
                    @Override
                    protected void updateItem(Message item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            userName.textProperty().bind(item.usernameProperty());
                            message.textProperty().bind(item.textProperty());
                            setGraphic(messageBox);
                        }
                    }
                };
            }
        });
    }

    public void setState(ChatState state) {
        this.state = state;
    }

    public void setConnectedUsersList(CopyOnWriteArrayList<String> users){
        ObservableList<String> userList= FXCollections.observableArrayList(new ArrayList<>());
        userList.addAll(users);
        connectedUsersListView.setItems(userList);
    }

    public void setUserName(String userName){
        userNameLabel.setText(userName);
    }

    public void printMessage(String user, String message){
        Message newMessage = new Message(user+":", message);
        chatMessages.add(newMessage);
        messages.refresh();
        messages.scrollTo(0);
        messages.scrollTo(newMessage);
    }

    @FXML
    private void handleLogout(){
        this.state.send("LOGOUT");
        try {
            this.state.toLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendMessage(){
        this.state.send("MSG "+this.state.getUserName()+" "+messageField.getText());
        this.messageField.clear();
    }

}
