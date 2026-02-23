package shared;

import java.io.*;
import java.util.*;
import shared.Gems.gemsColor;

public class CardLoader {
    private ArrayList<DevelopmentCards> level1;
    private ArrayList<DevelopmentCards> level2;
    private ArrayList<DevelopmentCards> level3;
    
    public CardLoader() {
        level1 = new ArrayList<>();
        level2 = new ArrayList<>();
        level3 = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("AllCards.csv"))) {
            String line;
            br.readLine(); // skip the Header line
            
            for (int i=1; i<=90; i++) {
                line = br.readLine();
                int level = Integer.parseInt(line.split(",")[0]);
                DevelopmentCards developmentCards = getDevelopmentCardsData(i, line);
                if (level == 1) {
                    level1.add(developmentCards);
                } else if (level == 2) {
                    level2.add(developmentCards);
                } else if (level == 3) {
                    level3.add(developmentCards);
                }
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public DevelopmentCards getDevelopmentCardsData(int id, String line) {
        String[] data = line.split(",");
        
        int prestigePoints = Integer.parseInt(data[2]);
        HashMap<gemsColor, Integer> cost = new HashMap<>();
        cost.put(gemsColor.Black, Integer.valueOf(data[3]));
        cost.put(gemsColor.Blue, Integer.valueOf(data[4]));
        cost.put(gemsColor.Green, Integer.valueOf(data[5]));
        cost.put(gemsColor.Red, Integer.valueOf(data[6]));
        cost.put(gemsColor.White, Integer.valueOf(data[7]));
        gemsColor bonusGemsColor = gemsColor.valueOf(data[1]);
        return new DevelopmentCards(id, prestigePoints, cost, bonusGemsColor);
    }
    
    public ArrayList<DevelopmentCards> getDevelopmentCardsLevel1() {return level1;}
    public ArrayList<DevelopmentCards> getDevelopmentCardsLevel2() {return level2;}
    public ArrayList<DevelopmentCards> getDevelopmentCardsLevel3() {return level3;}
    
}
