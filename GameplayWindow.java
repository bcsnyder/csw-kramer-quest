import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameplayWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 750;//Sets size of window
    public static final int CANVAS_HEIGHT = 500;

    private GameDisplay canvas;
    private String room;

    private String pName;
    private int levelNum; //Most of these variables wont be listed here once they can be accessed from other objects
    private int health;
    private int stamina;
    private int attack;
    private int wdurability;
    private int roomHeight;

    public void displayWindow(String playerName, String display, int level, int hP, int stam, int atk, int wD, int rHeight) {
        room = display;
        pName = playerName;
        levelNum = level;//Sets variables (will be replaced by getters)
        health = hP;
        stamina = stam;
        attack = atk;
        wdurability = wD;
        roomHeight = rHeight;

        canvas = new GameDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("PPP Dungeon Crawler");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }

    /*
     * centerStringX finds the x coordinate needed to center a String in the window.
     */
    private int centerStringX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 - textWidth/2;
        return textX;
    }

    /*
     * Panel inside frame that holds drawn graphics
     */
    private class GameDisplay extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays username and score
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            String playerInfo = pName + " - Level " + levelNum;
            int x = centerStringX(playerInfo, CANVAS_WIDTH, g);
            g.drawString(playerInfo, x, 20);

            g.setFont(new Font("Monospaced", Font.PLAIN, 24)); //Displays gameboard
            int yCor = 50; //Starting Y coordinate
            int inc = room.length()/roomHeight; //Size of each row chunk to be displayed
            x = centerStringX(room.substring(0,inc), CANVAS_WIDTH, g);
            for (int i = 1; i <= roomHeight; i++) {//Splits room into rows and displays them
                g.drawString(room.substring((i-1)*inc,i*inc), x, yCor);
                if (i == roomHeight - 1) {
                    yCor += 6;
                } else {
                    yCor += 25;
                }
            }
        }
    }
}