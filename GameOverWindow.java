import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays game over window with debrief message and button
 * to go back to menu. Yep, pretty simple.
 */
public class GameOverWindow extends JFrame
{
    //Sets size of window
    public static final int CANVAS_WIDTH  = 1200;
    public static final int CANVAS_HEIGHT = 500;

    private String[] debrief;
    private DebriefText canvas;

    private MenuWindow mW = new MenuWindow();
    public JButton menuButton;

    public void displayWindow(Player player, String deathMessage) {
        canvas = new DebriefText(); //Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        try {
            player.getSoundtrack().stopThread();
        } catch (InterruptedException e) {}
        
        setDebrief(player.getName(), deathMessage);
        JPanel buttonPane = new JPanel(new FlowLayout());
        menuButton = new JButton("Return to Menu ");
        buttonPane.add(menuButton);
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mW.displayWindow(new SoundSystem());
                dispose();
            }
        });

        //Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE); //Handle the CLOSE button
        pack(); //Either pack() the components; or setSize()
        setTitle("Game Over"); //JFrame sets the title of outer frame
        setVisible(true); //Displays window
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis
    }

    /**
     * creates debrief message with name and
     * cause of death. Each element in the 
     * array is another line of text.
     */
    private void setDebrief(String name, String message) {
        if (message.equals("win")) {
            debrief = new String[4];
            debrief[0] = "You, " + name + ", disappear in a flash of light.";
            debrief[1] = "You reappear in the outside world, holding the golden idol.";
            debrief[2] = "You hop on a conveniently placed horse and ride into the sunset.";
            debrief[3] = "Congrats, you win! Return to the menu if you want to play again!";
        } else {
            debrief = new String[3];
            debrief[0] = "RIP " + name;
            debrief[1] = message;
            debrief[2] = "Return to the menu to try again.";
        }
    }

    /**
     * centerStringX finds the x coordinate needed to center a String in the window.
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
    private class DebriefText extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            //Paint base background
            super.paintComponent(g); 
            setBackground(Color.BLACK); 
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 28));
            String txt;
            int x;
            
            //Splits debrief message into separate lines of text and displays
            for (int i = 0; i < debrief.length; i++) {
                txt = debrief[i];
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*i));
            }
        }
    }
}
