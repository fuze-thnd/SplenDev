package client;

import shared.*;
import shared.Gems.gemsColor;
import java.util.*;

public class Player {
    private String name;
    private int scores;
    private ArrayList<DevelopmentCards> developmentCards;
    private HashMap<gemsColor, Integer> gems;
    
    public Player(String name) {
        setName(name);
        setScores(0);
        developmentCards = new ArrayList<>();
        gems = new HashMap<>();
        gems.put(gemsColor.Black, 0);
        gems.put(gemsColor.Blue, 0);
        gems.put(gemsColor.Green, 0);
        gems.put(gemsColor.Red, 0);
        gems.put(gemsColor.White, 0);
    }
    public void setName(String name) {this.name = name;}
    public String getName() {return this.name;}
    public void setScores(int scores) {this.scores = scores;}
    public int getScores() {return scores;}
    public void addDevelopmentCards(DevelopmentCards developmentCards) {this.developmentCards.add(developmentCards);}
    public ArrayList<DevelopmentCards> getDevelopmentCards() {return developmentCards;}
    public void addGems(gemsColor gems, int n) {this.gems.put(gems, this.gems.get(gems)+n);}
    public boolean removeGems(gemsColor gems, int n) {
        if (this.gems.get(gems)-n >= 0) {
            this.gems.put(gems, this.gems.get(gems)-n);
            return true;
        } else {
            System.out.println("Not enough Gems");
            return false;
        }
    }
    public HashMap<gemsColor, Integer> getGems() {return gems;}
}
