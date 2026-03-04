package shared;

import client.Player;
import java.util.*;

public class NobleCards extends Cards {
    public NobleCards(int id, int prestigePoints, HashMap cost) {
        super(id, prestigePoints, cost);
    }
    
    @Override
    public void addPlayerPrestigePoints(Player p) {
        p.setPrestigePoints(p.getPrestigePoints() + this.getPrestigePoints());
    }
}
