package server;

import client.Player;
import shared.CardLoader;
import shared.DevelopmentCards;
import shared.Gems;
import shared.NobleCards;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomHandler implements Serializable {
    private ArrayList<PlayerHandler> players;
    private String roomName;
    private final String code;
    private ServerGameState gameState;
    private HashMap<Gems.gemsColor, Integer> gems;
    private CardLoader cardLoader;
    private ArrayList<DevelopmentCards> showCardsLv1;
    private ArrayList<DevelopmentCards> showCardsLv2;
    private ArrayList<DevelopmentCards> showCardsLv3;
    private ArrayList<NobleCards> showNobleCard;
    private PlayerHandler owner;

    public RoomHandler(String roomName) {
        this.players = new ArrayList<>();
        this.code = generateCode();
        this.roomName = roomName;
        this.gameState = ServerGameState.WAITING;
        this.gems = new HashMap<>();
        this.cardLoader = new CardLoader();
        this.showCardsLv1 = new ArrayList<>();
        this.showCardsLv2 = new ArrayList<>();
        this.showCardsLv3 = new ArrayList<>();
        this.showNobleCard = new ArrayList<>();

        for(int i = 0;i < 4;i++){
            showCardsLv1.add(this.cardLoader.getDevelopmentCardsLevel1().removeLast());
            showCardsLv2.add(this.cardLoader.getDevelopmentCardsLevel2().removeLast());
            showCardsLv3.add(this.cardLoader.getDevelopmentCardsLevel3().removeLast());
        }

        for(int i = 0;i < 5;i++){
            showNobleCard.add(this.cardLoader.getNobleCards().removeLast());
        }

        for(Gems.gemsColor color : Gems.gemsColor.values()) {
            gems.put(color, 7);
        }
    }

    public ArrayList<NobleCards> getShowNobleCard() {
        return showNobleCard;
    }

    public ArrayList<DevelopmentCards> getShowCardsLv1() {
        return showCardsLv1;
    }

    public ArrayList<DevelopmentCards> getShowCardsLv2() {
        return showCardsLv2;
    }

    public ArrayList<DevelopmentCards> getShowCardsLv3() {
        return showCardsLv3;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public PlayerHandler getOwner() {
        return owner;
    }

    public void setOwner(PlayerHandler owner) {
        this.owner = owner;
    }

    public void sendRefreshLobbyToEveryone(){
        for(PlayerHandler player : this.players){
            player.sendRefreshLobbyCommand();
        }
    }

    public void kickEveryone(){
        for(PlayerHandler player : players){
            player.send("KICK");
        }

        GameServer.getInstance().getRooms().remove(this);
        GameServer.getInstance().sendRefreshRoomListToEveryone();

        this.players.clear();
    }

    public synchronized boolean addPlayer(PlayerHandler p){
        if(players.size() >= GameServer.getInstance().getMaxPlayer()){
            p.sendMessage("Failed to join room, room is full!");
            return false;
        }

        this.players.add(p);

        if(players.size() >= GameServer.getInstance().getMaxPlayer()){
            startGame();
        }

        return true;
    }

    public void startGame(){
        new Thread(() -> {
            try {
                Thread.sleep(500);

                for(int i = 3;i > 0;i--){
                    broadcastLobbyMessage("Game is starting in " + i);
                    Thread.sleep(1000);
                }

                setGameState(ServerGameState.STARTING);
                sendRefreshLobbyToEveryone();
                sendCommandToEveryone("START_GAME");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void sendCommandToEveryone(Object object){
        for(PlayerHandler player : players){
            player.send(object);
        }
    }

    public void removePlayer(PlayerHandler p){
        Iterator<PlayerHandler> iterator = this.players.iterator();
        while (iterator.hasNext()) {
            server.PlayerHandler p1 = iterator.next();
            if (p1.equals(p)) {
                iterator.remove();
            }
        }
    }

    public int getSize(){
        return this.players.size();
    }

    public synchronized void broadcastLobbyMessage(String message){
        for(PlayerHandler p : this.players){
            p.send("LOBBY_LOG_MESSAGE:" + message);
        }
    }
    public void broadcastLobbyMessage(String message, PlayerHandler except){
        for(PlayerHandler p : this.players){
            if(p.equals(except)) continue;

            p.send("LOBBY_LOG_MESSAGE:" + message);
        }
    }

    public void broadcastMessage(String message){
        for(PlayerHandler p : this.players){
            p.sendMessage(message);
        }
    }

    public HashMap<Gems.gemsColor, Integer> getGems(){
        return this.gems;
    }

    public void setGems(HashMap<Gems.gemsColor, Integer> gems){
        this.gems = gems;
    }

    public String getCode(){
        return this.code;
    }

    public void setGameState(ServerGameState gameState){
        this.gameState = gameState;
    }

    public ServerGameState getGameState(){
        return this.gameState;
    }

    private String generateCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        boolean isUnique = false;
        Random random = new Random();

        while(!isUnique){
            for(int i = 0; i < 6; i++){
                code.append(characters.charAt(random.nextInt(characters.length())));
            }

            isUnique = true;

            for(RoomHandler room : GameServer.getInstance().getRooms()){
                if(room.getCode().equals(code.toString())){
                    isUnique = false;
                    break;
                }
            }
        }

        return code.toString();
    }

    public ArrayList<PlayerHandler> getPlayers(){
        return players;
    }
}
