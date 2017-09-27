import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CombatWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 750;//Sets size of window
    public static final int CANVAS_HEIGHT = 450;
    
    public JButton runButton;
    public JButton attackButton;
    
    private CombatDisplay canvas;
    
    private String pName;
    private String mName;
    private String combatMessage = "";
    private int pHP;
    private double mHP;
    private int pAttack;
    private double mAttack;
    private int pDur;
    private String mSym;

    public void displayWindow(String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        pName = playerName;
        mName = monster.getName();
        String combatMessage = "";
        pHP = pHealth;
        mHP = monster.getHP();
        pAttack = pAtk;
        mAttack = monster.getAttack();
        pDur = wD;
        mSym = monster.getSymbol();

        canvas = new CombatDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        JPanel buttonPane = new JPanel(new FlowLayout());
        attackButton = new JButton("Attack ");
        buttonPane.add(attackButton);
        
        runButton = new JButton("Run Away ");
        buttonPane.add(runButton);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Battle");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }

    public void refreshWindow(String battleMessage, String playerName, Monster monster, int pHealth, int pAtk, int wD) {
        combatMessage = battleMessage;
        pName = playerName;
        String combatMessage = "";
        pHP = pHealth;
        mHP = monster.getHP();
        pAttack = pAtk;
        pDur = wD;

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

    private int inLineStringX(String text, int distFromLeft, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = distFromLeft + textWidth/2;
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

            g.setColor(Color.WHITE);//Displays username and score
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            int x = centerStringX(pName, CANVAS_WIDTH/3, g);
            g.drawString(pName, x, 30);
            x = centerStringX(mName, CANVAS_WIDTH - CANVAS_WIDTH/3, g);
            g.drawString(mName, x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 28)); //Displays gameboard
            x = centerStringX("@", CANVAS_WIDTH/3, g);
            g.drawString("@", x, 60);
            x = CANVAS_WIDTH - x;
            g.drawString(mSym, x, 60);

            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String var = "HP:" + pHP;
            x = inLineStringX(var, 30, g);
            g.drawString(var, x, 90);
            var = "Attack:" + pAttack;
            x = inLineStringX(var, 30, g);
            g.drawString(var, x, 110);
            var = "Weapon Durability:" + pDur;
            x = inLineStringX(var, 30, g);
            g.drawString(var, x, 130);

            g.setColor(Color.RED);
            var = "HP:" + mHP;
            x = inLineStringX(var, CANVAS_WIDTH - 30, g);
            g.drawString(var, x, 90);
            var = "Attack:" + mAttack;
            x = inLineStringX(var, CANVAS_WIDTH - 30, g);
            g.drawString(var, x, 110);

            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            x = centerStringX(combatMessage, CANVAS_WIDTH, g);
            g.drawString(combatMessage, x, CANVAS_HEIGHT/2);
        }
    }
}