package client;

import client.ui.GameWindow;
import shared.NetworkMessage;
import java.io.*;
import java.net.Socket;
import server.RoomHandler;
import client.lobby.LobbyWindow;
import shared.GameState;

public class GameClient {
    // 🌟 1. สร้างตัวแปร static เพื่อเก็บ Client ตัวเดียวของระบบ
    private static GameClient instance;
    private LobbyWindow currentLobbyWindow;
    private GameWindow currentGameWindow;
    private GameController gameController;
    private GameState gameState;
    
    public void setLobbyWindow(LobbyWindow window) {
        this.currentLobbyWindow = window;
    }
    
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean connected = false;

    // 🌟 2. เมธอดสำหรับเรียกใช้ Client จากหน้าต่างไหนก็ได้
    public static GameClient getInstance() {
        if (instance == null) {
            instance = new GameClient();
        }
        return instance;
    }

    // เมธอดเชื่อมต่อ
    public void connect(String playerName) {
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;

            // ส่งชื่อไปหา Server ทันทีที่ต่อติด
            sendToServer("INIT:"+playerName);

            // (ส่วนของการรับข้อมูลจาก Server แนะนำให้ทำเป็น Thread แยกไว้ต่างหากเมื่อพร้อมครับ)
            System.out.println("Connected to server as: " + playerName);
            startListening();
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    
    private void startListening() {
        new Thread(() -> {
            try {
                while (connected) {
                    Object obj = in.readObject();
                    if (obj instanceof String message) {
                        handleServerMessage(message);
                    } else if (obj instanceof NetworkMessage msg) {
                        handleServerMessage(msg);
                    } else if (obj instanceof RoomHandler room) {
                        handleServerMessage(room);
                    } else {
                        System.out.println("[Server -> Client] ได้รับ Object ชนิด: " + obj.getClass().getSimpleName());
                    }
                }
            } catch (Exception e) {
                System.err.println("การเชื่อมต่อถูกตัดขาด: " + e.getMessage());
                connected = false;
            }
        }).start();
    }
    
    public void handleServerMessage(String str) throws IOException, ClassNotFoundException {
        System.out.println("[Server -> Client] " + str);

        if (str.equals("START_GAME")) {
            currentLobbyWindow.dispose();
            GameWindow gw = new  GameWindow();
            currentGameWindow = gw;
            gameController = new GameController(gw, this);
        }else if(str.equals("REFRESH_LOBBY")){
            RoomHandler r = (RoomHandler) in.readObject();
            System.out.println(r);
            handleServerMessage(r);
        }
    }
    
    public void handleServerMessage(NetworkMessage msg) {
        String command = msg.getCommand();
        System.out.println("[Server -> Client] command " + command);
        if (command.equals("UPDATE_GAME_STATE")) {
            gameState = (GameState) msg.getData();
            currentGameWindow.refreshUiLeftPanel(gameState.getNobleCardsOnBoard(),gameState.getCardsOnBoard());
            currentGameWindow.refreshUiCenterPanel(gameState.getBankGems());
            currentGameWindow.refreshUiRightPanel(gameState.getPlayers());
        } else if (command.equals("YOUR_TURN")) {
            gameController.onYourTurn();
        }
    }
    
    public void handleServerMessage(RoomHandler room) {
        if (currentLobbyWindow != null) {
            currentLobbyWindow.updateRoomData(room);
        }else{
            System.out.println("Current lobby is null");
        }
    }

    // เมธอดสำหรับส่งข้อมูล
    public void sendToServer(NetworkMessage msg) {
        if (connected && out != null) {
            try {
                out.reset();
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                System.err.println("Send Error: " + e.getMessage());
            }
        }
    }
    
    public void sendToServer(String msg) {
        if (connected && out != null) {
            try {
                out.reset();
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                System.err.println("Send Error: " + e.getMessage());
            }
        }
    }
    
    public GameState getGameState() {
        return gameState;
    }
}