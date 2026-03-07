package shared;

import client.Player;
import shared.Gems.gemsColor;
import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private int numberOfPlayers;
    
    private DevelopmentCards[][] cardsOnBoard;
    private Map<gemsColor, Integer> bankGems;
    
    private Stack<DevelopmentCards> developmentCardsLevel1;
    private Stack<DevelopmentCards> developmentCardsLevel2;
    private Stack<DevelopmentCards> developmentCardsLevel3;
    private ArrayList<NobleCards> nobleCardsOnBoard;
    
    public GameState() {
        players = new ArrayList<>();
        cardsOnBoard = new DevelopmentCards[3][4];
        bankGems = new HashMap<>();
        developmentCardsLevel1 = new Stack<>();
        developmentCardsLevel2 = new Stack<>();
        developmentCardsLevel3 = new Stack<>();
        nobleCardsOnBoard = new ArrayList<>();
        currentPlayerIndex = 0;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public DevelopmentCards[][] getCardsOnBoard() {
        return cardsOnBoard;
    }

    public Map<gemsColor, Integer> getBankGems() {
        return bankGems;
    }

    public Stack<DevelopmentCards> getDevelopmentCardsLevel1() {
        return developmentCardsLevel1;
    }

    public Stack<DevelopmentCards> getDevelopmentCardsLevel2() {
        return developmentCardsLevel2;
    }

    public Stack<DevelopmentCards> getDevelopmentCardsLevel3() {
        return developmentCardsLevel3;
    }

    public ArrayList<NobleCards> getNobleCardsOnBoard() {
        return nobleCardsOnBoard;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setCardsOnBoard(DevelopmentCards[][] cardsOnBoard) {
        this.cardsOnBoard = cardsOnBoard;
    }

    public void setBankGems(Map<gemsColor, Integer> bankGems) {
        this.bankGems = bankGems;
    }

    public void setDevelopmentCardsLevel1(Stack<DevelopmentCards> developmentCardsLevel1) {
        this.developmentCardsLevel1 = developmentCardsLevel1;
    }

    public void setDevelopmentCardsLevel2(Stack<DevelopmentCards> developmentCardsLevel2) {
        this.developmentCardsLevel2 = developmentCardsLevel2;
    }

    public void setDevelopmentCardsLevel3(Stack<DevelopmentCards> developmentCardsLevel3) {
        this.developmentCardsLevel3 = developmentCardsLevel3;
    }

    public void setNobleCardsOnBoard(ArrayList<NobleCards> nobleCardsOnBoard) {
        this.nobleCardsOnBoard = nobleCardsOnBoard;
    }
    
    
    
}
