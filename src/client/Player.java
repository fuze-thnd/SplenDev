package client;

import shared.*;
import shared.Gems.gemsColor;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private String name;
    private int prestigePoints;
    private ArrayList<DevelopmentCards> developmentCards;
    private ArrayList<DevelopmentCards> reservedCards;
    private ArrayList<NobleCards> nobleCards;
    private HashMap<gemsColor, Integer> gems;
    private HashMap<gemsColor, Integer> bonusGems;

    
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
        gems.put(gemsColor.Gold, 0);
        bonusGems = new HashMap<>();
        bonusGems.put(gemsColor.Black, 0);
        bonusGems.put(gemsColor.Blue, 0);
        bonusGems.put(gemsColor.Green, 0);
        bonusGems.put(gemsColor.Red, 0);
        bonusGems.put(gemsColor.White, 0);
        nobleCards = new ArrayList<>();
        reservedCards = new ArrayList<>();
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
    public HashMap<gemsColor, Integer> getBonusGems() {return bonusGems;}
    public void addBonusGems(gemsColor g) {this.bonusGems.put(g, bonusGems.get(g)+1);}
    public ArrayList<DevelopmentCards> getReservedCard() {return reservedCards;}
    public void addReservedCard(DevelopmentCards card) {this.reservedCards.add(card);}
    public int getDevelopmentCardSize() {return this.developmentCards.size();}
}
