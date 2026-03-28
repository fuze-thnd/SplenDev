package client;

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
    private Cards selectedCard = null;
    private JButton lastSelectedCardBtn = null;

    public GameController(GameWindow view, GameClient network) {
        this.view = view;
        this.network = network;
        initController();
    }

    private void initController() {
        List<JButton> actionBtns = view.getActionButtons();
        for (int i = 0; i < actionBtns.size(); i++) {
            final int index = i;
            actionBtns.get(i).addActionListener(e -> handleAction(index));
        }
        
        List<JButton> nobleBtns = view.getNobleCardLst();
        for (int i = 0; i < nobleBtns.size(); i++) {
            final int index = i;
            nobleBtns.get(i).addActionListener(e -> handleNobleSelection(index));
        }

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
    }
    
    public void onYourTurn() {
        view.showOverlay("ACTION");
    }
    
    public void updateRoomData(RoomHandler room) {
        this.currentRoom = room;
        //refreshGameUI();
    }
    
    private void resetSelection() {
        for (JButton btn : highlightedButtons) {
            view.setHighlight(btn, false);
        }
        highlightedButtons.clear();
        
        selectedGems.clear();
        selectedCard = null;
        lastSelectedCardBtn = null;
        
        view.getConfirmBtn().setEnabled(false);

        System.out.println("Selection reset.");
    }
    
    private void handleAction(int index) {
        selectedGems.clear();
        selectedCard = null;
        
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
                if (selectedCard != null) {
                    msg = new NetworkMessage("BUY_CARD", selectedCard.getId());
                }
                break;
            case 0: // Reserve Card
                if (selectedCard != null) {
                    msg = new NetworkMessage("RESERVE_CARD", selectedCard.getId());
                }
                break;
        }
        
        if (msg != null) {
            network.sendToServer(msg);
            System.out.println(msg.getData());
            resetSelection();
            currentActionMode = -1;
            view.showOverlay("NONE");
        }
        
        
    }
    
    private void checkSelectionValidity() {
        boolean isValid = false;
        switch (currentActionMode) {
            case 1 -> isValid = (selectedGems.size() == 3);
            case 2 -> isValid = (selectedGems.size() == 1);
            case 3, 0 -> isValid = (lastSelectedCardBtn != null);
            default -> {
            }
        }
        view.getConfirmBtn().setEnabled(isValid);
    }
    
    private void handleNobleSelection(int index) {
        if (currentRoom == null) return;
        if (currentActionMode == 0) {
            NobleCards clickedCard = currentRoom.getShowNobleCard().get(index);
            if (clickedCard != null) {
                this.selectedCard = clickedCard;
                System.out.println("Selected Card ID: " + clickedCard.getId());
            }
        }
    }

    private void handleCardSelection(int level, int index) {
        if (currentRoom == null) return;
        if (currentActionMode == 3) {
            DevelopmentCards clickedCard = null;
//            if (level == 1) clickedCard = currentRoom.getShowCardsLv1().get(index);
//            else if (level == 2) clickedCard = currentRoom.getShowCardsLv2().get(index);
//            else if (level == 3) clickedCard = currentRoom.getShowCardsLv3().get(index);
            
            if (clickedCard == null) return;
            
            JButton clickedBtn = null;
            if (level == 1) clickedBtn = view.getLevel1CardLst().get(index);
            else if (level == 2) clickedBtn = view.getLevel2CardLst().get(index);
            else if (level == 3) clickedBtn = view.getLevel3CardLst().get(index);
            
            if (selectedCard != null && selectedCard.getId() == clickedCard.getId()) {
                resetSelection();
            } else {
                resetSelection();
                this.selectedCard = clickedCard;
                view.setHighlight(clickedBtn, true);
                highlightedButtons.add(clickedBtn);
                
                System.out.println("Selected Card ID: " + clickedCard.getId() + " at Level " + level);
            }
        }
        checkSelectionValidity();
    }

    private void handleGemSelection(int index) {
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