import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MenuWindow extends JFrame
{
    public static final int LOGO_WIDTH  = 500;
    public static final int LOGO_HEIGHT = 250;

    private ImageFrame logo;
    private String imgLogoFilename = "images/logo.jpg";
    private BufferedImage imgLogo;
    
    public JButton tutorialButton;
    public JButton playButton;

    public void displayWindow() {
        logo = new ImageFrame();
        logo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));

        JPanel buttonPane = new JPanel(new FlowLayout());
        tutorialButton = new JButton("Tutorial ");
        buttonPane.add(tutorialButton);
        
        playButton = new JButton("Play Game ");
        buttonPane.add(playButton);
        
        try {
            imgLogo = ImageIO.read(new File("/Users/Ben/Desktop/School/SE/csw-kramer-quest/images/logo_ex.jpg"));
        } catch (IOException e) {}
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(logo, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        pack();
        setVisible(true);
    }

    private class ImageFrame extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            g.drawImage(imgLogo, 0, 0, null);
        }
    }
}