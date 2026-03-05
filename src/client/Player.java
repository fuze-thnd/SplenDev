package client;

import shared.*;
import shared.Gems.gemsColor;
import java.util.*;

public class Player {
    private String name;
    private int prestigePoints;
    private ArrayList<DevelopmentCards> developmentCards;
    private ArrayList<NobleCards> nobleCards;
    private HashMap<gemsColor, Integer> gems;
    
    public Player(String name) {
        setName(name);
        setPrestigePoints(0);
        developmentCards = new ArrayList<>();
        gems = new HashMap<>();
        gems.put(gemsColor.Black, 0);
        gems.put(gemsColor.Blue, 0);
        gems.put(gemsColor.Green, 0);
        gems.put(gemsColor.Red, 0);
        gems.put(gemsColor.White, 0);
        nobleCards = new ArrayList<>();
    }
    public void setName(String name) {this.name = name;}
    public String getName() {return this.name;}
    public void setPrestigePoints(int prestigePoint) {this.prestigePoints = prestigePoint;}
    public int getPrestigePoints() {return prestigePoints;}
    public ArrayList<DevelopmentCards> getDevelopmentCards() {return developmentCards;}
    public void addDevelopmentCards(DevelopmentCards developmentCard) {this.developmentCards.add(developmentCard);}
    public void removeDevelopmentCards(DevelopmentCards developmentCard) {
        if (developmentCards.contains(developmentCard)) {
            developmentCards.remove(developmentCard);
        } else {
            System.out.println("You don't have this card ID:" + developmentCard.getId());
        }
    }
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
    public void addNobleCards(NobleCards nobleCard) {this.nobleCards.add(nobleCard);}
}
