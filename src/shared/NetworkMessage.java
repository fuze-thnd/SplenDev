package shared;

import java.io.Serializable;

public class NetworkMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum MessageType {
        INIT, JOIN, CREATE_ROOM, START_GAME, ACTION,
        CONNECTED, UPDATE_STATE, ERROR_MESSAGE, INFO_MESSAGE
    }

    private MessageType type;
    private GameState gameState
    private String textContent;
    private String playerName;

    public NetworkMessage(MessageType type) {
        this.type = type;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public MessageType getType() { return type; }
    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

}