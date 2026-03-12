package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerHandler implements Serializable {
    private String name;
    private RoomHandler currentRoom;
    private final Socket socket;
    private ObjectInputStream inFromClient;
    private ObjectOutputStream outToClient;
    private Thread readThread, writeThread;
    private boolean isRunning = false;
    private GameServer gs = GameServer.getInstance();

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

    private void handleCommandFromClient(String command){
        String[] parts = command.split(":");

        switch (parts[0]){
            case "INIT":
                this.name = parts[1];
                break;
            case "JOIN":
                joinRoom(parts[1]);
                break;
            case "CREATE_ROOM":
                RoomHandler room = GameServer.getInstance().createRoom(parts[1]);
                sendMessage("Room " + parts[1] + " created.");
                joinRoom(room.getUUID().toString());
                break;
            case "ROOM_LIST":
                send(gs.getRooms());
                break;
        }
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

    public void joinRoom(String uuidString){
        UUID uuid = UUID.fromString(uuidString);
        RoomHandler roomHandler = GameServer.getInstance().getRoom(uuid);

        if(roomHandler == null){
            sendMessage("Room " + uuid + " not found.");
        }else{
            roomHandler.addPlayer(this);
            currentRoom = roomHandler;
            sendMessage("Room " + uuid + " joined.");
            gs.log("Player " + name + " joined room " + currentRoom.getRoomName() + "." + "( " + currentRoom.getSize() + " / " + gs.getMaxPlayer() + " )");
        }
    }

    private void error(String message){
        System.out.println("[ Player Handler Error ] " + message);
    }

    private void log(String message){
        System.out.println("[ Player Handler ] " + message);
    }
}
