package server;

import java.io.Serializable;
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

    public synchronized boolean processAction(Player sender, NetworkMessage msg) {
        Player currentPlayer = state.getPlayers().get(state.getCurrentPlayerIndex());
        if (!currentPlayer.getName().equals(sender.getName())) {
            return false;
        }

        boolean success = false;
        String command = msg.getCommand();

        try {
            ArrayList<String> data = (ArrayList) msg.getData();
            if (command.equals("TAKE_3_GEMS")) {
                gemsColor color1 = gemsColor.valueOf(data.get(0));
                gemsColor color2 = gemsColor.valueOf(data.get(1));
                gemsColor color3 = gemsColor.valueOf(data.get(2));
                success = take3Gems(currentPlayer, color1, color2, color3);
            } else if (command.equals("TAKE_2_GEMS")) {
                gemsColor color1 = gemsColor.valueOf(data.get(0));
                success = take2Gems(currentPlayer, color1);
            } else if (command.equals("BUY_CARD")) {
                int id = Integer.parseInt(data.get(0));
                DevelopmentCards card = findCardById(currentPlayer, id);
                if (card != null) {
                    success = buyCard(currentPlayer, card);
                }
            } else if (command.equals("RESERVE_CARD")) {
                int id = Integer.parseInt(data.get(0));
                DevelopmentCards card = findCardById(currentPlayer, id);
                success = reserveCard(currentPlayer, card);
            }
        } catch (Exception e) {
            return false;
        }

        if (success) {
            buyNoble(currentPlayer);
            refillDevelopmentCards();
            int nextTurn = (state.getCurrentPlayerIndex() + 1) % state.getNumberOfPlayers();
            state.setCurrentPlayerIndex(nextTurn);
        }
        return success;
    }
    
    public synchronized boolean take3Gems(Player p, gemsColor g1, gemsColor g2, gemsColor g3) {
        boolean notGold = (g1 != gemsColor.Gold) && (g2 != gemsColor.Gold) && (g3 != gemsColor.Gold);
        boolean notSameColors = g1 != g2 && g1 != g3 && g2 != g3;
        boolean haveEnoughGem = state.getBankGems().get(g1) > 0 && state.getBankGems().get(g2) > 0 && state.getBankGems().get(g3) > 0;
        int sumOfGems = 0;
        for (Integer value : p.getGems().values()) {
            sumOfGems += value;
        }
        boolean notMoreThan10 = (sumOfGems + 3) <= 10;
        if (notSameColors && haveEnoughGem && notGold && notMoreThan10) {
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
    
    public synchronized boolean take2Gems(Player p, gemsColor g) {
        int sumOfGems = 0;
        for (Integer value : p.getGems().values()) {
            sumOfGems += value;
        }
        boolean notMoreThan10 = (sumOfGems + 2) <= 10;
        if (g != gemsColor.Gold && state.getBankGems().get(g) >= 4 && notMoreThan10) {
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
    
    public synchronized boolean buyCard(Player p, DevelopmentCards card) {
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
        state.setBankGems(bankGems);
        p.addDevelopmentCards(card);
        card.addPlayerPrestigePoints(p);
        p.addBonusGems(card.getGemsColor());
        // remove card
        if (p.getReservedCard().contains(card)) {
            p.getReservedCard().remove(card);
        } else {
            removeDevelopmentCardOnboard(card);
        }
        return true;
    }
    
    public int[] findDevelopmentCardIndex(DevelopmentCards card) {
        for (int i=0; i<3; i++)
            for (int j=0; j<4; j++) {
                if (state.getCardsOnBoard()[i][j].equals(card))
                    return new int[]{i, j};
            }
        return null;
    }
    
    public void removeDevelopmentCardOnboard(DevelopmentCards card) {
        int[] index = findDevelopmentCardIndex(card);
        int i = index[0], j = index[1];
        DevelopmentCards[][] cardsOnBoard = state.getCardsOnBoard();
        cardsOnBoard[i][j] = null;
        state.setCardsOnBoard(cardsOnBoard);
    }

    public DevelopmentCards findCardById(Player p, int id) {
        // Search on the Board (3 rows, 4 columns)
        DevelopmentCards[][] board = state.getCardsOnBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                DevelopmentCards card = board[i][j];
                if (card != null && card.getId() == id) {
                    return card;
                }
            }
        }

        // Search in the Player's Reserved Cards
        for (DevelopmentCards card : p.getReservedCard()) {
            if (card.getId() == id) {
                return card;
            }
        }

        return null; // Card not found
    }
    
    public synchronized boolean reserveCard(Player p, DevelopmentCards card) {
        if (p.getReservedCard().size() >= 3) {
            return false;
        }
        if (state.getBankGems().get(gemsColor.Gold) > 0) {
            p.addGems(gemsColor.Gold, 1);
            state.getBankGems().put(gemsColor.Gold, state.getBankGems().get(gemsColor.Gold)-1);
        }
        p.addReservedCard(card);
        removeDevelopmentCardOnboard(card);
        return true;
    }
    
    public synchronized void sacrificeCard(Player p, Sacrificable card) {
        int bankGemsGold = state.getBankGems().get(gemsColor.Gold);
        int playerRefund = card.getDropRefund();
        int actuallyGold;
        if (bankGemsGold >= playerRefund) {
            actuallyGold = playerRefund;
        } else {
            actuallyGold = bankGemsGold;
        }
        p.removeDevelopmentCards((DevelopmentCards)card);
        state.getBankGems().put(gemsColor.Gold, state.getBankGems().get(gemsColor.Gold)-actuallyGold);
        p.addGems(gemsColor.Gold, actuallyGold);
    }
    
    public boolean checkWin(Player p){
        return (p.getPrestigePoints() >= 15);
    }
    
    public synchronized NobleCards buyNoble(Player p) {
        ArrayList<NobleCards> nobleCardsOnBoard = state.getNobleCardsOnBoard();
        HashMap<gemsColor,Integer> pBonusGems = p.getBonusGems();
        NobleCards noble = null;
        
        for (NobleCards i :nobleCardsOnBoard) {
            HashMap<gemsColor,Integer> currentNobleCardCost = i.getCost();
            boolean buyable = true;
            for (gemsColor j : currentNobleCardCost.keySet()) {
                if (pBonusGems.get(j) < currentNobleCardCost.get(j)) {
                    buyable = false;
                    break;
                }
            }
            if (buyable) {
                noble = i;
                p.addNobleCards(i);
                i.addPlayerPrestigePoints(p);
                nobleCardsOnBoard.remove(i);
                state.setNobleCardsOnBoard(nobleCardsOnBoard);
                break;
            }
        }
        return noble;
    }
    
    public synchronized boolean checkGems(Player p) {
        HashMap<Gems.gemsColor,Integer> pGems = p.getGems();
        Integer sumGems = 0;
        for (gemsColor i : pGems.keySet()) {
            sumGems += pGems.get(i);
        }
        return (sumGems > 10);
    }
    
    public void refillDevelopmentCards() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                // skip if the slot is already has a card
                if (state.getCardsOnBoard()[i][j] != null)
                    continue;
                // add the slot that has no card
                if (i == 0 && !state.getDevelopmentCardsLevel1().isEmpty())
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel1().pop();
                else if (i == 1 && !state.getDevelopmentCardsLevel1().isEmpty())
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel2().pop();
                else if (i == 2 && !state.getDevelopmentCardsLevel1().isEmpty())
                    state.getCardsOnBoard()[i][j] = state.getDevelopmentCardsLevel3().pop();
            }
        }
    }
    
    public synchronized Player getWinner() {
        ArrayList<Player> winner = new ArrayList<>();
        for (Player p : state.getPlayers()) {
            if (p.getPrestigePoints() >= 15) {
                winner.add(p);
            }
        }
        Collections.sort(winner, Comparator.comparing(Player::getDevelopmentCardSize)); // sort by development card size
        return winner.getFirst();
    }
}
