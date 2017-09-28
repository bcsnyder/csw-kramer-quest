import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UseWindow
{
    private MenuWindow menu;
    private GameplayWindow game;
    private TutorialWindow tut;
    private CombatWindow comb;
    private InventoryWindow inv;
    private GameOverWindow govr;
    
    public String currentWindow;

    public UseWindow() {
        menu = new MenuWindow();
        game = new GameplayWindow();
        tut = new TutorialWindow();
        comb = new CombatWindow();
        inv = new InventoryWindow();
        govr = new GameOverWindow();

        currentWindow = "menu";
        exampleFunctionality();
    }
    
    public void exampleFunctionality() {
        showMenu();
        showCombat("Player 1", new Monster(), 20, 5, 2);
        showGameOver("Player 1", "Killed by stupidity.");
        ArrayList<Item> inven = new ArrayList<Item>();
        inven.add(new Food("Bread"));
        inven.add(new Food("Meat"));
        inven.add(new Food("Cheese"));
        showInventory(inven);
    }

    public void showMenu() {
        currentWindow = "menu";
        menu.displayWindow();

        menu.tutorialButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeMenu();
                    showTutorial();
                }
            });

        menu.playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeMenu();
                    showGame("Player 1", "_______________ | . . . . . . | # . . @ . . . | | . . . . . $ | | . . . . . . | | . . . . . . | | . . . . . . | | . . . . . . | _______________ ", 1, 1, 1, 1, 1, 9);
                }
            });
    }

    public void showTutorial() {
        currentWindow = "tutorial";
        tut.displayWindow();

        tut.menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeTutorial();
                    showMenu();
                }
            });
    }
    
    public void showGame(String playerName, String display, int level, int hP, int stam, int atk, int wD, int rHeight) { 
        currentWindow = "game";
        game.displayWindow(playerName, display, level, hP, stam, atk, wD, rHeight);
    }

    public void showCombat(String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        currentWindow = "combat";
        comb.displayWindow(playerName, monster, pHealth, pAtk, wD);

        comb.runButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeCombat();
                    showGame(playerName, game.room, 0, pHealth, 0, pAtk, wD, 9);
                }
            });
        comb.attackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    int bonus = (int)(Math.random()*5);
                    monster.ouchie(pAtk + bonus);
                    String message = "You attack, dealing " + (pAtk+bonus)+ " damage!";
                    refreshCombat(message, playerName, monster, pHealth, pAtk, wD);
                }
            });
    }

    public void showGameOver(String playerName, String deathMessage) {
        currentWindow = "game over";
        govr.displayWindow(playerName, deathMessage);
        govr.menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeGameOver();
                    showMenu();
                }
            });
    }
    
    public void showInventory(ArrayList<Item> inventory) {
        currentWindow = "inventory";
        inv.displayWindow(inventory);
        
        inv.returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeInventory();
                    showGame("Player 1", "_______________ | . . . . . . | # . . @ . . . | | . . . . . $ | | . . . . . . | | . . . . . . | | . . . . . . | | . . . . . . | _______________ ", 1, 1, 1, 1, 1, 9);
                }
            });
    }

    public void refreshCombat(String battleMessage, String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        comb.refreshWindow(battleMessage, playerName, monster, pHealth, pAtk, wD);
    }

    public void refreshGame(String useMessage, String display, int level, int hP, int stam, int atk, int wD, int rHeight) {
        game.refreshWindow(useMessage, display, level, hP, stam, atk, wD, rHeight);
    }

    public void closeMenu() {
        menu.dispose();
    }

    public void closeTutorial() {
        tut.dispose();
    }

    public void closeGame() {
        game.dispose();
    }

    public void closeCombat() {
        comb.dispose();
    }

    public void closeGameOver() {
        govr.dispose();
    }
    
    public void closeInventory() {
        inv.dispose();
    }

    public String roomToString(String[][] tiles) {
        String roomDisplay = "";

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                if (y == 0 || y == tiles.length - 1) {
                    roomDisplay = tiles[y][x] + "_";
                } else {
                    roomDisplay = tiles[y][x] + " ";
                }
            }
        }

        return roomDisplay;
    }
}