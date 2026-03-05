package shared;

public class Gems {
    public static enum gemsColor {Black, Blue, Green, Red, White, Gold};
    public gemsColor color;
    
    public Gems(gemsColor color) {
        setColor(color);
    }
    
    private void setColor(gemsColor color) {this.color = color;}
}
