import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * LOL Im so tired of doing this documentation so here's the gist of it:
 * inventory is an array of items
 * inventoryText is that array as a string array so it can be displayed
 * There's a button to go back to the game.
 */
public class InventoryWindow extends JFrame
{
    //Sets size of window
    public static final int CANVAS_WIDTH  = 900;
    public static final int CANVAS_HEIGHT = 500;

    private ArrayList<Item> inventory;
    private InventoryDisplay canvas;
    private ArrayList<String> inventoryText;

    public JButton returnButton;
    int select = 0;
    private String actionMessage = "";
    private int health;
    private int stamina;
    private int attack;
    private int weaponDur;
    private Player savedPlayer;
    private Monster savedMonster;
    private Stage savedStage;
    private int savedRoomPosition;
    private String wName;
    private boolean savedFlee;
    int baseCode[] = new int [] {1,1,2,2,3,4,3,4,5,6};
    int inputCode [] = new int [] {0,0,0,0,0,0,0,0,0,0};
    int currInput = 0;
    public Item selectedItem;
    public Player play;
    private int page = 0;

    public void storeCombat (Player playCombat, Monster monsterCombat, Stage stCombat, int numCombat, boolean fleeCombat) {
        savedPlayer = playCombat;
        savedStage = stCombat;
        savedMonster = monsterCombat;
        savedRoomPosition = numCombat;
        savedFlee = fleeCombat;
    }

    public void displayWindow(ArrayList<Item> inv, Player p, Stage s, int n) {
        inventory = inv;
        canvas = new InventoryDisplay(); //Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        //Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE); //Handle the CLOSE button
        pack(); //Either pack() the components; or setSize()
        setTitle("Inventory"); //JFrame sets the title of outer frame
        setVisible(true); //Displays window
        setLocationRelativeTo(null); //Puts the JFrame in the middle of the screen @Francis
        setFocusable(true);
        play = p;
        health = play.getHealth();
        stamina = play.getStamina();
        attack = play.getAttack();
        weaponDur = play.getDur();
        wName = play.weaponName();
        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_W:
                        if (select > 0) {
                            select = select - 1;
                        } 
                        repaint();
                        break;

                        case KeyEvent.VK_S:
                        if (select < inventoryText.size() - 1) {
                            select = select + 1;
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_ESCAPE:
                        if (p.getCombat() == true) {
                            dispose();
                            CombatWindow cW = new CombatWindow();
                            cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition, savedFlee);
                        } else {
                            dispose();
                            GameplayWindow gW = new GameplayWindow();
                            gW.displayWindow(p, s, n);
                        }
                        break;

                        case KeyEvent.VK_I:
                        if (p.getCombat() == true) {
                            dispose();
                            CombatWindow cW = new CombatWindow();
                            cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition, savedFlee);
                        } else {
                            dispose();
                            GameplayWindow gW = new GameplayWindow();
                            gW.displayWindow(p, s, n);
                        }
                        break;

