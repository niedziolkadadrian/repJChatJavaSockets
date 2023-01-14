package com.company.Chat.Server;


public class NoAuthUser implements UserState{
    private final ClientHandler handler;
    public NoAuthUser(ClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(String message) {
        String[] polecenie = message.split("\\s",2);
        if(polecenie.length == 0)
            return;

        if(polecenie.length == 2 && polecenie[0].equals("LOGIN") && !polecenie[1].isBlank()){
            handler.setUser(polecenie[1]);
            handler.getServer().addUserConn(polecenie[1],handler);
            handler.setUserState(new AuthUser(handler));
        }else if(polecenie.length == 2 && polecenie[0].equals("REGISTER") && !polecenie[1].isBlank()) {
            System.out.println("REJESTRACJA");
        }
        else{
            ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+" "+message);
        }
    }
}
