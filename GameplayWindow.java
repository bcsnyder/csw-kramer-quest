import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays a window with a demo components of the game inside
 * including example stats, the user's name, and text graphics
 * of an example room.
 * 
 * Interesting Variable Only Dictionary:
 * room             String holding the ASCII text for the current room
 * levelNum         What stage the player is on
 * actionMessage    message displayed at bottom of screen to notify user
 */
public class GameplayWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 750;//Sets size of window
    public static final int CANVAS_HEIGHT = 450;

    private GameDisplay canvas;
    public String room;

    /**
     * All these are stats that are ideally accessed through other objects
     * like a Player object or something
     */
    private String pName;
    private int levelNum; 
    private int health;
    private int stamina;
    private int attack;
    private int wDurability;
    private int roomHeight;
    private int roomNum
    private Player play;
    private Room space;

    private String actionMessage = "";

    /**
     * Sets variables based on what's passed in and sets up
     * the window's components and layout. Pretty standard.
     */
    public void displayWindow(Player player, Room board) {
        room = board.toString();
        pName = player.getName();
        levelNum = board.getLevel();//Sets variables (will be replaced by getters)
        health = player.getHealth();
        stamina = player.getStamina();
        attack = player.getAttack();
        wDurability = player.getDur();
        roomHeight = board.getHeight();
        play = player;
        space = board;

        canvas = new GameDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("\"Game Board\"");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window

        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_W:
                        int action = play.moveUp();
                        do {
                            if (action == 1) {
                                space = play.getRoom();
                                refreshWindow("You moved up!", play, space);
                            } else if(action == 2) {
                                roomNum = roomNum +1;
                                space = stage.nextRoom(roomNum); //This runs through stage of premade rooms instead of make one
                                space.addPlayer(1,1);
                                play.setRoom(space);
                                play.setPos(1,1);
                                refreshWindow("You moved to a new room!", play, space);
                            } else if(action == 3) {
                                play.addItem(new Bread());
                                refreshWindow("You got bread.", play, space);
                            } else if(action == 4) {
                                refreshWindow("You enter combat!", play, space);
                                CombatWindow cW = new CombatWindow();
                                cW.displayWindow(play, new Gremlin(), space);
                                dispose();
                            } else {
                                action = play.moveUp();
                                refreshWindow("You can't move there!", play, space);
                            }
                        } while (action < 0);
                        repaint();
                        break;
                        case KeyEvent.VK_S:
                        action = play.moveDown();
                        do {
                            if (action == 1) {
                                space = play.getRoom();
                                refreshWindow("You moved down!", play, space);
                            } else if(action == 2) {
                                roomNum = roomNum +1;
                                space = stage.nextRoom(roomNum);
                                space.addPlayer(1,1);
                                play.setRoom(space);
                                play.setPos(1,1);
                                refreshWindow("You moved to a new room!", play, space);
                            } else if(action == 3) {
                                play.addItem(new Axe());
                                refreshWindow("You got axe.", play, space);
                            } else if(action == 4) {
                                refreshWindow("You enter combat!", play, space);
                                CombatWindow cW = new CombatWindow();
                                cW.displayWindow(play, new Gremlin(), space);
                                dispose();
                            } else {
                                action = play.moveDown();
                                refreshWindow("You can't move there!", play, space);
                            }
                        } while (action < 0);
                        repaint();
                        break;
                        case KeyEvent.VK_A:
                        action = play.moveLeft();
                        do {
                            if (action == 1) {
                                space = play.getRoom();
                                refreshWindow("You moved left!", play, space);
                            } else if(action == 2) {
                                roomNum = roomNum +1;
                                space = stage.nextRoom(roomNum);
                                space.addPlayer(1,1);
                                play.setRoom(space);
                                play.setPos(1,1);
                                refreshWindow("You moved to a new room!", play, space);
                            } else if(action == 3) {
                                play.addItem(new Bread());
                                refreshWindow("You got bread.", play, space);
                            } else if(action == 4) {
                                refreshWindow("You enter combat!", play, space);
                            } else {
                                action = play.moveLeft();
                                refreshWindow("You can't move there!", play, space);
                            }
                        } while (action < 0);
                        repaint();
                        break;
                        case KeyEvent.VK_D:
                        action = play.moveRight();
                        do {
                            if (action == 1) {
                                space = play.getRoom();
                                refreshWindow("You moved right!", play, space);
                            } else if(action == 2) {
                                roomNum = roomNum +1;
                                space = stage.nextRoom(roomNum);
                                space.addPlayer(1,1);
                                play.setRoom(space);
                                play.setPos(1,1);
                                refreshWindow("You moved to a new room!", play, space);
                            } else if(action == 3) {
                                play.addItem(new Bread());
                                refreshWindow("You got bread.", play, space);
                            } else if(action == 4) {
                                refreshWindow("You enter combat!", play, space);
                            } else {
                                action = play.moveRight();
                                refreshWindow("You can't move there!", play, space);
                            }
                        } while (action < 0);
                        repaint();
                        break;
                        case KeyEvent.VK_I:
                        InventoryWindow iW = new InventoryWindow();
                        iW.displayWindow(play.getInventory(), play, space);
                        dispose();
                        break;
                    }
                }
            });
    }

    /**
     * Simply updates variables with what's passed in and
     * repaints the window. VOILA!
     */
    public void refreshWindow(String useMessage, Player player, Room board) {
        actionMessage = useMessage;
        room = board.toString();
        pName = player.getName();
        levelNum = board.getLevel();//Sets variables (will be replaced by getters)
        health = player.getHealth();
        stamina = player.getStamina();
        attack = player.getAttack();
        wDurability = player.getDur();
        roomHeight = board.getHeight();

        canvas.repaint();
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
     * Panel inside frame that holds game graphics
     * and stats for user
     */
    private class GameDisplay extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays username and score at top of screen
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            String playerInfo = pName + " - Level " + levelNum;
            int x = centerStringX(playerInfo, CANVAS_WIDTH, g);
            g.drawString(playerInfo, x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 24)); //Displays gameboard
            int yCor = 60; //Starting Y coordinate
            int inc = room.length()/roomHeight; //Size of each row chunk to be displayed
            x = centerStringX(room.substring(0,inc), CANVAS_WIDTH, g);
            for (int i = 1; i <= roomHeight; i++) {//Splits room into rows and displays them
                g.drawString(room.substring((i-1)*inc,i*inc), x, yCor);
                yCor += 25;
            }

            //Shows desired action message to notify user about
            //whatever just happened
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            x = centerStringX(actionMessage, CANVAS_WIDTH, g);
            g.drawString(actionMessage, x, CANVAS_HEIGHT - 60);

            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.setColor(Color.YELLOW); //Displays important stats at bottom of screen
            String line1Vars = "HP:"+health+"  Stamina:"+stamina+"    Attack:"+attack+"   Weapon Strength:"+wDurability;
            x = centerStringX(line1Vars, CANVAS_WIDTH, g);
            g.drawString(line1Vars, x, CANVAS_HEIGHT - 20);
        }
    }
}
