package server;

import java.util.*;

import client.*;
import shared.*;
import shared.Gems.gemsColor;

public class GameManager {
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private int numberOfPlayers;
    
    private DevelopmentCards[][] cardsOnBoard = new DevelopmentCards[3][4];
    private Map<gemsColor, Integer> bankGems;
    
    public Stack<DevelopmentCards> developmentCardsLevel1;
    public Stack<DevelopmentCards> developmentCardsLevel2;
    public Stack<DevelopmentCards> developmentCardsLevel3;
    public ArrayList<NobleCards> nobleCards;
    
    public GameManager(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        setupGame();
    }
    
    public void setupGame() {
        CardLoader cardLoader = new CardLoader();
        // load all development cards and shuffle
        developmentCardsLevel1 = cardLoader.getDevelopmentCardsLevel1();
        developmentCardsLevel2 = cardLoader.getDevelopmentCardsLevel2();
        developmentCardsLevel3 = cardLoader.getDevelopmentCardsLevel3();
        Collections.shuffle(developmentCardsLevel1);
        Collections.shuffle(developmentCardsLevel2);
        Collections.shuffle(developmentCardsLevel3);
        // load all noble cards and shuffle
        nobleCards = cardLoader.getNobleCards();
        Collections.shuffle(nobleCards);
        
        refillDevelopmentCards();
        
        // random the noble card and assign from rules
        numberOfPlayers = players.size();
        nobleCards = new ArrayList<>(nobleCards.subList(0, numberOfPlayers+1));
        // make the bank get the correct amount of gems
        bankGems = new HashMap<>();
        int n = 0;
        if (numberOfPlayers == 2) n = 4;
        else if (numberOfPlayers == 3) n = 5;
        else if (numberOfPlayers == 4) n = 7;
        bankGems.put(gemsColor.Black, n);
        bankGems.put(gemsColor.Blue, n);
        bankGems.put(gemsColor.Green, n);
        bankGems.put(gemsColor.Red, n);
        bankGems.put(gemsColor.White, n);
        bankGems.put(gemsColor.Gold, 5); // AI is always 5 gems
        
        // random the first player to start the game
        Collections.shuffle(players);
    }
    
    public void refillDevelopmentCards() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // skip if the slot is already has a card
                if (cardsOnBoard[i][j] != null)
                    continue;
                // add the slot that has no card
                if (i == 0)
                    cardsOnBoard[i][j] = developmentCardsLevel1.pop();
                else if (i == 1)
                    cardsOnBoard[i][j] = developmentCardsLevel2.pop();
                else if (i == 2)
                    cardsOnBoard[i][j] = developmentCardsLevel3.pop();
            }
        }
    }
}
