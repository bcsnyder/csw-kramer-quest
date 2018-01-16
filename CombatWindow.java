import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * This window is actually important and tricky to understand
 * so listen up!! It shows the combat screen btw.
 * 
 * "Interesting Variables Only" Info Session:
 * m means monster
 * p means player
 * pDur             player's weapon's durability
 * mSym             Ascii symbol representing monster
 * combatMessage    message telling user what just happened
 */
public class CombatWindow extends JFrame
{
    //Sets size of window
    public static final int CANVAS_WIDTH  = 1000;
    public static final int CANVAS_HEIGHT = 600;

    public static final int IMAGE_WIDTH = 120;
    public static final int IMAGE_HEIGHT = 122;

    private CombatDisplay canvas; //Subcomponent where graphics displayed

    private String imgPlayerFileName = "images/Player_ex.png";
    private BufferedImage imgPlayer;
    private String imgDragonFileName = "images/Dragon_ex.png";
    private BufferedImage imgDragon;
    private String imgTrollFileName = "images/Troll.png";
    private BufferedImage imgTroll;
    private String imgSkeletonFileName = "images/Skeleton.png";
    private BufferedImage imgSkeleton;
    private String imgBossFileName = "images/Boss.png";
    private BufferedImage imgBoss;
    private String imgGremlinFileName = "images/Gremlin.png";
    private BufferedImage imgGremlin;

    private Player play;
    private String pName;
    private String mName;
    private String combatMessage = "";
    private String menuString;
    private int pHP;
    private double mHP;
    private int pAttack;
    private double mAttack;
    private int pDur;
    int menuSelect = 0;
    private char mSym;
    private GameplayWindow gW = new GameplayWindow();
    private GameOverWindow gOW = new GameOverWindow();
    boolean fleeCondition;
    private int monsterGraphicLocation;
    private int playerGraphicLocation;

    Thread monsterAnimator;
    Thread playerAnimator;

    public void setMessage (String m) {
        combatMessage = m;
    }

