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
            String[] values = polecenie[1].split("\\s");
            if(values.length == 2 && !values[0].isBlank() && !values[1].isBlank()){
                try{
                    handler.getUser().login(values[0], values[1]);
                    handler.getServer().addUserConn(handler.getUser().getUserName(),handler);
                    handler.sendMessage("LOGIN_SUCCESS");
                    handler.setUserState(new AuthUser(handler));
                } catch (NoSuchUserError | WrongPasswordError e) {
                    handler.sendMessage("ERROR "+e.getMessage());
                    ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+
                            "ERROR "+e.getMessage());
                }
            }
        }else if(polecenie.length == 2 && polecenie[0].equals("REGISTER") && !polecenie[1].isBlank()) {
            String[] values = polecenie[1].split("\\s");
            if(values.length == 2 && !values[0].isBlank() && !values[1].isBlank()) {
                try{
                    handler.getUser().register(values[0], values[1]);
                } catch (UserAlreadyExistError e) {
                    handler.sendMessage("ERROR "+e.getMessage());
                    ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+
                            "ERROR "+e.getMessage());
                }
            }
        }else{
            ServerLogger.log(handler.getSocket().getInetAddress()+":"+handler.getSocket().getPort()+" "+message);
        }
    }
}
