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
 * room             room the player is in currently
 */
public class UseWindow
{
    private MenuWindow menu;
    private GameplayWindow game;
    private TutorialWindow tut;
    private CombatWindow comb;
    private InventoryWindow inv;
    private GameOverWindow govr;
    
    String currentWindow;
    
    Room room;
    Monster monst;
    Player user;
    
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
    }
    
    /**
     * Displays menu screen and controls what the menu's buttons
     * do.
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
                    showGame();
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
     * (only non obvious parameters listed)
     * 
     * @param display   the current room in string format
     * @param level     the location of the player (what stage)
     * @param wD        weapon durability statistic
     * @param rHeight   height of the room so the string can be divided
     *                  into lines to print out separately.
     */
    public void showGame() { 
        currentWindow = "game";
        game.displayWindow(user, room);
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
    public void showCombat(Player play, Monster monster) {
        currentWindow = "combat";
        //comb.displayWindow(play, monster);

        comb.runButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeCombat();
                    showGame();
                }
            });
        
            //controls what happens when attack button pressed
        comb.attackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    //Makes combat more exciting by adding random damage modifier
                    int bonus = (int)(Math.random()*5);
                    int damage = user.getAttack() + bonus;
                    monster.ouchie(damage);//damages monster
                    
                    //Sets feedback for user
                    String message = "You attack, dealing " + (damage)+ " damage!";
                    refreshCombat(message);
                }
            });
    }
    
    /**
     * Displays tutorial screen and controls what the return to menu button
     * does. Includes death message and player's name.
     */
    public void showGameOver(String deathMessage) {
        currentWindow = "game over";
        govr.displayWindow(user.getName(), deathMessage);
        
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
        //inv.displayWindow(inventory);
        
        inv.returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeInventory();
                    showGame();
                }
            });
    }
    
    /**
     * Refreshes combat screen with updated variables
     */
    public void refreshCombat(String battleMessage) {
        comb.refreshWindow(battleMessage, user, monst);
    }
    
    /**
     * Refreshes game screen with updated variables
     */
    public void refreshGame(String useMessage) {
        game.refreshWindow(useMessage, user, room);
    }
    
    /**
     * These methods close windows.
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
    
    public void setRoom(Room loc) {
        room = loc;
    }
    
    public void setPlayer(Player play) {
        user = play;
    }
    
    public void setMonster(Monster enemy) {
        monst = enemy;
    }
}
