package shared;

public class Cards {
    private int id;
    private int prestigePoints;
    private int[] cost = new int[5];  // black blue green red white in sequence
    
    public Cards(int id, int prestigePoints, int[] cost) {
        this.setId(id);
        this.setPrestigePoints(prestigePoints);
        this.setCost(cost[0], cost[1], cost[2], cost[3], cost[4]);
    }
    
    public void setId(int id) {this.id = id;}
    public void setId(String id) {setId(Integer.parseInt(id));} // overload
    public int getId() {return id;}
    public void setPrestigePoints(int points) {this.prestigePoints = points;}
    public void setPrestigePoints(String points) {setPrestigePoints(Integer.parseInt(points));} // overload
    public int getPrestigePoints() {return prestigePoints;}
    public void setCost(int black, int blue, int green, int red, int white) {
        this.cost[0] = black;
        this.cost[1] = blue;
        this.cost[2] = green;
        this.cost[3] = red;
        this.cost[4] = white;
    }
    public int[] getCost() {return cost;}
    
    // need sell method
    
}
