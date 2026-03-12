package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class RoomHandler implements Serializable {
    private transient ArrayList<PlayerHandler> players;
    private String roomName;
    private final UUID uuid;

    public RoomHandler(String roomName) {
        this.players = new ArrayList<PlayerHandler>();
        this.uuid = UUID.randomUUID();
        this.roomName = roomName;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public void addPlayer(PlayerHandler p){
        this.players.add(p);
    }

    public void removePlayer(PlayerHandler p){
        this.players.remove(p);
    }

    public int getSize(){
        return this.players.size();
    }

    public void broadcastMessage(String message){
        for(PlayerHandler p : this.players){
            p.sendMessage(message);
        }
    }

    public ArrayList<PlayerHandler> getPlayers(){
        return players;
    }
}
