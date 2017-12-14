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
    public static final int CANVAS_WIDTH  = 1000;
    public static final int CANVAS_HEIGHT = 500;

    MenuWindow menuWind;

    private String[] tutorial;
    private int pageNumber;
    private TutorialText canvas;

    /**
     * Sets up tutorial window's components and shows them.
     */
    public void displayWindow(SoundSystem musicPlayer) {
        //Constructs the drawing canvas
        canvas = new TutorialText();    
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        pageNumber = 1;
        
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

        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                        if (pageNumber < 5) {
                            pageNumber++;
                            repaint();
                        }
                        break;
                    }
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
        setFocusable(true);
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis
    }

    /**
     * Sets the text line by line for the tutorial as an array
     */
    private void setTutorialBody(int page) {
        if (page == 1) {
            tutorial = new String[8];
            tutorial[0] = "Hello adventurer, welcome to the fantastic world of wefwef.";
            tutorial[1] = "It is a world full of evil monsters and items of great magical power.";
            tutorial[2] = "You have been searching around the ruins of a keep for an ancient idol.";
            tutorial[3] = "Suddenly the floor collapses and you fall into some buried chamber, dazed.";
            tutorial[4] = "When you regain your senses, you don't see the hole you fell into.";
            tutorial[5] = "Looking around, you see the room you are in is crumbling but well kept.";
            tutorial[6] = "There are lit torches in the walls and you see an eerie humanoid shadow.";
            tutorial[7] = "You have a strange feeling that the idol you're searching for is nearby...";
        } else if (page == 2) {
            tutorial = new String[7];
            tutorial[0] = "Hello adventurer, welcome to the fantastic world of wefwef.";
            tutorial[1] = "It is a world full of evil monsters and items of great magical power.";
            tutorial[2] = "You have been searching around the ruins of a keep for an ancient idol.";
            tutorial[3] = "Suddenly the floor collapses and you fall into some buried chamber, dazed.";
            tutorial[4] = "When you regain your senses, you don't see the hole you fell into.";
            tutorial[5] = "Looking around, you see the room you are in is crumbling but well kept.";
            tutorial[6] = "There are lit torches in the walls and you see an eerie humanoid shadow.";
        }
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
            int x;
            String txt;

            if (pageNumber == 1) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Monospaced", Font.PLAIN, 24));
                txt = "The world of Wefwef:";
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, 20);

                setTutorialBody(1);
                g.setFont(new Font("Monospaced", Font.PLAIN, 18));
                for (int i = 0; i < tutorial.length; i++) {
                    txt = tutorial[i];
                    x = centerStringX(txt, CANVAS_WIDTH, g);
                    g.drawString(txt, x, (50 + 30*i));
                }
            } else if (pageNumber == 2) {
                g.setColor(Color.WHITE);
                g.setFont(new Font("Monospaced", Font.PLAIN, 24));
                txt = "Moving through the dungeon:";
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, 20);

                setTutorialBody(2);
                g.setFont(new Font("Monospaced", Font.PLAIN, 18));
                for (int i = 0; i < tutorial.length; i++) {
                    txt = tutorial[i];
                    x = centerStringX(txt, CANVAS_WIDTH, g);
                    g.drawString(txt, x, (50 + 30*i));
                }
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            txt = "Page "+pageNumber+" out of 4. Press enter to continue.";
            x = centerStringX(txt, CANVAS_WIDTH, g);
            g.drawString(txt, x, CANVAS_HEIGHT-30);
        }
    }
}
