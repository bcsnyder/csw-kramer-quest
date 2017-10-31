import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * LOL Im so tired of doing this documentation so here's yje giyst of it:
 * inventory is an array of items
 * inventoryText is that array as a string array so it can be displayed
 * There's a button to go back to the game.
 */
public class InventoryWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 800;//Sets size of window
    public static final int CANVAS_HEIGHT = 500;

    private ArrayList<Item> inventory;
    private InventoryDisplay canvas;
    private ArrayList<String> inventoryText;

    public JButton returnButton;
    int select = 0;
    private String actionMessage = "";
    private int health;
    private int stamina;
    private int attack;
    private int weaponDur;
    private Player savedPlayer;
    private Monster savedMonster;
    private Stage savedStage;
    private int savedRoomPosition;
    private String wName;
    
    public void storeCombat (Player playCombat, Monster monsterCombat, Stage stCombat, int numCombat) {
        savedPlayer = playCombat;
        savedStage = stCombat;
        savedMonster = monsterCombat;
        savedRoomPosition = numCombat;
    }

    public void displayWindow(ArrayList<Item> inv, Player p, Stage s, int n) {
        inventory = inv;

        canvas = new InventoryDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        JPanel buttonPane = new JPanel(new FlowLayout());
        returnButton = new JButton("Return to Game ");
        buttonPane.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (p.getCombat() == true) {
                        dispose();
                        CombatWindow cW = new CombatWindow();
                        cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition);
                    } else {
                        dispose();
                        GameplayWindow gW = new GameplayWindow();
                        gW.displayWindow(p, s, n);
                    }
                }
            });

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Inventory");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
        setFocusable(true);
        Player play = p;
        health = play.getHealth();
        stamina = play.getStamina();
        attack = play.getAttack();
        weaponDur = play.getDur();
        wName = play.weaponName();
        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_W:

                        if (select > 0) {
                            select = select - 1;
                        } 
                        repaint();
                        break;
                        case KeyEvent.VK_S:
                        if (select < inventoryText.size() - 1) {
                            select = select + 1;
                        }
                        repaint();
                        break;
                        case KeyEvent.VK_ENTER:
                        if (inventory.get(select)!=null) {
                            if (inventory.get(select).getType().equals("Food")) {
                                if (play.getStamina() >= 100) {
                                    actionMessage = "You're already full!";
                                } else {
                                    play.setStamina(play.getStamina() + ((Food)inventory.get(select)).eat());
                                    actionMessage = "You regained some stamina.";
                                    inventory.remove(select);
                                    stamina = play.getStamina();
                                    if (play.getStamina() > 100) {
                                        play.setStamina(100);
                                    } 

                                    if (p.getCombat() == true) {

                                        p.setHealth(play.getHealth() - savedMonster.getAttack());
                                        if (play.getHealth() <= 0) {
                                            GameOverWindow gOW = new GameOverWindow();
                                            gOW.displayWindow(play.getName(), "Killed by " + savedMonster.getName());
                                            dispose();
                                            
                                        } 
                                    }
                                }
                            } else
                            if (inventory.get(select).getType().equals("Weapon")) {
                                play.setWeapon((Weapon)inventory.get(select));
                                actionMessage = "You equipped the " +inventory.get(select).getName() +".";
                                attack = play.getAttack();
                                weaponDur = play.getDur();
                                if (p.getCombat() == true) {

                                    p.setHealth(play.getHealth() - savedMonster.getAttack());
                                    if (play.getHealth() <= 0) {
                                        GameOverWindow gOW = new GameOverWindow();
                                        gOW.displayWindow(play.getName(), "Killed by " + savedMonster.getName());
                                        dispose();
                                    } 
                                }

                            }
                        }

                        repaint();
                        break;
                    }
                } 

            });
    }

    /**
     * takes array of items and returns info about the
     * items as a String so it can be displayed.
     * Each element is a line of text.
     */
    private void setInvTxt() {
        inventoryText = new ArrayList<String>();

        //Gets info about items to show user including the type and name
        for (int i = 0; i < inventory.size(); i++) {
            String type = inventory.get(i).getType();
            String name = inventory.get(i).getName();
            inventoryText.add(type + ": " + name);
        }
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
    private class InventoryDisplay extends JPanel {

        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            setInvTxt();
            String txt;
            int x;
            //Basically splits up each element of String arraylist into a
            //different line on the window
            for (int i = 0; i < inventoryText.size(); i++) {
                if (i == select) {
                    txt = "> " + inventoryText.get(i);
                } else {
                    txt = inventoryText.get(i);
                }
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*i));
            }
            x = centerStringX(actionMessage, CANVAS_WIDTH, g);
            g.drawString(actionMessage, x, CANVAS_HEIGHT - 60);

            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.setColor(Color.YELLOW); //Displays important stats at bottom of screen
            String line1Vars = "HP:"+health +"  Stamina:"+stamina +"    Attack:"+attack +"   Weapon:"+wName+"   Weapon Integrity:" +weaponDur;
            x = centerStringX(line1Vars, CANVAS_WIDTH, g);
            g.drawString(line1Vars, x, CANVAS_HEIGHT - 20);
        }
    }
}
