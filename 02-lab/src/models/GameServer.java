package models;

import utils.CounterLock;

public class GameServer {
    private int requiredPlayers;
    private CounterLock playersCount;

    public GameServer(int requiredPlayers) {
        this.requiredPlayers = requiredPlayers;
        this.playersCount = new CounterLock();
    }

    public void joinGame(String username) {
        System.out.println("User " + username + " has joined the game!");
        playersCount.advance();
        startGame();
    }

    private void startGame() {
        try {
            playersCount.await(requiredPlayers);
        }
        catch (InterruptedException ex) {
            System.out.println("Waiting has been interrupted.");
        }

        System.out.println("Game started.");
    }
}
