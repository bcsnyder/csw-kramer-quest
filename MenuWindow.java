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
 * LOGO_WIDTH       static width of logo MUST BE IMAGE WIDTH (I think?)
 * LOGO_HEIGHT      static height of logo MUST BE IMAGE height (I think?)
 */
public class MenuWindow extends JFrame
{
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
    public void displayWindow() {
        logo = new ImageFrame();//Creates subcomponent panel for image
        logo.setPreferredSize(new Dimension(LOGO_WIDTH, LOGO_HEIGHT));//Sets logo's size

        //Creates two buttons and decides their functionality
        JPanel buttonPane = new JPanel(new FlowLayout());
        JButton tutorialButton = new JButton("Tutorial ");
        buttonPane.add(tutorialButton);
        tutorialButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    tutorialWind = new TutorialWindow();
                    dispose();
                    tutorialWind.displayWindow();
                }
            });

        JButton playButton = new JButton("Play Game ");
        buttonPane.add(playButton);
        playButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    startUp();
                    gameWind = new GameplayWindow();
                    dispose();
                    gameWind.displayWindow(newPlayer, newStage, 0);
                }
            });

        //playMusic();

        //loads image
        try {
            imgLogo = ImageIO.read(new File(imgLogoFilename));
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
        setLocationRelativeTo(null);     //Puts the JFrame in the middle of the screen @Francis
    }

    /**
     * Sets up the music player and begins playing the menu
     * theme.
     */
    private void playMusic() {
        SoundSystem player = new SoundSystem();
        String audioFilePath = "Sound/Menu_Theme.wav";
        player.setPath(audioFilePath);
        player.play();
    }

    private void startUp() {
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

        int xPos = 1;
        int yPos = 1;

        while(currRoom.getTile(xPos, yPos) != '.') {
            while(currRoom.getTile(xPos, yPos) != '.') {
                yPos += 1;
            }
            xPos += 1;
        }

        newPlayer.setPos(xPos, yPos);
        currRoom.addTile(xPos, yPos, newPlayer);
        newPlayer.setRoom(currRoom);
    }

    private void chooseClass() {

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
