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
                
                level1.add(getDevelopmentCardsData(i, line));
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return level1;
    }
    
    public ArrayList<DevelopmentCards> getLevel2() {
        level2 = new ArrayList<DevelopmentCards>();
        try (BufferedReader br = new BufferedReader(new FileReader("AllCards.csv"))) {
            String line;
            line = br.readLine(); // skip the Header line
            // skip the level1 cards line
            for (int i=1; i<=40; i++)
                line = br.readLine();
            for (int i=41; i<=70; i++) {
                line = br.readLine();
                
                level2.add(getDevelopmentCardsData(i, line));
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return level2;
    }
    
    public ArrayList<DevelopmentCards> getLevel3() {
        level3 = new ArrayList<DevelopmentCards>();
        try (BufferedReader br = new BufferedReader(new FileReader("AllCards.csv"))) {
            String line;
            line = br.readLine(); // skip the Header line
            // skip the level1 and level2 cards line
            for (int i=1; i<=70; i++)
                line = br.readLine();
            for (int i=71; i<=90; i++) {
                line = br.readLine();
                
                level3.add(getDevelopmentCardsData(i, line));
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return level3;
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
}
