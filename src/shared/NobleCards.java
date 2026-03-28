package shared;

import client.Player;
import java.util.*;

public class NobleCards extends Cards {
    private String imageName;

    public NobleCards(int id, int prestigePoints, HashMap cost, String imageName) {
        super(id, prestigePoints, cost);
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public void addPlayerPrestigePoints(Player p) {
        p.setPrestigePoints(p.getPrestigePoints() + this.getPrestigePoints());
    }
}
