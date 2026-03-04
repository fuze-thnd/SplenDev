package shared;

import client.Player;
import shared.Gems.gemsColor;
import java.util.*;

public class DevelopmentCards extends Cards implements Sacrificable {
    private gemsColor bonusGemsColor;
    public DevelopmentCards(int id, int prestigePoints, HashMap cost, gemsColor bonusGemsColor) {
        super(id, prestigePoints, cost);
        setGemsColor(bonusGemsColor);
    }
    
    public void setGemsColor(gemsColor gemsColor) {this.bonusGemsColor = gemsColor;}
    public gemsColor getGemsColor() {return bonusGemsColor;}
    
    @Override
    public void addPlayerPrestigePoints(Player p) {
        p.setPrestigePoints(p.getPrestigePoints() + this.getPrestigePoints());
    }
    @Override
    public int getDropRefund() {return Math.floorDiv(getPrestigePoints(), 2);}
    
}