                        case KeyEvent.VK_ENTER:
                        if (inventory.size() > 0) {
                            if (inventory.get(select).getType().equals("Food")) {
                                if (play.getStamina() >= 100) {
                                    actionMessage = "You're already full!";
                                } else {
                                    play.setStamina(play.getStamina() + ((Food)inventory.get(select)).eat());
                                    actionMessage = "You regained some stamina.";
                                    inventory.remove(select);

                                    if (play.getStamina() > 100) {
                                        play.setStamina(100);
                                    } 
                                    stamina = play.getStamina();

                                    if (p.getCombat() == true) {
                                        p.setHealth(play.getHealth() - savedMonster.getAttack());
                                        if (play.getHealth() <= 0) {
                                            GameOverWindow gOW = new GameOverWindow();
                                            gOW.displayWindow(play, "Killed by " + savedMonster.getName());
                                            dispose();
                                        } else {
                                            dispose();
                                            CombatWindow cW = new CombatWindow();
                                            cW.setMessage(actionMessage);
                                            cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition, savedFlee);
                                        }
                                    }
                                }
                            } else
                            if (inventory.get(select).getType().equals("Weapon")) {
                                play.setWeapon((Weapon)inventory.get(select));
                                actionMessage = "You equipped the " +inventory.get(select).getName() +".";
                                attack = play.getAttack();
                                weaponDur = play.getDur();
                                wName = play.weaponName();
                                if (p.getCombat() == true) {
                                    p.setHealth(play.getHealth() - savedMonster.getAttack());
                                    if (play.getHealth() <= 0) {
                                        GameOverWindow gOW = new GameOverWindow();
                                        gOW.displayWindow(play, "Killed by " + savedMonster.getName());
                                        dispose();
                                    } else {
                                        dispose();
                                        CombatWindow cW = new CombatWindow();
                                        cW.setMessage(actionMessage);
                                        cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition, savedFlee);
                                    }
                                }
                            }
                            else 
                            if (inventory.get(select).getType().equals("HealingItem")) {
                                if (play.getHealth() >= play.getMaxHealth()) {
                                    actionMessage = "You're at full health!";
                                } else {
                                    play.setHealth(play.getHealth() + ((HealingItem)inventory.get(select)).use());
                                    actionMessage = "You regained some health.";
                                    inventory.remove(select);
                                    if (play.getHealth() > play.getMaxHealth()) {
                                        play.setHealth(play.getMaxHealth());
                                    } 
                                    health = play.getHealth();
                                    if (p.getCombat() == true) {
                                        p.setHealth(play.getHealth() - savedMonster.getAttack());
                                        if (play.getHealth() <= 0) {
                                            GameOverWindow gOW = new GameOverWindow();
                                            gOW.displayWindow(play, "Killed by " + savedMonster.getName());
                                            dispose();
                                        } else {
                                            dispose();
                                            CombatWindow cW = new CombatWindow();
                                            cW.setMessage(actionMessage);
                                            cW.displayWindow(savedPlayer, savedMonster, savedStage, savedRoomPosition, savedFlee);
                                        }
                                    }
                                }
                            } else if (inventory.get(select).getType().equals("Idol")) {
                                GameOverWindow gOW = new GameOverWindow();
                                gOW.displayWindow(play, "win");
                                dispose();
                            } else if (inventory.get(select).getType().equals("Lock") && selectedItem != null) {
                                if (inventory.get(select).getName().equals("Big Locked Chest")) {
                                    if (selectedItem.getName().equals("Big Key")) {
                                        inventory.remove(select);
                                        inventory.remove(inventory.indexOf(selectedItem));
                                        inventory.add(randomItem());
                                    }
                                } else if (inventory.get(select).getName().equals("Small Locked Chest")) {
                                    if (selectedItem.getName().equals("Small Key")) {
                                        inventory.remove(select);
                                        inventory.remove(inventory.indexOf(selectedItem));
                                        inventory.add(randomItem());
                                    }
                                }
                            } else {
                                selectedItem = inventory.get(select);
                            }
                        }
                        if (select >= inventory.size()) {
                            select = inventory.size() - 1;
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_UP:
                        if (currInput < 10) {
                            inputCode [currInput] = 1;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_DOWN:
                        if (currInput < 10) {
                            inputCode [currInput] = 2;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_LEFT:
                        if (currInput < 10) {
                            inputCode [currInput] = 3;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_RIGHT:
                        if (currInput < 10) {
                            inputCode [currInput] = 4;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_B:
                        if (currInput < 10) {
                            inputCode [currInput] = 5;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_A:
                        if (currInput < 10) {
                            inputCode [currInput] = 6;
                            currInput++;
                        } 
                        break;

                        case KeyEvent.VK_SHIFT:
                        for (int i = 0; i < 10; i++) {
                            if (inputCode [i] != baseCode [i]) {
                                break;
                            } else if (i == 9) {
                                play.enableCheats();
                                actionMessage = "Cheats enabled.";
                            }
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_NUMPAD1:
                        if (play.cheatCheck() == true) {
                            play.addItem(new Excalibur());
                            actionMessage = "Spawned item id: Excalibur.";
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_NUMPAD2:
                        if (play.cheatCheck() == true) {
                            play.addItem(new Ambrosia());
                            actionMessage = "Spawned item id: Ambrosia.";
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_NUMPAD3:
                        if (play.cheatCheck() == true) {
                            play.addItem(new HeartCanister());
                            actionMessage = "Spawned item id: HeartCanister.";
                        }
                        repaint();
                        break; 

                        case KeyEvent.VK_NUMPAD4:
                        if (play.cheatCheck() == true) {
                            play.addItem(new SmallKey());
                            actionMessage = "Spawned item id: SmallKey.";
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_NUMPAD5:
                        if (play.cheatCheck() == true) {
                            play.addItem(new BigKey());
                            actionMessage = "Spawned item id: BigKey.";
                        }
                        repaint();
                        break;

                        case KeyEvent.VK_BACK_SPACE:
                        actionMessage = "Threw out " +inventory.get(select).getName() +".";
                        if (inventory.get(select).getName().equals("Idol")) {
                            GameOverWindow gOW = new GameOverWindow();
                            gOW.displayWindow(play, "Killed by your own stupidity. Wow, you are dumb.");
                            dispose();
                        }
                        inventory.remove(select);
                        repaint();
                        break;

                        default: 
                        for (int i = 0; i < 10; i++) {
                            inputCode [i] = 0;
                        }
                        repaint();
                        break;
                    }
                } 
            });
    }

    public Item randomItem() {
        Room room = play.getRoom();
        int roomNumber = room.getroomNumber(); 
        int value = (int) (Math.random() * 100 + 1);

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
        } else {
            return new Bread();
        }
    }

    /**
     * takes array of items and returns info about the
     * items as a String so it can be displayed.
     * Each element is a line of text.
     */
    private void setInvTxt() {
        inventoryText = new ArrayList<String>();

        //Gets info about items to show user including the type and name
        if (inventory.size() == 0){
            inventoryText.add("There's nothing here...");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                String type = inventory.get(i).getType();
                String name = inventory.get(i).getName();
                inventoryText.add(type + ": " + name);
            }
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
    private class InventoryDisplay extends JPanel {
        //Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            //Paint base background
            page = (int) select/15;
            super.paintComponent(g);
            setBackground(Color.BLACK); 
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 18));
            setInvTxt();
            String txt;
            int x;

            //Basically splits up each element of String arraylist into a
            //different line on the window
            for (int i = 0 + (15 * page); i < inventoryText.size()&& i < (15 * (page + 1)); i++) {
                if (i == select) {
                    txt = "> " + inventoryText.get(i);
                } else {
                    txt = inventoryText.get(i);
                }
                x = centerStringX(txt, CANVAS_WIDTH, g);
                g.drawString(txt, x, (30 + 25*(i - (15*page))));
            }
            txt = "Inventory Page:" + (page + 1);
            x = centerStringX(txt, CANVAS_WIDTH, g);
            g.drawString(txt, x, (30 + 25*15));
            x = centerStringX(actionMessage, CANVAS_WIDTH, g);
            g.drawString(actionMessage, x, CANVAS_HEIGHT - 60);

            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.setColor(Color.YELLOW); //Displays important stats at bottom of screen
            String line1Vars = "HP:"+health +"  Stamina:"+stamina +"    Attack:"+attack +"   Weapon:"+wName+"   Weapon Integrity:" +weaponDur;
            x = centerStringX(line1Vars, CANVAS_WIDTH, g);
            g.drawString(line1Vars, x, CANVAS_HEIGHT - 20);
        }
    }
}
