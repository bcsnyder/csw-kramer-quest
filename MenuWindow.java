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
<<<<<<< HEAD
        
        playButton = new JButton("Play Game ");
=======
        tutorialButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    TutorialWindow tutorial = new TutorialWindow();
                    dispose();
                    tutorial.displayTutorial();
                }
            });
        JButton playButton = new JButton("Play Game ");
>>>>>>> 10db292d1d28293c66403aaa912b5b8285fb4a81
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