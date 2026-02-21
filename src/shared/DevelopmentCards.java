package shared;

import shared.Gems.gemsColor;
import java.util.*;

public class DevelopmentCards extends Cards {
    private gemsColor bonusGemsColor;
    public DevelopmentCards(int id, int prestigePoints, HashMap cost, gemsColor bonusGemsColor) {
        super(id, prestigePoints, cost);
        setGemsColor(bonusGemsColor);
    }
    
    public void setGemsColor(gemsColor gemsColor) {this.bonusGemsColor = gemsColor;}
    public gemsColor getGemsColor() {return bonusGemsColor;}
}
