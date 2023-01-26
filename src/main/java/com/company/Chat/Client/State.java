package com.company.Chat.Client;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public interface State {
    void start() throws IOException;
    void send(String message);
    void handleMessage(String message);

    void handleConnectedUserList(CopyOnWriteArrayList<String> usersList);
}
