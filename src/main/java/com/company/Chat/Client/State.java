package com.company.Chat.Client;

import java.io.IOException;

public interface State {
    void start() throws IOException;
    void send(String message);
    void handleMessage(String message);
}
