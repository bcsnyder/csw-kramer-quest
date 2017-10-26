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

    private String pName;
    private String mName;
    private String combatMessage1 = "";
    private String combatMessage2 = "";
    private int pHP;
    private double mHP;
    private int pAttack;
    private double mAttack;
    private int pDur;
    private String mSym;
    private GameplayWindow gW = new GameplayWindow();
    private GameOverWindow gOW = new GameOverWindow();

    /**
     * Sets all the variables needed to display combat and
     * sets up the window with its display components including
     * two buttons.
     * 
     * @param monster   the monster instance the player is fighting
     *                  contains helpful variables the program gets from it
     *                  like health
     */
    public void displayWindow(Player play, Monster monster, Stage map) {
        mName = play.getName();
        mName = monster.getName();
        String combatMessage = "";
        pHP = play.getHealth();
        mHP = monster.getHP();
        pAttack = play.getAttack();
        mAttack = monster.getAttack();
        pDur = play.getDur();
        mSym = monster.getSymbol();

        canvas = new CombatDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        //Buttons to attack or run away set up here
        JPanel buttonPane = new JPanel(new FlowLayout());
        attackButton = new JButton("Attack ");
        buttonPane.add(attackButton);
        attackButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    battle(play, monster, map);
                }
            });
        runButton = new JButton("Run Away ");
        buttonPane.add(runButton);
        runButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    dispose();
                    gW.displayWindow(play, map);
                }
            });

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Battle");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }

    public void battle(Player play, Monster monster, Stage map) {
        //Makes combat more exciting by adding random damage modifier
        int bonus = (int)(Math.random()*5);
        int damage = play.getAttack() + bonus;
        boolean survive = monster.ouchie(damage);//damages monster

        //Sets feedback for user
        String message1 = "You attack, dealing " + (damage)+ " damage";
        if (survive == true) {
            message1 = message1 + "!";
        } else {
            message1 = "You killed the " + monster.getName() + "!";
            gW.displayWindow(play, map);
            gW.refreshWindow(message1, play, map);
            dispose();
        }

        int mAttack = monster.getAttack();
        play.setHealth(play.getHealth() - mAttack);
        String message2 = "The monster attacks you dealing " + mAttack + " damage.";
        if (play.getHealth() <= 0) {
            gOW.displayWindow(play.getName(), "Killed by " + monster.getName());
            dispose();
        }
        refreshWindow(message1, message2, play, monster);
    }

    /**
     * Pauses the program for a specified number of seconds
     * @param time  desired pause time in seconds
     */
    private void delay(int time) {
        int delay = time * 1000;

        try {
            Thread.sleep(delay); //pause for 1.5 seconds
        } catch (Exception e) {}
    }

    /**
     * Updates variables based on what is passed in and then repaints the screen
     */
    public void refreshWindow(String message1, String message2, Player play, Monster monster) {
        combatMessage1 = message1;
        combatMessage2 = message2;
        pName = play.getName();
        mName = monster.getName();
        pHP = play.getHealth();
        mHP = monster.getHP();
        pAttack = play.getAttack();
        mAttack = monster.getAttack();
        pDur = play.getDur();
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
            g.drawString(mSym, x, 60);

            //Displays user stats in yellow under user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String var = "HP:" + pHP;
            x = 30;
            g.drawString(var, x, 90);
            var = "Attack:" + pAttack;
            g.drawString(var, x, 110);
            var = "Weapon Durability:" + pDur;
            g.drawString(var, x, 130);

            //Displays monster stats in red under monster name and symbol
            g.setColor(Color.RED);
            var = "HP:" + mHP;
            x = CANVAS_WIDTH - 200;
            g.drawString(var, x, 90);
            var = "Attack:" + mAttack;
            g.drawString(var, x, 110);

            //Shows info message to user about what just happened
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            x = centerStringStartX(combatMessage1, CANVAS_WIDTH, g);
            g.drawString(combatMessage1, x, CANVAS_HEIGHT/2);
            x = centerStringStartX(combatMessage2, CANVAS_WIDTH, g);
            g.drawString(combatMessage2, x, CANVAS_HEIGHT/2 + 25);
            
        }
    }
}
