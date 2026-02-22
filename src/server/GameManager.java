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
    public ArrayList<DevelopmentCards> level2Cards;
    public ArrayList<DevelopmentCards> level3Cards;
    
    public GameManager() {
        CardLoader cardLoader = new CardLoader();
        level1Cards = cardLoader.getLevel1();
        level2Cards = cardLoader.getLevel2();
        level3Cards = cardLoader.getLevel3();
        for (int i=0; i<40; i++) {
            System.out.println(level1Cards.get(i).getId() +", "+ level1Cards.get(i).getGemsColor() +", "+ level1Cards.get(i).getPrestigePoints() + ", " + level1Cards.get(i).getCost());
        }
        for (int i=0; i<30; i++) {
            System.out.println(level2Cards.get(i).getId() +", "+ level2Cards.get(i).getGemsColor() +", "+ level2Cards.get(i).getPrestigePoints() + ", " + level2Cards.get(i).getCost());
        }
        for (int i=0; i<20; i++) {
            System.out.println(level3Cards.get(i).getId() +", "+ level3Cards.get(i).getGemsColor() +", "+ level3Cards.get(i).getPrestigePoints() + ", " + level3Cards.get(i).getCost());
        }
    }
}
