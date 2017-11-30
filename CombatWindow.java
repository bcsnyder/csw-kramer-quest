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
    public static final int CANVAS_HEIGHT = 600;

    private CombatDisplay canvas;//Subcomponent where graphics displayed

    private Player play;
    private String pName;
    private String mName;
    private String combatMessage = "";
    private String menuString;
    private int pHP;
    private double mHP;
    private int pAttack;
    private double mAttack;
    private int pDur;
    int menuSelect = 0;
    private char mSym;
    private GameplayWindow gW = new GameplayWindow();
    private GameOverWindow gOW = new GameOverWindow();
    boolean fleeCondition;

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
    public void displayWindow(Player player, Monster monster, Stage st, int num, boolean flee) {
        play = player;
        pName = player.getName();
        mName = monster.getName();
        String combatMessage = "";
        pHP = player.getHealth();
        mHP = monster.getHP();
        pAttack = player.getAttack();
        mAttack = monster.getAttack();
        pDur = player.getDur();
        mSym = monster.getSymbol();
        play.setCombat(true);
        CombatWindow thisWindow = this;
        fleeCondition = flee;

        canvas = new CombatDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        //Creates menu system
        String menuString = ">Attack          Items          Magic          Flee";

        Container cp = getContentPane();
        cp.add(canvas);

        setTitle("Combat");  //JFrame sets the title of outer frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();
        setVisible(true);    //Displays window
        setFocusable(true);
        setLocationRelativeTo(null);     //Puts the JFrame in the middle of the screen @Francis

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
                        if (menuSelect < 3 && menuSelect != -1) {
                            menuSelect = menuSelect + 1;
                        }
                        repaint();
                        break;
                        case KeyEvent.VK_ENTER:
                        if (menuSelect == 0) {
                            //Makes combat more exciting by adding random damage modifier
                            int bonus = (int)(Math.random()*5);
                            int damage = play.attack() + bonus;
                            boolean survive = monster.ouchie(damage);//damages monster

                            //Sets feedback for user
                            String message = "You swing your " +play.weaponName() +", dealing " + (damage)+ " damage";
                            if (survive == true) {
                                message = message + "!";
                                refreshWindow(message, play, monster);
                            } else {
                                message = message + " and the " +monster.getName() +" has been slain!";
                                play.maxHealthAdd(monster.getExp());
                                play.setHealth(play.getHealth() + monster.getExp());
                                refreshWindow(message, play, monster);
                                play.setCombat(false);
                                if (monster.getName().equals("Boss")) {
                                    st.getRoom(num).addTile(monster.getX(),monster.getY(),new Idol());
                                } else {
                                    st.getRoom(num).removeTile(monster.getX(),monster.getY());
                                }
                                gW.displayWindow(play, st, num);
                                dispose();
                            }
                        } else if (menuSelect == 3) { 
                            if (fleeCondition == true) {
                              int rng = (int)(Math.random() * 100);
                              if (rng < 41) {
                                 dispose();
                                 play.setCombat(false);
                                 gW.displayWindow(play, st, num);   
                                  
                                }
                              String message = "You tried to run but the monster blocked your path!";
                              refreshWindow(message, play, monster);
                            } else {
                               String message = "You cannot run from a fight you started!";
                               refreshWindow(message, play, monster);
                            }
                           
                        } else if (menuSelect == 1) {
                            dispose();
                            InventoryWindow iW = new InventoryWindow();
                            iW.displayWindow(play.getInventory(), play, st, num);
                            iW.storeCombat(play, monster, st, num, flee);
                        } else if (menuSelect == -1) {
                            play.setHealth(play.getHealth() - monster.getAttack());
                            String message = "The " +monster.getName() +" attacks and deals "+monster.getAttack()+" damage to you!";
                            refreshWindow(message, play, monster);

                            if (play.getHealth() <= 0) {
                                gOW.displayWindow(play.getName(), "Killed by " + monster.getName());
                                dispose();
                            } 
                            refreshWindow(message, play, monster);
                        }
                        if (menuSelect == -1) {
                            menuSelect = 0;
                        } else {
                            menuSelect = -1;
                        }
                        repaint();
                        break;
                    } 
                }
            });
    }

    /**
     * Updates variables based on what is passed in and then repaints the screen
     */
    public void refreshWindow(String battleMessage, Player player, Monster monster) {
        play = player;
        combatMessage = battleMessage;
        pName = player.getName();
        mName = monster.getName();
        String combatMessage = "";
        pHP = player.getHealth();
        mHP = monster.getHP();
        pAttack = player.getAttack();
        mAttack = monster.getAttack();
        pDur = player.getDur();
        mSym = monster.getSymbol();

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
            int x = centerStringStartX(pName, CANVAS_WIDTH/3, g);//centers in the first
            //third of the window
            g.drawString(pName, x, 30);
            x = CANVAS_WIDTH - centerStringEndX(mName, CANVAS_WIDTH/3, g);
            g.drawString(mName, x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 28)); //Displays monster name and symbol
            x = centerStringStartX("@", CANVAS_WIDTH/3, g);//Displays them symmetrically across from player
            g.drawString("@", x, 60);
            x = CANVAS_WIDTH - centerStringEndX("@", CANVAS_WIDTH/3, g);
            g.drawString(mSym+"", x, 60);

            //Displays user stats in yellow under user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String var = "HP:" + pHP;
            x = 30;
            g.drawString(var, x, 90);
            var = "Attack:" + pAttack;
            g.drawString(var, x, 110);
            var = "Weapon: " + play.weaponName();
            g.drawString(var, x, 130);
            var = "Weapon Durability:" + pDur;
            g.drawString(var, x, 150);

            //Displays monster stats in red under monster name and symbol
            g.setColor(Color.RED);
            var = "HP:" + mHP;
            x = CANVAS_WIDTH - 200;
            g.drawString(var, x, 90);
            var = "Attack:" + mAttack;
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
            } else if (menuSelect == -1) {
                menuString = ">Next Turn";
            }
            x = centerStringStartX(menuString, CANVAS_WIDTH, g);
            g.drawString(menuString, x, 250);
        }
    }
}
