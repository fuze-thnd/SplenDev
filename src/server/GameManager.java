package server;

import java.util.*;

import client.Player;
import shared.*;

public class GameManager {
    private Player[] players;
    private int currentPlayer;
    private Cards[] cardsOnBoard;
    private Map<String, Integer> BankGems;
    public DevelopmentCards[] level1Cards;
    public DevelopmentCards[] level2Cards;
    
    public GameManager() {
        CardLoader cardLoader = new CardLoader();
        level1Cards = cardLoader.getLevel1();
        for (int i=0; i<40; i++) {
            System.out.println(level1Cards[i].getId() +", "+ level1Cards[i].getPrestigePoints() +", "+ level1Cards[i].getGemsColor());
        }
//        for (int i=0; i<30; i++) {
//            System.out.println(level2Cards[i].getId() +", "+ level2Cards[i].getPrestigePoints() +", "+ level2Cards[i].getCost()[0]);
//        }
    }
}
