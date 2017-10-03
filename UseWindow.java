import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * UseWindow provides all of the tools to display and close windows in
 * the game. It also controls the functionality of the buttons on all
 * of the windows, allowing modular changes.
 * 
 * Variable Dictionary:
 * menu             instance of menu window
 * game             instance of game play window
 * tut              instance of tutorial window
 * comb             instance of combat window
 * inv              instance of inventory window
 * govr             instance of game over window
 * currentWindow    public String allowing to access current window status
 * currentRoom      the String of the room the player is in
 */
public class UseWindow
{
    private MenuWindow menu;
    private GameplayWindow game;
    private TutorialWindow tut;
    private CombatWindow comb;
    private InventoryWindow inv;
    private GameOverWindow govr;
    
    public String currentWindow;
    public String currentRoom;
    
    /**
     * Constructor method for UseWindow that intializes
     * all instances of windows and calls example method
     */
    public UseWindow() {
        menu = new MenuWindow();
        game = new GameplayWindow();
        tut = new TutorialWindow();
        comb = new CombatWindow();
        inv = new InventoryWindow();
        govr = new GameOverWindow();

        exampleFunctionality();
    }
    
    /**
     * method used to test window components with demo/arbitrary
     * variables. Currently displays a whole bunch of windows
     * at once (menu, inventory, combat).
     */
    public void exampleFunctionality() {
        showMenu();
        showCombat("Player 1", new Monster(), 20, 5, 2);
        showGameOver("Player 1", "Killed by stupidity.");
        
        //Creates example inventory of food items to display
        ArrayList<Item> inven = new ArrayList<Item>();
        inven.add(new Food("Bread"));
        inven.add(new Food("Meat"));
        inven.add(new Food("Cheese"));
        showInventory(inven);
    }
    
    /**
     * Displays menu screen and controls what the menu's buttons
     * do. SEE IMPORTANT MESSAGE BELOW!!!!
     */
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
                    /**
                     * THIS BUTTON SHOWS THE GAME BUT IT USES
                     * AN ABITRARY ROOM AND SET OF VARIABLES.
                     * TO MAKE THE PROGRAM WORK ACTUAL VARIABLES
                     * WILL HAVE TO BE PASSED IN!!!!!!!!!!!!
                     */
                    showGame("Player 1", "_______________ | . . . . . . | # . . @ . . . | | . . . . . $ | | . . . . . . | | . . . . . . | | . . . . . . | | . . . . . . | _______________ ", 1, 1, 1, 1, 1, 9);
                }
            });
    }
    
    /**
     * Displays tutorial screen and controls what the tutorial's button
     * does.
     */
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
    
    /**
     * Displays game screen with the parameters passed into it.
     * MAY WANT TO SWICTH TO PASSING IN PLAYER OBJECT AND USING
     * SETTERS AND GETTERS CONTAINED IN THAT OBJECT SO THAT 
     * THE PARAMETER LIST ISN'T SO LONG
     * (only non obvious parameters listed)
     * 
     * @param display   the current room in string format
     * @param level     the location of the player (what stage)
     * @param wD        weapon durability statistic
     * @param rHeight   height of the room so the string can be divided
     *                  into lines to print out separately.
     */
    public void showGame(String playerName, String display, int level, int hP, int stam, int atk, int wD, int rHeight) { 
        currentWindow = "game";
        currentRoom = display;
        game.displayWindow(playerName, display, level, hP, stam, atk, wD, rHeight);
    }
    
    /**
     * Displays combat screen with the parameters passed into it.
     * Also controls the buttons on the combat screen including 
     * the option to attack.
     * MAY WANT TO SWICTH TO PASSING IN PLAYER OBJECT AND USING
     * SETTERS AND GETTERS CONTAINED IN THAT OBJECT SO THAT 
     * THE PARAMETER LIST ISN'T SO LONG
     * (only non obvious parameters listed)
     * 
     * @param monster   the monster object the player is fighting
     * @param pAtk      the player's attack
     * @param wD        weapon durability statistic
     */
    public void showCombat(String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        currentWindow = "combat";
        comb.displayWindow(playerName, monster, pHealth, pAtk, wD);

        comb.runButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeCombat();
                    /**
                     * THIS BUTTON SHOWS THE GAME BUT IT USES
                     * AN ABITRARY ROOM AND SET OF VARIABLES.
                     * TO MAKE THE PROGRAM WORK ACTUAL VARIABLES
                     * WILL HAVE TO BE PASSED IN!!!!!!!!!!!!
                     */
                    showGame(playerName, game.room, 0, pHealth, 0, pAtk, wD, 9);
                }
            });
        
            //controls what happens when attack button pressed
        comb.attackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //Makes combat more exciting by adding random damage modifier
                    int bonus = (int)(Math.random()*5);
                    monster.ouchie(pAtk + bonus);//damages monster
                    
                    //Sets feedback for user
                    String message = "You attack, dealing " + (pAtk+bonus)+ " damage!";
                    refreshCombat(message, playerName, monster, pHealth, pAtk, wD);
                }
            });
    }
    
    /**
     * Displays tutorial screen and controls what the return to menu button
     * does. Includes death message and player's name.
     */
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
    
    /**
     * Displays inventory screen and controls what the return to game button
     * does.
     * 
     * @param inventory ArrayList of items in user's inventory
     */
    public void showInventory(ArrayList<Item> inventory) {
        currentWindow = "inventory";
        inv.displayWindow(inventory);
        
        inv.returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeInventory();
                    /**
                     * THIS BUTTON SHOWS THE GAME BUT IT USES
                     * AN ABITRARY ROOM AND SET OF VARIABLES.
                     * TO MAKE THE PROGRAM WORK ACTUAL VARIABLES
                     * WILL HAVE TO BE PASSED IN!!!!!!!!!!!!
                     */
                    showGame("Player 1", "_______________ | . . . . . . | # . . @ . . . | | . . . . . $ | | . . . . . . | | . . . . . . | | . . . . . . | | . . . . . . | _______________ ", 1, 1, 1, 1, 1, 9);
                }
            });
    }
    
    /**
     * Refreshes combat screen with updated variables
     */
    public void refreshCombat(String battleMessage, String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        comb.refreshWindow(battleMessage, playerName, monster, pHealth, pAtk, wD);
    }
    
    /**
     * Refreshes game screen with updated variables
     */
    public void refreshGame(String useMessage, String display, int level, int hP, int stam, int atk, int wD, int rHeight) {
        currentRoom = display;
        game.refreshWindow(useMessage, display, level, hP, stam, atk, wD, rHeight);
    }
    
    /**ALL THESE METHODS JUST CLOSE WINDOWS! YAY! :)
     * 
     */
    
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
    
    /**
     * Takes in 2D array (the current room) and makes it into a string.
     * 
     * @param tiles         the current room in array form
     * @return roomDisplay  the room in freshly concatenated string form
     */
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
