import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays a tutorial window with demo text inside of it
 * 
 * Variable Dictionary:
 * canvas           the subcomponent of the window holding the text
 * tutorial         the tutorial text to display
 * menuButton       button to go to menu window
 * CANVAS_WIDTH     static width of canvas MUST BE IMAGE WIDTH (I think?)
 * CANVAS_HEIGHT    static height of canvas MUST BE IMAGE height (I think?)
 */
public class TutorialWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 800;//Sets size of window
    public static final int CANVAS_HEIGHT = 500;
    
    private String[] tutorial;
    private TutorialText canvas;
    
    public JButton menuButton;
    
    /**
     * Sets up tutorial window's components and shows them.
     */
    public void displayWindow() {
        canvas = new TutorialText();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
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
        setTitle("Tutorial");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }
    
    /**
     * Sets the text line by line for the tutorial as an array
     */
    private void setTutorial() {
        tutorial = new String[5];
        tutorial[0] = "Hello player, welcome to the world of ____";
        tutorial[1] = "You are stranded in a dark dungeon and must find the idol of ___ on the lowest floor";
        tutorial[2] = "Along the way you will find treasure ($) and fight monsters (text characters)";
        tutorial[3] = "blah";
        tutorial[4] = "blah";
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
    private class TutorialText extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            setTutorial();
            String txt;
            int x;
            //Displays the tutorial text line by line, however long it is
            for (int i = 0; i < tutorial.length; i++) {
                txt = tutorial[i];
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*i));
            }
        }
    }
}
