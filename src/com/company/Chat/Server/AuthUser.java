package com.company.Chat.Server;

public class AuthUser implements UserState{
    private final ClientHandler handler;

    public AuthUser(ClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleMessage(String message) {
        String[] polecenie = message.split("\\s",2);
        if(polecenie.length == 0)
            return;

        if(polecenie[0].equals("LOGOUT")){
            handler.logoutUser();
            handler.setUserState(new NoAuthUser(handler));
        }else if(polecenie.length == 2 && polecenie[0].equals("MSG") && !polecenie[1].isBlank()) {
            //broadcast: MSG <UZYTKOWNIK> <WIADOMOSC>
            handler.getServer().broadcast(polecenie[0]+" "+handler.getUser()+" "+polecenie[1]);

            ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+
                    " Użytkownik "+handler.getUser()+" wysłał wiadomość: "+message);
        }else{
            ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+" "+message);
        }
    }
}
