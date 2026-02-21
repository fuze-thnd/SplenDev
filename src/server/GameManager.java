package server;

import java.util.*;

import client.Player;
import shared.*;
import shared.Gems.gemsColor;

public class GameManager {
    private Player[] players;
    private int currentPlayer;
    private DevelopmentCards[] cardsOnBoard = new DevelopmentCards[4];
    private Map<gemsColor, Integer> BankGems;
    public ArrayList<DevelopmentCards> level1Cards;
    
    public GameManager() {
        CardLoader cardLoader = new CardLoader();
        level1Cards = cardLoader.getLevel1();
        for (int i=0; i<40; i++) {
            System.out.println(level1Cards.get(i).getId() +", "+ level1Cards.get(i).getGemsColor() +", "+ level1Cards.get(i).getPrestigePoints());
        }
    }
}
