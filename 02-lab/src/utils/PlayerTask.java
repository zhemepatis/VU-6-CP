package utils;

import models.GameServer;

public class PlayerTask implements Runnable {
    private GameServer server;
    private String username;
    
    public PlayerTask(GameServer server, String username) {
        this.server = server;
        this.username = username;
    }

    @Override
    public void run() {
        server.joinGame(username);
    }
}
