import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameplayWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 750;
    public static final int CANVAS_HEIGHT = 500;
    
    private GameDisplay canvas;
    private String room;
    private String pName;
    private int levelNum;
    private int health;
    private int stamina;
    private int attack;
    private int wdurability;
    
    public void displayWindow(String playerName, String display, int level, int hP, int stam, int atk, int wD) {
        room = display;
        pName = playerName;
        levelNum = level;
        health = hP;
        stamina = stam;
        attack = atk;
        wdurability = wD;
        
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
    
    private int centerStringX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 - textWidth/2;
        return textX;
    }
    
    private class GameDisplay extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            String text = pName + ", you are on level 2.";
            int x = centerStringX(text, CANVAS_WIDTH, g);
            g.drawString(text, x, 20);
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            g.drawString(room.substring(0,13), 50, 50);
            g.drawString(room.substring(14,27), 50, 80);
            g.drawString(room.substring(28,41), 50, 110);
            g.drawString(room.substring(42,55), 50, 140);
            g.drawString(room.substring(56,69), 50, 150);
        }
    }
}