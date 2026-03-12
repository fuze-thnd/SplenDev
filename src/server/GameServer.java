package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class GameServer {
    public static GameServer instance;
    private ArrayList<RoomHandler> rooms;
    private final int port = 8080;
    private final int maxRoom = 4;
    private final int maxPlayer = 4;
    private ServerSocket serverSocket;
    private boolean isRunning = false;

    private GameServer() {
        this.rooms = new ArrayList<RoomHandler>();
        log("Game server instance created.");
    }

    public void start(){
        if(isRunning) return;

        log("Starting Game Server.");
        log("Server is running on port " + port);
        isRunning = true;

        new Thread(() -> {
            try{
                serverSocket = new ServerSocket(port);

                while(isRunning){
                    Socket clientSocket = serverSocket.accept();

                    PlayerHandler playerHandler = new PlayerHandler(clientSocket);
                    playerHandler.start();
                }
            }catch (IOException e){
                error(e.toString());
            }finally {
                log("Game server stopped.");
                stop();
            }
        }).start();
    }

    public void stop(){
        log("Game server stopped.");
        isRunning = false;

        try{
            if(serverSocket != null) serverSocket.close();
        }catch (IOException e){
            error("Error closing server socket: " + e.toString());
        }
    }

    public ArrayList<RoomHandler> getRooms() {
        return rooms;
    }

    public RoomHandler getRoom(UUID uuid){
        for (RoomHandler room : rooms) {
            if(room.getUUID().equals(uuid)){
                return room;
            }
        }

        return null;
    }

    public RoomHandler createRoom(String roomName){
        RoomHandler room = new RoomHandler(roomName);
        rooms.add(room);

        log("Room created, Room Name: " +  roomName + " UUID: " + room.getUUID());
        return room;
    }

    public static GameServer getInstance(){
        if (instance == null) {
            instance = new GameServer();
        }

        return instance;
    }

    public void log(String message){
        System.out.println("[ Game Server ] " + message);
    }

    private void error(String message){
        System.out.println("[ Game Server Error ] " + message);
    }

    public int getMaxPlayer(){
        return this.maxPlayer;
    }
}
