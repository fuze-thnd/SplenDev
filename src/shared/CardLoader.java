package shared;

import java.io.*;
import java.util.*;
import shared.Gems.gemsColor;

public class CardLoader {
    private ArrayList<DevelopmentCards> level1;
    private ArrayList<DevelopmentCards> level2;
    private ArrayList<DevelopmentCards> level3;
    
    public ArrayList<DevelopmentCards> getLevel1() {
        level1 = new ArrayList<DevelopmentCards>();
        try (BufferedReader br = new BufferedReader(new FileReader("AllCards.csv"))) {
            String line;
            line = br.readLine(); // skip the Header line 
            
            for (int i=1; i<=40; i++) {
                line = br.readLine();
                String[] data = line.split(",");
                
                int prestigePoints = Integer.parseInt(data[2]);
                HashMap cost = new HashMap();
                cost.put(gemsColor.Black, Integer.valueOf(data[3]));
                cost.put(gemsColor.Blue, Integer.valueOf(data[4]));
                cost.put(gemsColor.Green, Integer.valueOf(data[5]));
                cost.put(gemsColor.Red, Integer.valueOf(data[6]));
                cost.put(gemsColor.White, Integer.valueOf(data[7]));
                gemsColor bonusGemsColor = gemsColor.valueOf(data[1]);
                level1.add(new DevelopmentCards(i, prestigePoints, cost, bonusGemsColor));
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return level1;
    }
}
