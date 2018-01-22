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
                    if (pageNumber < 4) {
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
            tutorial = new String[9];
            tutorial[0] = "The world of Wefwef is full of monsters and seemingly endless rooms.";
            tutorial[1] = "The W key moves up, D moves right, A moves left, and S moves down.";
            tutorial[2] = "Wefwef can sense your uncertainty and wants to keep you safe from monsters.";
            tutorial[3] = "After every move, press enter to lock in and run your desired advancement.";
            tutorial[4] = "Be careful though, after every move of yours, the monsters move as well.";
            tutorial[5] = "Alphabetical characters denote monsters. You are represented by the @ symbol.";
            tutorial[6] = "'#' is a door forward and '^' is a door backward. '|' and '_' represent walls.";
            tutorial[7] = "You will be presented with several choices in the form of a menu. Change your";
            tutorial[8] = "selection with the WASD keys and confirm with the enter key.";
        } else if (page == 3) {
            tutorial = new String[9];
            tutorial[0] = "The old dungeon contains 20 rooms of varying construction.";
            tutorial[1] = "As you descend to the lower floors, you will be faced with increasingly dangerous enemies.";
            tutorial[2] = "Luckily, the ruins also contains equipment that can be repurposed for your survival.";
            tutorial[3] = "Food and medicine are aplenty, left behind by the late explorers that came before.";
            tutorial[4] = "Weapons left over from the old civilization are ready for you to use to defend yourself.";
            tutorial[5] = "Take up arms and slay the creatures of the dark to continue your descent safely.";
            tutorial[6] = "Treasure--like weapons and other item--is shown on the map by the '$' character.";
            tutorial[7] = "You can access and close your inventory by pressing I and equip/use items like a menu.";
            tutorial[8] = "The backspace key allows you to destroy items. Be careful you don't throw away valuables!";
        } else if (page == 4) {
            tutorial = new String[5];
            tutorial[0] = "The monsters will not let you cut them down without a fight. If you encounter";
            tutorial[1] = "an enemy too strong for you to handle, you may want to run, lest you risk your demise.";
            tutorial[2] = "Keep in mind however that fleeing from a duel you requested is the mark of a coward";
            tutorial[3] = "and will not be permitted. You must train for the final showdown with the beast that";
            tutorial[4] = "guards the idol in order to claim it as your own.";
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
            String txt = "";
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            
            if (pageNumber == 1) {
                txt = "The world of Wefwef:";
            } else if (pageNumber == 2) {
                txt = "Moving through the dungeon:";
            } else if (pageNumber == 3) {
                txt = "Potions, Weapons, Health, and More:";
            } else if (pageNumber == 4) {
                txt = "Beware...";
            }
            x = centerStringX(txt, CANVAS_WIDTH, g);
            g.drawString(txt, x, 20);
            
            setTutorialBody(pageNumber);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            for (int i = 0; i < tutorial.length; i++) {
                txt = tutorial[i];
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (50 + 30*i));
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 14));
            txt = "Page "+pageNumber+" out of 4.";
            if (pageNumber != 4) {
                txt += " Press enter to continue.";
            } else {
                txt += " Click 'Return to Menu'.";
            }
            x = centerStringX(txt, CANVAS_WIDTH, g);
            g.drawString(txt, x, CANVAS_HEIGHT-30);
        }
    }
}
