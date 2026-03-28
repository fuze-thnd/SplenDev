package server;

import client.Player;
import shared.NetworkMessage;

import java.io.*;
import java.net.Socket;

public class PlayerHandler implements Serializable {
    private Player player;
    private transient RoomHandler currentRoom;
    private final transient Socket socket;
    private transient ObjectInputStream inFromClient;
    private transient ObjectOutputStream outToClient;
    private transient Thread readThread;
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
                    } else if (objectFromClient instanceof NetworkMessage msg){
                        handleCommandFromClient(msg);
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
                player = new Player(parts[1]);
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
            case "START_GAME":
                currentRoom.startGame();
        }
    }

    // Overload
    private void handleCommandFromClient(NetworkMessage msg){
        if (currentRoom != null) {
            currentRoom.handleGameAction(this, msg);
        }
    }

    private void disconnectFromRoom(){
        if(currentRoom != null){
            currentRoom.removePlayer(this);
            for(PlayerHandler p : currentRoom.getPlayersHandler()){
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

            log("Player " + getName() + " disconnected.");
        }catch (IOException e){
            //
        }
    }

    public void send(Object object){
        try{
            outToClient.reset();
            outToClient.writeObject(object);
        }catch(IOException e){
            error("Failed to send object to " + getName() + ". Disconnecting...");
            handleDisconnect();
        }
    }

    public void sendMessage(String message){
        try{
            outToClient.writeObject("MESSAGE:"+message);
        }catch(IOException e){
            error("Failed to send message to " + getName() + ". Disconnecting...");
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
                gs.log("Player " + getName() + " joined room " + currentRoom.getRoomName() + "." + "( " + currentRoom.getSize() + " / " + gs.getMaxPlayer() + " )");
                currentRoom.broadcastLobbyMessage(getName() + " has joined the room!");
            }
        }

        send("JOIN_ROOM_SUCCESS");
        send(this.currentRoom);
        this.currentRoom.sendRefreshLobbyToEveryone();
    }

    public void disconnect(){
        if(currentRoom != null){
            currentRoom.removePlayer(this);
            sendMessage("Player " + getName() + " has disconnected.");
            gs.log("Player " + getName() + " disconnected room " + currentRoom.getRoomName() + "." + "( " + currentRoom.getSize() + " / " + gs.getMaxPlayer() + " )");
        }
    }

    public String getName(){
        return player.getName();
    }

    private void error(String message){
        System.out.println("[ Player Handler Error ] " + message);
    }

    private void log(String message){
        System.out.println("[ Player Handler ] " + message);
    }

    public Player getPlayer() {
        return player;
    }
}
