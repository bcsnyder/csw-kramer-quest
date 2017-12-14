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
 * LOGO_WIDTH       static width of logo MUST BE IMAGE WIDTH
 * LOGO_HEIGHT      static height of logo MUST BE IMAGE HEIGHT
 */
public class MenuWindow extends JFrame {
    public static final int LOGO_WIDTH  = 779;
    public static final int LOGO_HEIGHT = 447;

    private GameplayWindow gameWind;
    private TutorialWindow tutorialWind;
    private Player newPlayer;
    private Stage newStage;

    private ImageFrame logo;
    private String imgLogoFilename = "images/logo_ex.png";
    private BufferedImage imgLogo;

    /**
     * Basically finds and loads the image and sets up the components
     * of the window including two buttons at the bottom and the logo
     * panel.
     */
    public void displayWindow(SoundSystem backgroundMusic) {
        logo = new ImageFrame();//Creates subcomponent panel for image
        logo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));//Sets logo's size

        SoundSystem soundtrack = backgroundMusic;
        if (!soundtrack.isPlayingMenuMusic()) {
            String audioFilePath = "Sound/Menu_Theme.wav";
            soundtrack.setPath(audioFilePath);
            soundtrack.playLoop1();
        }

        //Creates two buttons and decides their functionality
        JPanel buttonPane = new JPanel(new FlowLayout());
        JButton tutorialButton = new JButton("Tutorial ");
        buttonPane.add(tutorialButton);
        tutorialButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    tutorialWind = new TutorialWindow();
                    dispose();
                    tutorialWind.displayWindow(soundtrack);
                }
            });

        JButton playButton = new JButton("Play Game ");
        buttonPane.add(playButton);
        playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    soundtrack.stop();
                    String audioFilePath1 = "Sound/Blue_Orchid_Pt1.wav";
                    soundtrack.setPath(audioFilePath1);
                    String audioFilePath2 = "Sound/Blue_Orchid_Pt2.wav";
                    soundtrack.setPath2(audioFilePath2);
                    soundtrack.playLoop2();
                    startUp(soundtrack);
                    gameWind = new GameplayWindow();
                    dispose();
                    gameWind.displayWindow(newPlayer, newStage, 0);
                }
            });

        //Loads image
        try {
            imgLogo = ImageIO.read(new File(imgLogoFilename));
        } catch (IOException e) {}

        //Sets up where all the components in the container go
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(buttonPane, BorderLayout.SOUTH);
        cp.add(logo, BorderLayout.CENTER);

        //ets basic info about the window and shows it
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        pack();
        setVisible(true);
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis
    }

    private void startUp(SoundSystem backgroundMusic) {
        newStage = new Stage(20);
        Room currRoom = newStage.getRoom(0);

        newPlayer = new Player();
        newPlayer.setName("Unnamed");
        newPlayer.setHealth(25);
        newPlayer.setAttack(2);
        newPlayer.setStamina(100);
        newPlayer.setRoom(currRoom);
        newPlayer.setCombat(false);
        newPlayer.usedItem(false);
        newPlayer.setWeapon(new Fists());
        newPlayer.setMaxHealth(25);
        newPlayer.setExp(0);
        newPlayer.setSoundtrack(backgroundMusic);

        int xPos = 1;
        int yPos = 1;

        while(currRoom.getTile(xPos, yPos) != '.') {
            yPos += 1;
            xPos += 1;
        }

        newPlayer.setPos(xPos, yPos);
        currRoom.addTile(xPos, yPos, newPlayer);
        newPlayer.setRoom(currRoom);
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
