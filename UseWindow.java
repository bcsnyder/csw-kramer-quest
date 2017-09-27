import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class UseWindow
{
    private MenuWindow menu;
    private GameplayWindow game;
    private TutorialWindow tut;
    private CombatWindow comb;

    public UseWindow() {
        menu = new MenuWindow();
        game = new GameplayWindow();
        tut = new TutorialWindow();
        comb = new CombatWindow();

        showMenu();
    }

    public void showMenu() {
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
                    game.displayWindow("Player 1", "_______________ | . . . . . . | # . . @ . . . | | . . . . . $ | | . . . . . . | | . . . . . . | | . . . . . . | | . . . . . . | _______________ ", 0, 0, 0, 0, 0, 9);
                }
            });
    }

    public void showTutorial() {
        tut.displayWindow();

        tut.menuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeTutorial();
                    menu.displayWindow();
                }
            });
    }
    
    public void showCombat(String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        comb.displayWindow(playerName, monster, pHealth, pAtk, wD);

        comb.runButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeTutorial();
                    menu.displayWindow();
                }
            });
        comb.attackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    closeTutorial();
                    menu.displayWindow();
                }
            });
    }

    public void showGame(String playerName, String display, int level, int hP, int stam, int atk, int wD, int rHeight) {
        game.displayWindow(playerName, display, level, hP, stam, atk, wD, rHeight);
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