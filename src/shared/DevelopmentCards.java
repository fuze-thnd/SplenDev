package shared;

import client.Player;
import shared.Gems.gemsColor;

import java.io.Serializable;
import java.util.*;

public class DevelopmentCards extends Cards implements Sacrificable, Serializable {
    private gemsColor bonusGemsColor;
    private String imageName;

    public DevelopmentCards(int id, int prestigePoints, HashMap cost, gemsColor bonusGemsColor, String imageName) {
        super(id, prestigePoints, cost);
        setGemsColor(bonusGemsColor);
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
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
