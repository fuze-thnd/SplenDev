package shared;

import java.io.*;
import java.util.*;

public class CardLoader {
    private DevelopmentCards[] level1;
    private DevelopmentCards[] level2;
    private DevelopmentCards[] level3;
    
    public DevelopmentCards[] getLevel1() {
        level1 = new DevelopmentCards[40];
        try (BufferedReader br = new BufferedReader(new FileReader("AllCards.csv"))) {
            String line;
            line = br.readLine(); // skip the Header line 
            
            for (int i=1; i<=40; i++) {
                line = br.readLine();
                String[] data = line.split(",");
                
                int prestigePoints = Integer.parseInt(data[2]);
                int[] cost = new int[5];
                cost[0] = Integer.parseInt(data[3]);
                cost[1] = Integer.parseInt(data[4]);
                cost[2] = Integer.parseInt(data[5]);
                cost[3] = Integer.parseInt(data[6]);
                cost[4] = Integer.parseInt(data[7]);
                int gemsColor = Gems.gemsColorIndex.valueOf(data[1]).ordinal();
                level1[i-1] = new DevelopmentCards(i, prestigePoints, cost, gemsColor);
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return level1;
    }
}
