import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Displays a window with a demo image inside of it
 * 
 * Variable Dictionary:
 * logo             the subcomponent of the window holding the image
 * imgLogoFilename  path to find image
 * imgLogo          where the actual loaded image is stored
 * tutorialButton   button to go to tutorial window
 * playButton       button to go to game window
 * LOGO_WIDTH       static width of logo MUST BE IMAGE WIDTH (I think?)
 * LOGO_HEIGHT      static height of logo MUST BE IMAGE height (I think?)
 */
public class MenuWindow extends JFrame
{
    public static final int LOGO_WIDTH  = 500;
    public static final int LOGO_HEIGHT = 250;

    private ImageFrame logo;
    private String imgLogoFilename = "images/logo.jpg";
    private BufferedImage imgLogo;
    
    public JButton tutorialButton;
    public JButton playButton;
    
    /**
     * Basically finds and loads the image and sets up the components
     * of the window including two buttons at the bottom and the logo
     * panel.
     */
    public void displayWindow() {
        logo = new ImageFrame();//Creates subcomponent panel for image
        logo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));//Sets logo's size

        JPanel buttonPane = new JPanel(new FlowLayout());
        tutorialButton = new JButton("Tutorial ");
        buttonPane.add(tutorialButton);
        
        playButton = new JButton("Play Game ");
        buttonPane.add(playButton);
        
        //loads image
        try {
            imgLogo = ImageIO.read(new File("/Users/Ben/Desktop/School/SE/csw-kramer-quest/images/logo_ex.jpg"));
        } catch (IOException e) {}
        
        //Sets up where all the components in the container go
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(logo, BorderLayout.CENTER);
        
        //sets basic info about the window and shows it
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        pack();
        setVisible(true);
    }
    
    /**
     * Draws the image in a panel inside the larger frame
     */
    private class ImageFrame extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            g.drawImage(imgLogo, 0, 0, null);
        }
    }
}