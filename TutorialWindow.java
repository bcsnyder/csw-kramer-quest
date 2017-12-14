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
    //Sets size of window
    public static final int CANVAS_WIDTH  = 800;
    public static final int CANVAS_HEIGHT = 500;

    MenuWindow menuWind;

    private String[] tutorial;
    private TutorialText canvas;

    /**
     * Sets up tutorial window's components and shows them.
     */
    public void displayWindow(SoundSystem musicPlayer) {
        //Constructs the drawing canvas
        canvas = new TutorialText();    
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        JPanel buttonPane = new JPanel(new FlowLayout());
        JButton menuButton = new JButton("Return to Menu ");
        buttonPane.add(menuButton);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                menuWind = new MenuWindow();
                dispose();
                menuWind.displayWindow(musicPlayer);
            }
        });

        //Sets the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE); //Handles the CLOSE button
        pack(); //Either pack() the components or setSize()
        setTitle("Tutorial"); //JFrame sets the title of outer frame
        setVisible(true); //Displays window
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis
    }

    /**
     * Sets the text line by line for the tutorial as an array
     */
    private void setTutorial() {
        tutorial = new String[8];
        tutorial[0] = "Hello adventurer, welcome to the fantastic world of wefwef.";
        tutorial[1] = "You are stranded in a dark dungeon and must find the idol of ___ on the lowest floor";
        tutorial[2] = "Along the way you will find treasure ($) and fight monsters (text characters).";
        tutorial[3] = "Use the WASD keys to move around or select different options on the menu.";
        tutorial[4] = "Use enter to confirm your selection. # is a door forward, ^ is a door backward.";
        tutorial[5] = "Press i to open your inventory. As you travel your stamina will deplete.";
        tutorial[6] = "Food will replenish your stamina, but if it gets too low you could starve.";
        tutorial[7] = "Fighting monsters will increase your maximum health and potions will heal you.";
    }

    /**
     * Finds the x coordinate needed to center a String in the window.
     */
    private int centerStringX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 - textWidth/2;
        return textX;
    }

    /**
     * Panel inside frame that holds drawn graphics
     */
    private class TutorialText extends JPanel {
        //Overrides paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //Paints base background
            setBackground(Color.BLACK); //Sets background color for this JPanel

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
