package client;

import java.io.ObjectInputStream;
import shared.GameState;
import shared.NetworkMessage;

public class ClientReceiver extends Thread {
    private GameClient client;
    private ObjectInputStream in;

    public ClientReceiver(GameClient client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();

                if (obj instanceof NetworkMessage) {
                    NetworkMessage msg = (NetworkMessage) obj;
                    handleMessage(msg);
                } else if (obj instanceof GameState) {
                    GameState state = (GameState) obj;
                    System.out.println("Received Game State Update");
                }
            }
        } catch (Exception e) {
            System.err.println("Connection closed or lost: " + e.getMessage());
        }
    }

    private void handleMessage(NetworkMessage msg) {
        switch (msg.getType()) {
            case UPDATE_STATE:
                if (msg.getGameState() != null) {
                    System.out.println("Updating Game Board...");
                }
                break;
            case ERROR_MESSAGE:
                System.err.println("Server Error: " + msg.getTextContent());
                break;
            case INFO_MESSAGE:
                System.out.println("Server Info: " + msg.getTextContent());
                break;
            default:
                break;
        }
    }
}