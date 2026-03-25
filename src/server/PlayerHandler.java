package server;

import shared.Gems;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class PlayerHandler implements Serializable {
    private String name;
    private transient RoomHandler currentRoom;
    private final transient Socket socket;
    private transient ObjectInputStream inFromClient;
    private transient ObjectOutputStream outToClient;
    private transient Thread readThread;
    private HashMap<Gems.gemsColor, Integer> gems;
    private boolean isRunning = false;
    private transient GameServer gs = GameServer.getInstance();

    public PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() throws IOException {
        isRunning = true;
        inFromClient = new ObjectInputStream(socket.getInputStream());
        outToClient = new ObjectOutputStream(socket.getOutputStream());

        startListeningThread();
    }

    private void startListeningThread(){
        readThread = new Thread(()->{
            try{
                while(isRunning){
                    Object objectFromClient = inFromClient.readObject();

                    if(objectFromClient instanceof String message){
                        handleCommandFromClient(message);
                    }
                }
            }catch (IOException | ClassNotFoundException e){
                error("Connection lost in read thread: " + e.getMessage());
                handleDisconnect();
            }
        });

        readThread.start();

    }

    public void sendRefreshRoomListCommand(){
        send("REFRESH_ROOM");
        send(gs.getRooms());
    }

    public void sendRefreshLobbyCommand(){
        send("REFRESH_LOBBY");
        send(this.currentRoom);
    }

    private void handleCommandFromClient(String command){
        String[] parts = command.split(":");

        switch (parts[0]){
            case "INIT":
                this.name = parts[1];
                send("CONNECTED");
                sendRefreshRoomListCommand();
                break;
            case "JOIN":
                joinRoom(parts[1]);
                break;
            case "CREATE_ROOM":
                RoomHandler room = GameServer.getInstance().createRoom(parts[1]);
                room.setOwner(this);
//                sendMessage("Room " + parts[1] + " created.");
                joinRoom(room.getCode());

                for(PlayerHandler p : gs.getPlayers()){
                    p.sendRefreshRoomListCommand();
                }
                break;
            case "ROOM_LIST":
                send(gs.getRooms());
                break;
            case "DISCONNECT_FROM_ROOM":
                disconnectFromRoom();
                break;
            case "DISCONNECT":
                disconnect();
                break;
        }
    }

    private void disconnectFromRoom(){
        if(currentRoom != null){
            currentRoom.removePlayer(this);
            for(PlayerHandler p : currentRoom.getPlayers()){
                p.send("REFRESH_LOBBY");
                p.send(currentRoom);
            }
            currentRoom.broadcastLobbyMessage(getName() + " has disconnected the room!");

            if(currentRoom.getOwner().equals(this)){
                currentRoom.kickEveryone();
            }
        }

        currentRoom = null;
    }

    private void handleDisconnect(){
        try{
            isRunning = false;
            if(currentRoom != null){
                currentRoom.removePlayer(this);
            }

            if(inFromClient != null) inFromClient.close();
            if (outToClient != null) outToClient.close();
            if (socket != null && !socket.isClosed()) socket.close();

            log("Player " + name + " disconnected.");
        }catch (IOException e){
            //
        }
    }

    public void send(Object object){
        try{
            outToClient.reset();
            outToClient.writeObject(object);
        }catch(IOException e){
            error("Failed to send object to " + this.name + ". Disconnecting...");
            handleDisconnect();
        }
    }

    public void sendMessage(String message){
        try{
            outToClient.writeObject("MESSAGE:"+message);
        }catch(IOException e){
            error("Failed to send message to " + this.name + ". Disconnecting...");
            handleDisconnect();
        }
    }

    public void joinRoom(String code){
        RoomHandler roomHandler = GameServer.getInstance().getRoom(code);

        if(roomHandler == null){
            sendMessage("Room with code " + code + " not found.");
        }else{
            if(roomHandler.addPlayer(this)){
                currentRoom = roomHandler;
//                sendMessage("Room with code " + code + " joined.");
                gs.log("Player " + name + " joined room " + currentRoom.getRoomName() + "." + "( " + currentRoom.getSize() + " / " + gs.getMaxPlayer() + " )");
                currentRoom.broadcastLobbyMessage(getName() + " has joined the room!");
            }
        }

        this.gems = new HashMap<>();
        for(Gems.gemsColor color : Gems.gemsColor.values()) {
            gems.put(color, 0);
        }

        send("JOIN_ROOM_SUCCESS");
        send(this.currentRoom);
        this.currentRoom.sendRefreshLobbyToEveryone();
    }

    public void disconnect(){
        if(currentRoom != null){
            currentRoom.removePlayer(this);
            sendMessage("Player " + this.name + " has disconnected.");
            gs.log("Player " + name + " disconnected room " + currentRoom.getRoomName() + "." + "( " + currentRoom.getSize() + " / " + gs.getMaxPlayer() + " )");
        }
    }

    public String getName(){
        return this.name;
    }

    private void error(String message){
        System.out.println("[ Player Handler Error ] " + message);
    }

    private void log(String message){
        System.out.println("[ Player Handler ] " + message);
    }
}
