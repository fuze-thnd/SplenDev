package client;

import java.io.*;
import java.net.*;
import shared.NetworkMessage;
import shared.GameState;

public class GameClient {

    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final int DEFAULT_PORT = 12345;

    private final String host;
    private final int port;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
    
    public void sendCommand(String cmd) {
        sendRaw(cmd);
    }
    public void connect(String playerName) throws IOException {
        this.playerName = playerName;

        socket = new Socket(host, port);
        out  = new ObjectOutputStream(socket.getOutputStream());
        in     = new ObjectInputStream(socket.getInputStream());

        connected = true;

        sendRaw("INIT:" + playerName);

        Thread receiveThread = new Thread(this::receiveLoop, "ClientReceiver");
        receiveThread.setDaemon(true);
        receiveThread.start();

        System.out.println("[GameClient] Connected to " + host + ":" + port+ " as \"" + playerName + "\"");
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
            while (connected) {
                Object obj = in.readObject(); // อ่าน Object โดยตรง
                handleServerObject(obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            if (connected) {
                System.err.println("[GameClient] Connection lost: " + e.getMessage());
                connected = false;
                if (connectionListener != null) {
                    connectionListener.onDisconnected("Connection lost: " + e.getMessage());
                }
            }
        }
    }

   private void handleServerObject(Object obj) {
    if (obj instanceof GameState) {
        if (gameStateListener != null) {
            gameStateListener.onGameStateReceived((GameState) obj);
        }
    } else if (obj instanceof String) {
        handleStringMessage((String) obj);
    }
}
    private void handleStringMessage(String message) {
            if (message.startsWith("INFO:")) {
                String info = message.substring("INFO:".length());
                if (connectionListener != null) {
                    connectionListener.onInfoReceived(info);
                }
                
            } else if (message.startsWith("ERROR:")) {
                String error = message.substring("ERROR:".length());
                if (connectionListener != null) {
                    connectionListener.onErrorReceived(error);
                }
                
            } else if (message.equals("START")) {
                System.out.println("[GameClient] Game is starting!");
                if (connectionListener != null) {
                    connectionListener.onGameStarted();
                }
            } else if (message.equals("END:")) {
                String end = message.substring("END:".length());
                System.out.println("[GameClient] Game is starting!");
                if (connectionListener != null) {
                    connectionListener.onGameEnded(end);
                }
            }
        }
    private void sendRaw(Object obj) {
        try {
             if (out != null) {
                  out.writeObject(obj);
                  out.flush();
                  out.reset();
            }
        }
        catch (IOException e) {
            System.err.println("[GameClient] Send Error: " + e.getMessage());
        }
 
    }

    public interface GameStateListener {
        void onGameStateReceived(GameState gameState);
    }

    public interface ConnectionListener {
        void onDisconnected(String reason);
        void onInfoReceived(String info);
        void onErrorReceived(String error);
        void onGameStarted();
        void onGameEnded(String winnerName);
    }
}