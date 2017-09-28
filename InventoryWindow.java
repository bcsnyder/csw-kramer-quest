import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class InventoryWindow extends JFrame
{
    public static final int CANVAS_WIDTH  = 800;//Sets size of window
    public static final int CANVAS_HEIGHT = 500;
    
    private ArrayList<Item> inventory;
    private InventoryDisplay canvas;
    private ArrayList<String> inventoryText;
    
    public JButton returnButton;

    public void displayWindow(ArrayList<Item> inv) {
        inventory = inv;
        
        canvas = new InventoryDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        JPanel buttonPane = new JPanel(new FlowLayout());
        returnButton = new JButton("Return to Game ");
        buttonPane.add(returnButton);
        
        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("Inventory");  //JFrame sets the title of outer frame
        setVisible(true);    //Displays window
    }

    private void setInvTxt() {
        inventoryText = new ArrayList<String>();
        
        for (int i = 0; i < inventory.size(); i++) {
            String type = inventory.get(i).getType();
            String name = inventory.get(i).getName();
            inventoryText.add(type + ": " + name);
        }
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
    private class InventoryDisplay extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays username and score
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            setInvTxt();
            String txt;
            int x;
            for (int i = 0; i < inventoryText.size(); i++) {
                txt = inventoryText.get(i);
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*i));
            }
        }
    }
}
