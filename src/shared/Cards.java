package shared;

import client.Player;
import java.util.*;
import shared.Gems.gemsColor;

public abstract class Cards {
    private int id;
    private int prestigePoints;
    private HashMap<gemsColor, Integer> cost = new HashMap();
    
    public Cards(int id, int prestigePoints, HashMap cost) {
        this.setId(id);
        this.setPrestigePoints(prestigePoints);
        this.setCost(cost);
    }
    
    public void setId(int id) {this.id = id;}
    public void setId(String id) {setId(Integer.parseInt(id));} // overload
    public int getId() {return id;}
    public void setPrestigePoints(int points) {this.prestigePoints = points;}
    public void setPrestigePoints(String points) {setPrestigePoints(Integer.parseInt(points));} // overload
    public int getPrestigePoints() {return prestigePoints;}
    public void setCost(HashMap cost) {this.cost = cost;}
    public HashMap getCost() {return cost;}
    
    public abstract void addPlayerPrestigePoints(Player p);
    // need sell method
    
}
