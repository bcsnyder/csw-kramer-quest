import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This window is actually important and tricky to understand
 * so listen up!! It shows the combat screen btw.
 * 
 * "Interesting Variables Only" Info Session:
 * m means monster
 * p means player
 * pDur             player's weapon's durability
 * mSym             Ascii symbol representing monster
 * combatMessage    message telling user what just happened
 */
public class CombatWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 700;//Sets size of window
    public static final int CANVAS_HEIGHT = 400;

    public JButton runButton;//Buttons to run away or attackn enemy
    public JButton attackButton;

    private CombatDisplay canvas;//Subcomponent where graphics displayed

    private Player p;
    private Monster m;
    private Stage s;
    private int n;
    private String combatMessage = "";
    private String menuString =">Attack          Items          Magic          Flee";
    private int turnPhase;
    int menuSelect = 0;
    private GameplayWindow gW = new GameplayWindow();
    private GameOverWindow gOW = new GameOverWindow();

    public void setMessage (String m) {
        combatMessage = m;
    }

    /**
     * Sets all the variables needed to display combat and
     * sets up the window with its display components including
     * two buttons.
     * 
     * @param monster   the monster instance the player is fighting
     *                  contains helpful variables the program gets from it
     *                  like health
     */
    public void displayWindow(Player play, Monster monster, Stage st, int num) {
        p = play;
        m = monster;
        s = st;
        n = num;
        combatMessage = "";
        play.setCombat(true);
        CombatWindow thisWindow = this;

        canvas = new CombatDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        //Creates menu system
        String menuString = ">Attack          Items          Magic          Flee";

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        // cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Battle");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_A:

                        if (menuSelect > 0) {
                            menuSelect = menuSelect - 1;
                        } 
                        repaint();
                        break;
                        case KeyEvent.VK_D:
                        if (menuSelect < 3) {
                            menuSelect = menuSelect + 1;
                        }
                        repaint();
                        break;
                        case KeyEvent.VK_ENTER:
                        if (menuSelect == 0) {
                            if (turnPhase == 2) {
                                turnPhase = 1;
                            }
                        } else if (menuSelect == 3) {                            
                            dispose();
                            play.setCombat(false);
                            gW.displayWindow(p, s, n);
                        } else if (menuSelect == 1) {
                            dispose();
                            InventoryWindow iW = new InventoryWindow();
                            iW.displayWindow(play.getInventory(), p, s, n);
                            iW.storeCombat(p, m, s, n);
                        } 
                        repaint();
                        break;
                    } 
                }
            });
        
        delay(1);
        combatSequence();
    }
    
    /**
     * Pauses the program for a specified number of seconds
     * @param time  desired pause time in seconds
     */
    private void delay(double time) {
        int delay = (int)(time * 1000);

        try {
            Thread.sleep(delay);
        } catch (Exception e) {}
    }

    private void combatSequence () {
        turnPhase = 0;

        while(turnPhase >= 0) {
            if (turnPhase == 1) {
                //Makes combat more exciting by adding random damage modifier
                int bonus = (int)(Math.random()*5);
                int damage = p.attack() + bonus;
                boolean survive = m.ouchie(damage);//damages monster

                //Sets feedback for user
                String message = "You attack, dealing " + (damage)+ " damage";
                if (survive == true) {
                    message = message + "!";
                    refreshWindow(message, p, m);
                } else {
                    message = message + " and the monster is dead!";
                    refreshWindow(message, p, m);
                    delay(1);
                    turnPhase = -1;
                    p.setCombat(false);
                    gW.displayWindow(p, s, n);
                    dispose();
                }

                turnPhase = 2;
                repaint();
                delay(1.5);
            } else if (turnPhase == 2) {
                p.setHealth(p.getHealth() - m.getAttack());
                String message = "The "+m.getName()+" attacks you, dealing " + (m.getAttack())+ " damage";
                refreshWindow(message, p, m);
                repaint();

                if (p.getHealth() <= 0) {
                    gOW.displayWindow(p.getName(), "Killed by " + m.getName());
                    turnPhase = -1;
                    dispose();
                }
            }
        }
    }

    /**
     * Updates variables based on what is passed in and then repaints the screen
     */
    public void refreshWindow(String battleMessage, Player play, Monster monster) {
        combatMessage = battleMessage;
        p = play;
        m = monster;
        play.setCombat(true);
        CombatWindow thisWindow = this;

        canvas.repaint();
    }

    /*
     * centerStringX finds the x coordinate needed to center a String in the window.
     */
    private int centerStringStartX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 - textWidth/2;
        return textX;
    }

    /**
     * Rather than centering the start of the string, centers the end
     * of it. Kind of. Basically it allows me to make a symmetrical
     * window. Poorly named but you don't really need to understand this one
     * so just talk to me later to get the full story.
     */
    private int centerStringEndX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 + textWidth/2;
        return textX;
    }

    /*
     * Panel inside frame that holds drawn graphics
     */
    private class CombatDisplay extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            int x = centerStringStartX(p.getName(), CANVAS_WIDTH/3, g);//centers in the first
            //third of the window
            g.drawString(p.getName(), x, 30);
            x = CANVAS_WIDTH - centerStringEndX(m.getName(), CANVAS_WIDTH/3, g);
            g.drawString(m.getName(), x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 28)); //Displays monster name and symbol
            x = centerStringStartX("@", CANVAS_WIDTH/3, g);//Displays them symmetrically across from player
            g.drawString("@", x, 60);
            x = CANVAS_WIDTH - centerStringEndX("@", CANVAS_WIDTH/3, g);
            g.drawString(m.getSymbol(), x, 60);

            //Displays user stats in yellow under user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String var = "HP:" + p.getHealth();
            x = 30;
            g.drawString(var, x, 90);
            var = "Attack:" + p.getAttack();
            g.drawString(var, x, 110);
            var = "Weapon Durability:" + p.getDur();
            g.drawString(var, x, 130);

            //Displays monster stats in red under monster name and symbol
            g.setColor(Color.RED);
            var = "HP:" + m.getHP();
            x = CANVAS_WIDTH - 200;
            g.drawString(var, x, 90);
            var = "Attack:" + m.getAttack();
            g.drawString(var, x, 110);

            //Shows info message to user about what just happened
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            x = centerStringStartX(combatMessage, CANVAS_WIDTH, g);
            g.drawString(combatMessage, x, CANVAS_HEIGHT/2);

            //draws list of actions
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            if (menuSelect == 0) {
                menuString = ">Attack          Items          Magic          Flee";
            } else if (menuSelect == 1) {
                menuString = " Attack         >Items          Magic          Flee";
            } else if (menuSelect == 2){
                menuString = " Attack          Items         >Magic          Flee";
            } else if (menuSelect == 3) {
                menuString = " Attack          Items          Magic         >Flee";
            }
            x = centerStringStartX(menuString, CANVAS_WIDTH, g);
            g.drawString(menuString, x, 250);
        }
    }
}

