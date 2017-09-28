import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameOverWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 800;//Sets size of window
    public static final int CANVAS_HEIGHT = 500;
    
    private String[] debrief;
    private DebriefText canvas;
    
    public JButton menuButton;

    public void displayWindow(String playerName, String deathMessage) {
        canvas = new DebriefText();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        setDebrief(playerName, deathMessage);
        JPanel buttonPane = new JPanel(new FlowLayout());
        menuButton = new JButton("Return to Menu ");
        buttonPane.add(menuButton);
        
        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Game Over");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }

    private void setDebrief(String name, String message) {
        debrief = new String[3];
        debrief[0] = "RIP " + name;
        debrief[1] = message;
        debrief[2] = "Return to the menu to try again.";
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
    private class DebriefText extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays username and score
            g.setFont(new Font("Monospaced", Font.PLAIN, 28));
            String txt;
            int x;
            for (int i = 0; i < debrief.length; i++) {
                txt = debrief[i];
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*i));
            }
        }
    }
}