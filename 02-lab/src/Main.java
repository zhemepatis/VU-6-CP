import models.GameServer;
import utils.PlayerTask;

public class Main {
    public static final int REQUIRED_PLAYERS = 25;

    public static void main(String[] args) throws Exception {
        GameServer server = new GameServer(REQUIRED_PLAYERS);
        Thread[] threads = new Thread[REQUIRED_PLAYERS];

        // creating and running tasks, threads
        for (int i = 0; i < REQUIRED_PLAYERS; ++i) {
            PlayerTask task = new PlayerTask(server, ("Thread" + (i+1)));
            threads[i] = new Thread(task);
            threads[i].start();
        }

        // waiting for all threads to finish their tasks
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
