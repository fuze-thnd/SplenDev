package server;

import client.Player;
import shared.*;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.*;

public class RoomHandler implements Serializable {
    private ArrayList<PlayerHandler> playersHandler;
    private String roomName;
    private final String code;
    private ServerGameState gameState;
    private PlayerHandler owner;
    private transient GameManager gameManager;
    private ObjectInputStream inFromClient;
    private Thread listeningThread;

    public RoomHandler(String roomName) {
        this.playersHandler = new ArrayList<>();
        this.code = generateCode();
        this.roomName = roomName;
        this.gameState = ServerGameState.WAITING;
    }

    public ArrayList<NobleCards> getShowNobleCard() {
        return gameManager.state.getNobleCardsOnBoard();
    }

    public DevelopmentCards[][] getCardOnBoard() {
        return gameManager.state.getCardsOnBoard();
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
        for(PlayerHandler player : this.playersHandler){
            player.sendRefreshLobbyCommand();
        }
    }

    public void kickEveryone(){
        for(PlayerHandler player : playersHandler){
            player.send("KICK");
        }

        GameServer.getInstance().getRooms().remove(this);
        GameServer.getInstance().sendRefreshRoomListToEveryone();

        this.playersHandler.clear();
    }

    public synchronized boolean addPlayer(PlayerHandler p){
        if(playersHandler.size() >= GameServer.getInstance().getMaxPlayer()){
            p.sendMessage("Failed to join room, room is full!");
            return false;
        }

        this.playersHandler.add(p);

        if(playersHandler.size() >= GameServer.getInstance().getMaxPlayer()){
            startGame();
        }

        return true;
    }

    public void startGame(){
        new Thread(() -> {
            try {
                Thread.sleep(500);
                ArrayList<Player> players = new ArrayList<>();
                for (PlayerHandler playerHandler : playersHandler) {
                    players.add(playerHandler.getPlayer());
                }
                this.gameManager = new GameManager(players);

                for(int i = 3;i > 0;i--){
                    broadcastLobbyMessage("Game is starting in " + i);
                    Thread.sleep(1000);
                }

                setGameState(ServerGameState.STARTING);
                sendRefreshLobbyToEveryone();
                sendCommandToEveryone("START_GAME");
                //broadcastGameState();

                broadcastGameState();
                notifyCurrentTurn();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public PlayerHandler getPlayerHandler(Player p) {
        for (PlayerHandler ph : playersHandler) {
            if (ph.getPlayer().equals(p)) {
                return ph;
            }
        }
        return null;
    }

    public synchronized void handleGameAction(PlayerHandler sender, NetworkMessage msg) {
        boolean isActionSuccess = gameManager.processAction(sender.getPlayer(), msg);

        if (isActionSuccess){
            broadcastGameState();

            if (gameManager.checkWin(sender.getPlayer())) {
                sendCommandToEveryone(new NetworkMessage("GAME_OVER", gameManager.getWinner().getName()));
                setGameState(ServerGameState.ENDING);
            } else {
                notifyCurrentTurn();
            }
        } else {
            notifyCurrentTurn();
        }
    }

    public void broadcastGameState() {
        sendCommandToEveryone(new NetworkMessage("UPDATE_GAME_STATE", gameManager.state));
    }

    public void notifyCurrentTurn() {
        int turnIndex = gameManager.state.getCurrentPlayerIndex();
        Player nextPlayer = gameManager.state.getPlayers().get(turnIndex);
        PlayerHandler nextPlayerHandler = getPlayerHandler(nextPlayer);
        nextPlayerHandler.send(new NetworkMessage("YOUR_TURN"));
    }

    public void sendCommandToEveryone(Object object){
        for(PlayerHandler player : playersHandler){
            player.send(object);
        }
    }

    public void removePlayer(PlayerHandler p){
        Iterator<PlayerHandler> iterator = this.playersHandler.iterator();
        while (iterator.hasNext()) {
            server.PlayerHandler p1 = iterator.next();
            if (p1.equals(p)) {
                iterator.remove();
            }
        }
    }

    public int getSize(){
        return this.playersHandler.size();
    }

    public synchronized void broadcastLobbyMessage(String message){
        for(PlayerHandler p : this.playersHandler){
            p.send("LOBBY_LOG_MESSAGE:" + message);
        }
    }
    public void broadcastLobbyMessage(String message, PlayerHandler except){
        for(PlayerHandler p : this.playersHandler){
            if(p.equals(except)) continue;

            p.send("LOBBY_LOG_MESSAGE:" + message);
        }
    }

    public void broadcastMessage(String message) {
        for (PlayerHandler p : this.playersHandler) {
            p.sendMessage(message);
        }
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

    public ArrayList<PlayerHandler> getPlayersHandler(){
        return playersHandler;
    }
}
