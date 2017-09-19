import java.awt.*;       
import java.awt.event.*; 
import javax.swing.*;   

public class SampleWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 750;
    public static final int CANVAS_HEIGHT = 500;
    
    private DrawCanvas canvas;
    String room = "_ _ _ _ _ _ _ | . . . . . | # . . @ . . | | . . . . $ | _ _ _ _ _ _ _ ";
    String name = "Player1";
    
    public SampleWindow() {
        canvas = new DrawCanvas();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("PPP Dungeon Crawler");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show
    }

    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.BLACK);  // set background color for this JPanel
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.drawString(name + ", you are on level 2", 230, 20);
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            System.out.println(room);
            g.drawString(room.substring(0,13), 50, 50);
            g.drawString(room.substring(14,27), 50, 80);
            g.drawString(room.substring(28,41), 50, 110);
            g.drawString(room.substring(42,55), 50, 140);
            g.drawString(room.substring(56,69), 50, 150);
        }
    }

    // The entry main method
    public static void main(String[] args) {
        // Run the GUI codes on the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {public void run() {new SampleWindow();}});
    }
}