    /**
     * Sets all the variables needed to display combat and
     * sets up the window with its display components including
     * two buttons.
     * 
     * @param monster   the monster instance the player is fighting
     *                  contains helpful variables the program gets from it
     *                  like health
     */
    public void displayWindow(Player player, Monster monster, Stage st, int num, boolean flee) {
        play = player;
        pName = player.getName();
        mName = monster.getName();
        String combatMessage = "";
        pHP = player.getHealth();
        mHP = monster.getHP();
        pAttack = player.getAttack();
        mAttack = monster.getAttack();
        pDur = player.getDur();
        mSym = monster.getSymbol();
        play.setCombat(true);
        CombatWindow thisWindow = this;
        fleeCondition = flee;
        monsterGraphicLocation = 400;
        playerGraphicLocation = 400;

        canvas = new CombatDisplay(); //Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        //Creates menu system
        String menuString = ">Attack          Items          Pass          Flee";

        Container cp = getContentPane();
        cp.add(canvas);

        setTitle("Combat"); //JFrame sets the title of outer frame
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Handle the CLOSE button
        pack();
        setVisible(true); //Displays window
        setFocusable(true);
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis

        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_A:

                        if (menuSelect > 0) {
                            menuSelect = menuSelect - 1;
                        } else if (menuSelect == 0) {
                            menuSelect = 3;
                        }
                        repaint();
                        break;
                        case KeyEvent.VK_D:
                        if (menuSelect < 3 && menuSelect != -1) {
                            menuSelect = menuSelect + 1;
                        } else if (menuSelect == 3) {
                            menuSelect = 0;
                        }
                        repaint();
                        break;
                        case KeyEvent.VK_ENTER:
                        if (menuSelect == 0) {
                            //Makes combat more exciting by adding random damage modifier
                            int bonus = (int)(Math.random()*5);
                            int crit = (int) (Math.random() * 10) + 1; 
                            int damage = play.attack() + bonus;
                            if (crit >= 8 && crit <= 10) {
                                damage = (damage * 2);
                            }
                            boolean survive = monster.ouchie(damage);//damages monster

                            startAnimation(true);

                            String message;
                            //Sets feedback for user
                            if (crit < 8) {
                                message = "You swing your " +play.weaponName() +", dealing " + (damage)+ " damage";
                            } else {
                                message = "Your " +play.weaponName() +" lands a critical strike, dealing " +damage +" damage";
                            }
                            if (survive == true) {
                                message = message + "!";
                                refreshWindow(message, play, monster);
                            } else {
                                message = message + " and the " +monster.getName() +" has been slain!";
                                refreshWindow(message, play, monster);
                                menuSelect = -2;
                            }
                        } else if (menuSelect == 3) { 
                            if (fleeCondition == true) {
                                int rng = (int)(Math.random() * 100);
                                if (rng < 41) {
                                    dispose();
                                    play.setCombat(false);
                                    gW.displayWindow(play, st, num);   

                                }
                                String message = "You tried to run but the monster blocked your path!";
                                refreshWindow(message, play, monster);
                            } else {
                                String message = "You cannot run from a fight you started!";
                                refreshWindow(message, play, monster);
                            }
                        } else if (menuSelect == 1) {
                            dispose();
                            InventoryWindow iW = new InventoryWindow();
                            iW.displayWindow(play.getInventory(), play, st, num);
                            iW.storeCombat(play, monster, st, num, flee);
                        } else if (menuSelect == -1) {
                            int crit = (int) (Math.random() * 10) + 1; 
                            int damage = monster.getAttack();
                            if (crit >= 8 && crit <= 10) {
                                damage = (damage * 2);
                            }
                            play.setHealth(play.getHealth() - damage);
                            String message;
                            if (crit < 8) {
                                message = "The " +monster.getName() +" attacks and deals "+monster.getAttack()+" damage to you!";
                            } else {
                                message = "The " +monster.getName() +" lands a critical strike and deals "+monster.getAttack()+" damage to you!";
                            }
                            message = "The " +monster.getName() +" attacks and deals "+monster.getAttack()+" damage to you!";
                            refreshWindow(message, play, monster);

                            startAnimation(false);

                            if (play.getHealth() <= 0) {
                                gOW.displayWindow(play, "Killed by " + monster.getName());
                                dispose();
                            } 
                            refreshWindow(message, play, monster);
                        } else if (menuSelect == -2) {
                            play.maxHealthAdd(monster.getExp());
                            play.setHealth(play.getHealth() + monster.getExp());
                            play.addExp(monster.getExp());
                            play.setCombat(false);
                            if (monster.getName().equals("Boss")) {
                                st.getRoom(num).addTile(monster.getX(),monster.getY(),new Idol());
                            } else {
                                st.getRoom(num).addTile(monster.getX(),monster.getY(),randomItem());
                            }
                            gW.displayWindow(play, st, num);
                            dispose();
                        }
                        if (menuSelect == -1) {
                            menuSelect = 0;
                        } else if (menuSelect != -2) {
                            menuSelect = -1;
                        }
                        repaint();
                        break;
                    } 
                }
            });
    }

    public void startAnimation(boolean isMonster) {
        if (isMonster) {
            monsterAnimator = new Thread(new MonsterAnimation());
            monsterAnimator.start();
        } else {
            playerAnimator = new Thread(new PlayerAnimation());
            playerAnimator.start();
        }
        return;
    }

    /**
     * Updates variables based on what is passed in and then repaints the screen
     */
    public void refreshWindow(String battleMessage, Player player, Monster monster) {
        play = player;
        combatMessage = battleMessage;
        pName = player.getName();
        mName = monster.getName();
        String combatMessage = "";
        pHP = player.getHealth();
        mHP = monster.getHP();
        pAttack = player.getAttack();
        mAttack = monster.getAttack();
        pDur = player.getDur();
        mSym = monster.getSymbol();

        canvas.repaint();
    }

    /**
     * centerStringX finds the x coordinate needed to center a String in the window.
     */
    private int centerStringStartX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 - textWidth/2;
        return textX;
    }

    /**
     * Rather than centering the start of the string, centers the end
     * of it. Kind of. Basically it allows me to make a symmetrical
     * window. Poorly named but you don't really need to understand this one
     * so just talk to me later to get the full story.
     */
    private int centerStringEndX(String text, int frameWidth, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = frameWidth/2 + textWidth/2;
        return textX;
    }

    public Item randomItem() {
        Room room = play.getRoom();
        int roomNumber = room.getroomNumber(); 
        int value = (int) (Math.random() * 150 + 1);

        if (value <= 40 && value >= 1) {
            return new Bread();
        } else if (value <= 70 && value >= 41) {
            return new Potion();
        } else if (value <= 100 && value >= 71) {
            int weaponType = 0;
            if (roomNumber < 2) {
                weaponType = 0;
            } else if (roomNumber >= 2 && roomNumber < 8){
                weaponType = (int)(Math.random() * 2);
            } else if (roomNumber  >= 8 && roomNumber < 12) {
                weaponType = (int)(Math.random() * 2) + 1;
            } else if (roomNumber >= 12 && roomNumber < 17) {
                weaponType = (int)(Math.random() * 2)+ 2;
            } else if (roomNumber  >= 17 && roomNumber < 20) {
                weaponType = 3;
            }

            if (weaponType == 0) {
                return new Spear();
            } else if (weaponType == 1) {
                return new Axe();
            } else if (weaponType == 2) {
                return new Sword();
            } else if (weaponType == 3) {
                return new Musket();
            } else {
                return new Axe();
            }
        } else if (value <= 130 && value >= 101) {
            return new Steak();
        } else if (value <= 140 && value >= 131) {
            return new SmallKey();
        } else if (value <= 150 && value >= 141) {
            return new BigKey();
        } else {
            return new Bread();
        }
    }

    /**
     * Panel inside frame that holds drawn graphics
     */
    private class CombatDisplay extends JPanel {
        //Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //Paint base background
            setBackground(Color.BLACK); //Set background color for this JPanel

            g.setColor(Color.WHITE); //Displays user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            int x = centerStringStartX(pName, CANVAS_WIDTH/3, g); //Centers in the first
            //third of the window
            g.drawString(pName, x, 30);
            x = CANVAS_WIDTH - centerStringEndX(mName, CANVAS_WIDTH/3, g);
            g.drawString(mName, x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 28)); //Displays monster name and symbol
            x = centerStringStartX("@", CANVAS_WIDTH/3, g);//Displays them symmetrically across from player
            g.drawString("@", x, 60);
            x = CANVAS_WIDTH - centerStringEndX("@", CANVAS_WIDTH/3, g);
            g.drawString(mSym+"", x, 60);

            //Displays user stats in yellow under user name and symbol
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String var = "HP:" + pHP;
            x = 30;
            g.drawString(var, x, 90);
            var = "Base Attack:" + pAttack;
            g.drawString(var, x, 110);
            var = "Weapon: " + play.weaponName();
            g.drawString(var, x, 130);
            var = "Weapon Durability:" + pDur;
            g.drawString(var, x, 150);

            //Displays monster stats in red under monster name and symbol
            g.setColor(Color.RED);
            var = "HP:" + mHP;
            x = CANVAS_WIDTH - 200;
            g.drawString(var, x, 90);
            var = "Attack:" + mAttack;
            g.drawString(var, x, 110);

            //Shows info message to user about what just happened
            g.setFont(new Font("Monospaced", Font.PLAIN, 28));
            x = centerStringStartX(combatMessage, CANVAS_WIDTH, g);
            g.drawString(combatMessage, x, CANVAS_HEIGHT/2 + 50);

            //Draws list of actions
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            if (menuSelect == 0) {
                menuString = ">Attack          Items          Pass          Flee";
            } else if (menuSelect == 1) {
                menuString = " Attack         >Items          Pass          Flee";
            } else if (menuSelect == 2){
                menuString = " Attack          Items         >Pass          Flee";
            } else if (menuSelect == 3) {
                menuString = " Attack          Items          Pass         >Flee";
            } else if (menuSelect == -1 || menuSelect == -2) {
                menuString = ">Next Turn";
            }
            x = centerStringStartX(menuString, CANVAS_WIDTH, g);
            g.drawString(menuString, x, 250);

            //Gets and loads player
            x = CANVAS_WIDTH - CANVAS_WIDTH/4 * 4 + 80;           
            g.drawImage(imgPlayer, x, playerGraphicLocation, null);
            //Loads image
            try {
                imgPlayer = ImageIO.read(new File(imgPlayerFileName));
            } catch (IOException e) {}
            //Gets and loads Monster
            if (mName == "Gremlin"){
                x = CANVAS_WIDTH - CANVAS_WIDTH/4 - 40;
                g.drawImage(imgGremlin, x, monsterGraphicLocation, null);
                //Loads image
                try {
                    imgGremlin = ImageIO.read(new File(imgGremlinFileName));
                } catch (IOException e) {}
            }else if (mName == "Skeleton"){
                x = CANVAS_WIDTH - CANVAS_WIDTH/4 - 40;
                g.drawImage(imgSkeleton, x, monsterGraphicLocation, null);
                //Loads image
                try {
                    imgSkeleton = ImageIO.read(new File(imgSkeletonFileName));
                } catch (IOException e) {}
            }else if (mName == "Dragon"){  
                x = CANVAS_WIDTH - CANVAS_WIDTH/4 - 40;
                g.drawImage(imgDragon, x, monsterGraphicLocation, null);
                //Loads image
                try {
                    imgDragon = ImageIO.read(new File(imgDragonFileName));
                } catch (IOException e) {}
            }else if (mName == "Troll"){
                x = CANVAS_WIDTH - CANVAS_WIDTH/4 - 40;
                g.drawImage(imgTroll, x, monsterGraphicLocation, null);
                //Loads image
                try {
                    imgTroll = ImageIO.read(new File(imgTrollFileName));
                } catch (IOException e) {}
            }else if (mName == "Boss"){
                x = CANVAS_WIDTH - CANVAS_WIDTH/4 - 40;
                g.drawImage(imgBoss, x, monsterGraphicLocation, null);
                //Loads image
                try {
                    imgBoss = ImageIO.read(new File(imgBossFileName));
                } catch (IOException e) {}
            }

        }
    }

    private class MonsterAnimation implements Runnable {
        public void run(){
            boolean up = true;

            for (int i = 0; i < 60; i++) {
                if (up) {
                    monsterGraphicLocation += 2;
                } else {
                    monsterGraphicLocation -= 2;
                }

                if (i == 10 || i == 50) {
                    up = false;
                } else if (i == 30) {
                    up = true;
                }
                repaint();
                try {
                    Thread.sleep(5);
                } catch (Exception e) {}
            }

            monsterGraphicLocation = 400;
            repaint();
        }
    }

    private class PlayerAnimation implements Runnable {
        public void run(){
            boolean up = true;

            for (int i = 0; i < 60; i++) {
                if (up) {
                    playerGraphicLocation += 2;
                } else {
                    playerGraphicLocation -= 2;
                }

                if (i == 10 || i == 50) {
                    up = false;
                } else if (i == 30) {
                    up = true;
                }
                repaint();
                try {
                    Thread.sleep(5);
                } catch (Exception e) {}
            }

            playerGraphicLocation = 400;
            repaint();
        }
    }
}
