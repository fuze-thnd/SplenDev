package shared;

import java.io.*;

public class DevelopmentCards extends Cards {
    private int gemsColor;
    public DevelopmentCards(int id, int prestigePoints, int[] cost, int gemsColor) {
        super(id, prestigePoints, cost);
        setGemsColor(gemsColor);
    }
    
    public void setGemsColor(int gemsColor) {this.gemsColor = gemsColor;}
    public int getGemsColor() {return gemsColor;}
}
