package server;

import java.util.*;

import client.*;
import shared.*;
import shared.Gems.gemsColor;

public class GameManager {
    private Player[] players;
    private int currentPlayer;
    private DevelopmentCards[] cardsOnBoard = new DevelopmentCards[4];
    private Map<gemsColor, Integer> BankGems;
    public ArrayList<DevelopmentCards> developmentCardsLevel1;
    public ArrayList<DevelopmentCards> developmentCardsLevel2;
    public ArrayList<DevelopmentCards> developmentCardsLevel3;
    
    public GameManager() {
        CardLoader cardLoader = new CardLoader();
        developmentCardsLevel1 = cardLoader.getDevelopmentCardsLevel1();
        developmentCardsLevel2 = cardLoader.getDevelopmentCardsLevel2();
        developmentCardsLevel3 = cardLoader.getDevelopmentCardsLevel3();
        for (int i=0; i<40; i++) {
            System.out.println(developmentCardsLevel1.get(i).getId() +", "+ developmentCardsLevel1.get(i).getGemsColor() +", "+ developmentCardsLevel1.get(i).getPrestigePoints() + ", " + developmentCardsLevel1.get(i).getCost());
        }
        for (int i=0; i<30; i++) {
            System.out.println(developmentCardsLevel2.get(i).getId() +", "+ developmentCardsLevel2.get(i).getGemsColor() +", "+ developmentCardsLevel2.get(i).getPrestigePoints() + ", " + developmentCardsLevel2.get(i).getCost());
        }
        for (int i=0; i<20; i++) {
            System.out.println(developmentCardsLevel3.get(i).getId() +", "+ developmentCardsLevel3.get(i).getGemsColor() +", "+ developmentCardsLevel3.get(i).getPrestigePoints() + ", " + developmentCardsLevel3.get(i).getCost());
        }
    }
}
