package server;

import java.util.*;

import client.*;
import shared.*;
import shared.Gems.gemsColor;

public class GameManager {
    public GameState state;
    
    public GameManager(ArrayList<Player> players) {
        state = new GameState();
        state.setPlayers(players);
        state.setNumberOfPlayers(players.size());
        setupGame();
    }
    
    public void setupGame() {
        CardLoader cardLoader = new CardLoader();
        // load all development cards and shuffle
        Stack<DevelopmentCards> deck1 = cardLoader.getDevelopmentCardsLevel1();
        Stack<DevelopmentCards> deck2 = cardLoader.getDevelopmentCardsLevel2();
        Stack<DevelopmentCards> deck3 = cardLoader.getDevelopmentCardsLevel3();
        Collections.shuffle(deck1);
        Collections.shuffle(deck2);
        Collections.shuffle(deck3);
        state.setDevelopmentCardsLevel1(deck1);
        state.setDevelopmentCardsLevel2(deck2);
        state.setDevelopmentCardsLevel3(deck3);
        // load all noble cards and shuffle
        ArrayList<NobleCards> nobles = cardLoader.getNobleCards();
        Collections.shuffle(nobles);
        
        refillDevelopmentCards();
        
        // decrease the number of cards from rules
        state.setNobleCardsOnBoard(new ArrayList<>(nobles.subList(0, state.getNumberOfPlayers()+1)));
        // make the bank get the correct amount of gems
        int n = 0;
        if (state.getNumberOfPlayers() == 2) n = 4;
        else if (state.getNumberOfPlayers() == 3) n = 5;
        else if (state.getNumberOfPlayers() == 4) n = 7;
        state.getBankGems().put(gemsColor.Black, n);
        state.getBankGems().put(gemsColor.Blue, n);
        state.getBankGems().put(gemsColor.Green, n);
        state.getBankGems().put(gemsColor.Red, n);
        state.getBankGems().put(gemsColor.White, n);
        state.getBankGems().put(gemsColor.Gold, 5); // Gold is always 5 gems
        
        // random the first player to start the game
        ArrayList<Player> p = state.getPlayers();
        Collections.shuffle(p);
        state.setPlayers(p);
    }
    
    public boolean take3Gems(Player p, gemsColor g1, gemsColor g2, gemsColor g3) {
        boolean notSameColors = g1 != g2 && g1 != g3 && g2 != g3;
        boolean haveEnoughGem = state.getBankGems().get(g1) > 0 && state.getBankGems().get(g2) > 0 && state.getBankGems().get(g1) > 0;
        if (notSameColors & haveEnoughGem) {
            state.getBankGems().put(g1, state.getBankGems().get(g1)-1);
            state.getBankGems().put(g2, state.getBankGems().get(g2)-1);
            state.getBankGems().put(g3, state.getBankGems().get(g3)-1);
            p.addGems(g1,1);
            p.addGems(g2,1);
            p.addGems(g3,1);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean take2Gems(Player p, gemsColor g) {
        if (state.getBankGems().get(g) >= 4) {
            state.getBankGems().put(g, state.getBankGems().get(g)-2);
            p.addGems(g, 2);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean canPlayerBuyCard(Player p, DevelopmentCards card) {
        // check if player can buy this card
        HashMap<gemsColor, Integer> cardCost = card.getCost();
        int goldAvailable = p.getGems().get(gemsColor.Gold);
        int goldNeeded = 0;
        
        
        for (gemsColor color : cardCost.keySet()) {
            // skip gold (no gold is cost)
            if (color == gemsColor.Gold) continue;
            
            int cost = cardCost.get(color);
            int playerGems = p.getGems().get(color);
            int playerBonus = p.getBonusGems().get(color);
            
            int costAfterBonus = Math.max(0, cost - playerBonus);
            if (costAfterBonus > 0) {
                if (playerGems < costAfterBonus) {
                    goldNeeded += costAfterBonus - playerGems;
                }
            }
        }
        return goldNeeded <= goldAvailable;
    }
    
    public boolean buyCard(Player p, DevelopmentCards card) {
        if (!canPlayerBuyCard(p, card)) {
            return false;
        }
        
        HashMap<gemsColor, Integer> cardCost = card.getCost();
        HashMap<gemsColor, Integer> bankGems = state.getBankGems();
        
        for (gemsColor color : cardCost.keySet()) {
            // skip gold (no gold is cost)
            if (color == gemsColor.Gold) continue;
            
            int cost = cardCost.get(color);
            int playerBonus = p.getBonusGems().get(color);
            int costAfterBonus = Math.max(0, cost - playerBonus);
            
            if (costAfterBonus > 0) {
                int playerGems = p.getGems().get(color);
                // have enough gems
                if (playerGems >= costAfterBonus) {
                    p.removeGems(color, costAfterBonus);
                    bankGems.put(color, bankGems.get(color) + costAfterBonus);
                // doesn't have enough gems (use gold)
                } else {
                    int goldNeeded = costAfterBonus - playerGems;
                    
                    p.removeGems(color, playerGems);
                    bankGems.put(color, bankGems.get(color) + playerGems);
                    
                    p.removeGems(gemsColor.Gold, goldNeeded);
                    bankGems.put(gemsColor.Gold, bankGems.get(gemsColor.Gold) + goldNeeded);
                }
            }
        }
        p.addDevelopmentCards(card);
        return true;
    }
    
    public boolean reserveCard(Player p, DevelopmentCards card) {
        if (p.getReservedCard().size() >= 3) {
            return false;
        }
        p.addReservedCard(card);
        return true;
    }
    
    public boolean sacrificeCard(Player p, Sacrificable card) {
        return false;
    }
    
    public boolean checkNoble(Player p) {
        return false;
    }
    
    public void refillDevelopmentCards() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // skip if the slot is already has a card
                if (state.getCardsOnBoard()[i][j] != null)
                    continue;
                // add the slot that has no card
                if (i == 0)
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel1().pop();
                else if (i == 1)
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel2().pop();
                else if (i == 2)
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel3().pop();
            }
        }
    }
}
