package client;

import com.sun.security.jgss.GSSUtil;
import shared.*;
import shared.Gems.gemsColor;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import client.GameClient;
import java.util.HashSet;
import shared.NetworkMessage;
import server.RoomHandler;
import client.ui.GameWindow;

public class GameController {
    private GameWindow view;
    private GameClient network; 
    private RoomHandler currentRoom; 

    private int  currentActionMode = -1; // 0=Reserve, 1=take3, 2=take2, 3=BuyCard
    private List<JButton> highlightedButtons = new ArrayList<>();
    private List<gemsColor> selectedGems = new ArrayList<>();
    private int selectedCard = 0;
    private GameState gs;

    public GameController(GameWindow view, GameClient network) {
        this.view = view;
        this.network = network;
        //initController(gs);
    }

    public void initController(GameState gs) {
        List<JButton> actionBtns = view.getActionButtons();
        for (int i = 0; i < actionBtns.size(); i++) {
            final int index = i;
            actionBtns.get(i).addActionListener(e -> handleAction(index));
        }

//        List<JButton> nobleBtns = view.getNobleCardLst();
//        for (int i = 0; i < nobleBtns.size(); i++) {
//            final int index = i;
//            nobleBtns.get(i).addActionListener(e -> handleNobleSelection(index));
//        }

        List<JButton> lv1Btns = view.getLevel1CardLst();
        for (int i = 0; i < lv1Btns.size(); i++) {
            final int index = i;
            lv1Btns.get(i).addActionListener(e -> handleCardSelection(1, index));
        }

        List<JButton> lv2Btns = view.getLevel2CardLst();
        for (int i = 0; i < lv2Btns.size(); i++) {
            final int index = i;
            lv2Btns.get(i).addActionListener(e -> handleCardSelection(2, index));
        }

        List<JButton> lv3Btns = view.getLevel3CardLst();
        for (int i = 0; i < lv3Btns.size(); i++) {
            final int index = i;
            lv3Btns.get(i).addActionListener(e -> handleCardSelection(3, index));
        }

        List<JButton> gemBtns = view.getGemCardLst();
        for (int i = 0; i < gemBtns.size(); i++) {
            final int index = i;
            gemBtns.get(i).addActionListener(e -> handleGemSelection(index));
        }
        
        view.getResetBtn().addActionListener(e -> handleResetAction());
        view.getConfirmBtn().addActionListener(e -> handleConfirmAction());

        this.gs = gs;
    }
    
    public void onYourTurn() {
        view.showOverlay("ACTION");
    }

    public void Winner(String name) {
        view.getWinnerLabel().setText(name + " is The Winner");
        view.showOverlay("WIN");
    }
    
    public void updateRoomData(RoomHandler room) {
        this.currentRoom = room;
    }
    
    private void resetSelection() {
        for (JButton btn : highlightedButtons) {
            view.setHighlight(btn, false);
        }
        highlightedButtons.clear();
        
        selectedGems.clear();
        selectedCard = 0;
        
        view.getConfirmBtn().setEnabled(false);

        System.out.println("Selection reset.");
    }
    
    private void handleAction(int index) {
        selectedGems.clear();
        selectedCard = 0;
        
        this.currentActionMode = index;
        
        view.showOverlay("CONFIRM");
        
        switch (index) {
            case 0:
                System.out.println("Mode: Reserve - Select a card to reserve.");
                break;
               
            case 1:
                System.out.println("Mode: Take 3 - Select 3 different colored gems.");
                break;
            
            case 2:
                System.out.println("Mode: Take 2 - Select 1 gem color to take two.");
                break;
               
            case 3:
                System.out.println("Mode: Purchase - Select a card to but.");
                break;
                
            default:
                currentActionMode = -1;
                System.out.println("Please select an action.");
                break;
        }
    }
    
    private void handleResetAction() {
        view.showOverlay("ACTION");
        resetSelection();
    }
    
    private void handleConfirmAction() {
        if (currentRoom != null) return;
        NetworkMessage msg = null;
        
        switch (currentActionMode) {
            case 1: // Take 3 Gems
                msg = new NetworkMessage("TAKE_3_GEMS", new ArrayList<>(selectedGems));
                break;
            case 2: // Take 2 Gems
                msg = new NetworkMessage("TAKE_2_GEMS", selectedGems.get(0));
                break;
            case 3: // Buy Card
                if (selectedCard != 0) {
                    msg = new NetworkMessage("BUY_CARD", selectedCard);
                }
                break;
            case 0: // Reserve Card
                if (selectedCard != 0) {
                    msg = new NetworkMessage("RESERVE_CARD", selectedCard);
                }
                break;
        }
        
        if (msg != null) {
            view.showOverlay("NONE");
            System.out.println(msg.getData());
            resetSelection();
            currentActionMode = -1;
            network.sendToServer(msg);

        }
        
        
    }
    
    private void checkSelectionValidity() {
        boolean isValid = false;
        switch (currentActionMode) {
            case 1 -> isValid = (selectedGems.size() == 3);
            case 2 -> isValid = (selectedGems.size() == 1);
            case 3, 0 -> isValid = (selectedCard != 0);
            default -> {
            }
        }
        view.getConfirmBtn().setEnabled(isValid);
    }

//    private void handleNobleSelection(int index) {
//        if (currentRoom == null) return;
//        if (currentActionMode == 0) {
//            NobleCards clickedCard = currentRoom.getShowNobleCard().get(index);
//            if (clickedCard != null) {
//                this.selectedCard = clickedCard;
//                System.out.println("Selected Card ID: " + clickedCard.getId());
//            }
//        }
//    }

    private void handleCardSelection(int level, int index) {
        if (currentActionMode == 3 || currentActionMode == 0) {

            int clickedCard = 0;
            
            JButton clickedBtn = null;
            if (level == 1) {
                clickedCard = gs.getCardsOnBoard()[0][index].getId();
                clickedBtn = view.getLevel1CardLst().get(index);
            } else if (level == 2) {
                clickedCard = gs.getCardsOnBoard()[1][index].getId();
                clickedBtn = view.getLevel2CardLst().get(index);
            } else if (level == 3) {
                clickedCard = gs.getCardsOnBoard()[2][index].getId();
                clickedBtn = view.getLevel3CardLst().get(index);
            }
            
            if (selectedCard != 0 && selectedCard == clickedCard) {
                resetSelection();
            } else {
                resetSelection();
                this.selectedCard = clickedCard;
                view.setHighlight(clickedBtn, true);
                highlightedButtons.add(clickedBtn);
                
                System.out.println("Selected Card ID: " + clickedCard + " at Level " + level);
            }
        }
        checkSelectionValidity();
    }

    private void handleGemSelection(int index) {
        if (index == 5) return;
        gemsColor color = gemsColor.values()[index];
        JButton clickedBtn = view.getGemCardLst().get(index);
        if (currentActionMode == 1) {
            if (selectedGems.contains(color)) {
                selectedGems.remove(color);
                view.setHighlight(clickedBtn, false);
                highlightedButtons.remove(clickedBtn);
            } else if (selectedGems.size() < 3) {
                selectedGems.add(color);
                view.setHighlight(clickedBtn, true);
                highlightedButtons.add(clickedBtn);
            }
        } else if (currentActionMode == 2) {
            if (selectedGems.contains(color)) {
                resetSelection();
            } else {
                resetSelection();
                selectedGems.add(color);
                view.setHighlight(clickedBtn, true);
                highlightedButtons.add(clickedBtn);
            }
        }
        checkSelectionValidity();    
    }
    
}