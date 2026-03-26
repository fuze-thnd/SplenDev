package shared;

import java.io.*;
import java.util.*;
import shared.Gems.gemsColor;

public class CardLoader implements Serializable{
    private Stack<DevelopmentCards> level1;
    private Stack<DevelopmentCards> level2;
    private Stack<DevelopmentCards> level3;
    private ArrayList<NobleCards> noble;
    
    public CardLoader() {
        level1 = new Stack<>();
        level2 = new Stack<>();
        level3 = new Stack<>();
        noble = new ArrayList<>();
        
        // development cards loader
        try (BufferedReader br = new BufferedReader(new FileReader("DevelopmentCards.csv"))) {
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
        
        
        // noble cards loader
        try (BufferedReader br = new BufferedReader(new FileReader("NobleCards.csv"))) {
            String line;
            br.readLine(); // skip the Header line
            
            for (int i=1; i<=10; i++) {
                line = br.readLine();
                int level = Integer.parseInt(line.split(",")[0]);
                noble.add(getNobleCardsData(i, line));
            }
        } catch (FileNotFoundException fileLost) {
            System.out.println(fileLost);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        this.shuffleAll();
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
        String imageName = data[8];
        gemsColor bonusGemsColor = gemsColor.valueOf(data[1]);
        return new DevelopmentCards(id, prestigePoints, cost, bonusGemsColor, imageName);
    }
    
    public NobleCards getNobleCardsData(int id, String line) {
        String[] data = line.split(",");
        
        int prestigePoints = Integer.parseInt(data[0]);
        HashMap<gemsColor, Integer> cost = new HashMap<>();
        cost.put(gemsColor.Black, Integer.valueOf(data[1]));
        cost.put(gemsColor.Blue, Integer.valueOf(data[2]));
        cost.put(gemsColor.Green, Integer.valueOf(data[3]));
        cost.put(gemsColor.Red, Integer.valueOf(data[4]));
        cost.put(gemsColor.White, Integer.valueOf(data[5]));
        String imageName = data[6].trim();
        return new NobleCards(id, prestigePoints, cost, imageName);
    }

    // Shuffle card
    // by Taweerat
    public void shuffleAll() {
        Collections.shuffle(level1);
        Collections.shuffle(level2);
        Collections.shuffle(level3);

        Collections.shuffle(noble);
    }
    
    public Stack<DevelopmentCards> getDevelopmentCardsLevel1() {return level1;}
    public Stack<DevelopmentCards> getDevelopmentCardsLevel2() {return level2;}
    public Stack<DevelopmentCards> getDevelopmentCardsLevel3() {return level3;}
    public ArrayList<NobleCards> getNobleCards() {return noble;}
    
}
