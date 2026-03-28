package server.serverTester;

import server.GameServer;

public class ServerTester {
    public static void main(String[] args) {
        GameServer gs = GameServer.getInstance();
        gs.start();
    }
}
