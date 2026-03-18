package client;

import java.io.*;
import java.net.*;

public class GameClient {

    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 12345;

    private final String host;
    private final int port;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private String playerName;
    private boolean connected = false;

    private GameStateListener gameStateListener;
    private ConnectionListener connectionListener;

    public GameClient() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(String playerName) throws IOException {
        this.playerName = playerName;

        socket = new Socket(host, port);
        out    = new PrintWriter(new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream())), true);
        in     = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));

        connected = true;

        sendRaw("JOIN:" + playerName);

        Thread receiveThread = new Thread(this::receiveLoop, "ClientReceiver");
        receiveThread.setDaemon(true);
        receiveThread.start();

        System.out.println("[GameClient] Connected to " + host + ":" + port
                           + " as \"" + playerName + "\"");
    }

    public void disconnect() {
        if (!connected) return;
        connected = false;
        try {
            sendRaw("LEAVE:" + playerName);
            socket.close();
        } catch (IOException e) {
        }
        System.out.println("[GameClient] Disconnected.");
    }

    public void sendAction(String actionMessage) {
        if (!connected) {
            System.err.println("[GameClient] Cannot send – not connected.");
            return;
        }
        String networkMessage = "ACTION:" + playerName + ":" + actionMessage;
        sendRaw(networkMessage);
        System.out.println("[GameClient] Sent → " + networkMessage);
    }

    public void sendChat(String text) {
        if (!connected) return;
        sendRaw("CHAT:" + playerName + ":" + text);
    }

    public void setGameStateListener(GameStateListener listener) {
        this.gameStateListener = listener;
    }

    public void setConnectionListener(ConnectionListener listener) {
        this.connectionListener = listener;
    }

    public boolean isConnected() { return connected; }
    public String  getPlayerName() { return playerName; }
    public String  getHost()       { return host; }
    public int     getPort()       { return port; }

    private void receiveLoop() {
        try {
            String message;
            while (connected && (message = in.readLine()) != null) {
                handleServerMessage(message);
            }
        } catch (IOException e) {
            if (connected) {
                System.err.println("[GameClient] Connection lost: " + e.getMessage());
                connected = false;
                if (connectionListener != null) {
                    connectionListener.onDisconnected("Connection lost: " + e.getMessage());
                }
            }
        }
    }

    private void handleServerMessage(String message) {
        System.out.println("[GameClient] Received ← " + message);

        if (message.startsWith("STATE:")) {
            String gameState = message.substring("STATE:".length());
            if (gameStateListener != null) {
                gameStateListener.onGameStateReceived(gameState);
            }

        } else if (message.startsWith("INFO:")) {
            String info = message.substring("INFO:".length());
            System.out.println("[Server INFO] " + info);
            if (connectionListener != null) {
                connectionListener.onInfoReceived(info);
            }

        } else if (message.startsWith("ERROR:")) {
            String error = message.substring("ERROR:".length());
            System.err.println("[Server ERROR] " + error);
            if (connectionListener != null) {
                connectionListener.onErrorReceived(error);
            }

        } else if (message.equals("START")) {
            System.out.println("[GameClient] Game is starting!");
            if (connectionListener != null) {
                connectionListener.onGameStarted();
            }

        } else if (message.startsWith("END:")) {
            String winner = message.substring("END:".length());
            System.out.println("[GameClient] Game over! Winner: " + winner);
            if (connectionListener != null) {
                connectionListener.onGameEnded(winner);
            }

        } else {
            System.out.println("[GameClient] Unknown message: " + message);
        }
    }

    private void sendRaw(String raw) {
        if (out != null) {
            out.println(raw);
        }
    }

    public interface GameStateListener {
        void onGameStateReceived(String gameState);
    }

    public interface ConnectionListener {
        void onDisconnected(String reason);
        void onInfoReceived(String info);
        void onErrorReceived(String error);
        void onGameStarted();
        void onGameEnded(String winnerName);
    }
}