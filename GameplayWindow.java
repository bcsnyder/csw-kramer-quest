import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 * Displays a window with a demo components of the game inside
 * including example stats, the user's name, and text graphics
 * of an example room.
 * 
 * Interesting Variable Only Dictionary:
 * room             String holding the ASCII text for the current room
 * levelNum         What stage the player is on
 * actionMessage    message displayed at bottom of screen to notify user
 */
public class GameplayWindow extends JFrame
{
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public final int CANVAS_WIDTH  = gd.getDisplayMode().getWidth();//Sets size of window
    public final int CANVAS_HEIGHT = (int)(gd.getDisplayMode().getHeight()*0.9);

    private GameDisplay canvas;
    public String room;

    private int levelNum; 
    private int roomHeight;
    private Player play;
    private Stage space;
    private Room board;
    private ArrayList<Item> inventory;
    private int turnPhase = 0;
    private String menuString;
    private int menuSelection;
    private boolean inCombat;
    Tileable currentTile;
    boolean attacked = false;

    private String actionMessage = "";

    /**
     * Sets variables based on what's passed in and sets up
     * the window's components and layout. Pretty standard.
     */
    public void displayWindow(Player player, Stage map, int roomNum) {
        play = player;
        space = map;
        levelNum = roomNum;
        board = space.getRoom(levelNum);     
        room = board.toString();
        roomHeight = board.getHeight();
        inventory = play.getInventory();

        inCombat = false;
        currentTile = new Bread();

        String menuString = "";
        if (player.getName().equals("Unnamed")) {
            turnPhase = 2;
            menuSelection = 0;
        }

        canvas = new GameDisplay();    // Construct the drawing canvas
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);

        setTitle("Game Name");
        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setVisible(true);    //Displays window
        setLocationRelativeTo(null);     //Puts the JFrame in the middle of the screen @Francis

        addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    switch(evt.getKeyCode()) {
                        case KeyEvent.VK_W:
                        if (turnPhase == 0) {
                            currentTile = move("up");
                        }
                        break;
                        case KeyEvent.VK_S:
                        if (turnPhase == 0) {
                            currentTile = move("down");
                        }
                        break;
                        case KeyEvent.VK_Q:
                        break;
                        case KeyEvent.VK_A:
                        if (turnPhase == 0) {
                            currentTile = move("left");
                        } else if (turnPhase == 2) {
                            if (menuSelection > 0) {
                                menuSelection--;
                                refreshWindow("", play, space, levelNum);
                            } else {
                                menuSelection++;
                                refreshWindow("", play, space, levelNum);
                            }
                        }
                        break;
                        case KeyEvent.VK_D:
                        if (turnPhase == 0) {
                            currentTile = move("right");
                        } else if (turnPhase == 2) {
                            if (menuSelection < 1) {
                                menuSelection++;
                                refreshWindow("", play, space, levelNum);
                            } else {
                                menuSelection--;
                                refreshWindow("", play, space, levelNum);
                            }
                        }
                        break;
                        case KeyEvent.VK_I:
                        if (turnPhase == 0) {
                            InventoryWindow iW = new InventoryWindow();
                            iW.displayWindow(play.getInventory(), play, space, levelNum);
                            dispose();
                        }
                        break;
                        case KeyEvent.VK_ENTER:
                        if (inCombat == true) {
                            Monster enemy = (Monster)currentTile;//Selects the monster based on what tile the user hit
                            CombatWindow cW = new CombatWindow();
                            cW.displayWindow(play, enemy, space, levelNum, attacked);
                            dispose();
                        }

                        if (turnPhase == 1 && !inCombat) {
                            ArrayList<Monster> countedMonsters = new ArrayList<Monster>();
                            //Monster move
                            for(int xCounter = 1; xCounter < board.getWidth() - 1; xCounter++) {
                                for(int yCounter = 1; yCounter < board.getHeight() - 1; yCounter++) {
                                    Tileable c = board.returnTileObject(xCounter, yCounter);

                                    if (c.getCategory().equals("Monster")) {
                                        Monster m = (Monster)board.returnTileObject(xCounter, yCounter);

                                        if (!m.getMoved()) {
                                            countedMonsters.add(m);
                                            Room newBoard = m.chooseMove(board);
                                            if (newBoard == null) {
                                                refreshWindow("The enemy attacks!", play, space, levelNum);
                                                currentTile = m;
                                                inCombat = true;
                                                attacked = true;
                                            } else {
                                                space.setRoom(newBoard, levelNum);
                                                play.setRoom(newBoard);
                                                refreshWindow("The monsters moved.", play, space, levelNum);
                                            }
                                        }
                                    }
                                }
                            }
                 
                            
                            for (int monsterCount = 0; monsterCount < countedMonsters.size(); monsterCount++) {
                                 Monster m = countedMonsters.get(monsterCount);
                                 int xLocation = m.getX();
                                 int yLocation = m.getY();
                                 board.addTile(xLocation,yLocation,m);
                                 space.setRoom(board, levelNum);
                                 play.setRoom(board);
                                 m.setMoved(false);
                             }
                            if (!inCombat) {
                                turnPhase = 0;
                            }
                            

                            if (countedMonsters.size() == 0) {
                                refreshWindow("", play, space, levelNum);
                            }
                        } else if (turnPhase == 2) {
                            if (menuSelection == 0) {
                                turnPhase = 0;
                                play.setName("Wefwef");
                                refreshWindow("", play, space, levelNum);
                            } else if (menuSelection == 1) {
                                turnPhase = 0;
                                play.setName(JOptionPane.showInputDialog("Enter your name!"));
                                refreshWindow("", play, space, levelNum);
                            }
                        }
                        break;
                    }
                }
            });
    }

    private Tileable move(String direction) {
        Tileable action = new Empty();

        if (direction.equals("up")) {
            action = play.moveUp();
        } else if (direction.equals("down")) {
            action = play.moveDown();
        } else if (direction.equals("left")) {
            action = play.moveLeft();
        } else if (direction.equals("right")) {
            action = play.moveRight();
        }

        if (play.getHealth() == 0){
            GameOverWindow gOW  = new GameOverWindow();        
            dispose();
            gOW.displayWindow(play, "Died of starvation");
        }

        if (action.getCategory().equals("Empty")) {
            board = play.getRoom();
            space.setRoom(board, levelNum);
            if (play.getStamina() < 10){
                refreshWindow ("You moved "+direction+"! You can hear your stomache growl.", play, space, levelNum);   
            }else {
                refreshWindow("You moved "+direction+".", play, space, levelNum);
            }

            turnPhase = 1;
        } else if(action.getCategory().equals("Door Forward")) {
            board.removePlayer();
            space.setRoom(board, levelNum);
            levelNum++;
            board = space.getRoom(levelNum);
            int x = board.returnPositionX();
            int y = board.returnPositionY();
            board.addTile(x,y, play);
            space.setRoom(board, levelNum);
            play.setRoom(board);
            play.setPos(x,y);
            if (play.getStamina() < 10){
                refreshWindow ("You entered a new room! Your stomache feels rather empty.", play, space, levelNum);   
            }else {
                refreshWindow("You entered a new room!", play, space, levelNum);
            }
        } else if(action.getCategory().equals("Item")) {
            board = play.getRoom();
            space.setRoom(board, levelNum);                
            play.addItem((Item)action);
            refreshWindow("Obtained the " +inventory.get(inventory.size() - 1).getName() +".", play, space, levelNum);

            turnPhase = 1;
        } else if(action.getCategory().equals("Wall")) {
            refreshWindow("You bumped into a wall!", play, space, levelNum);
        } else if(action.getCategory().equals("Door Back")) {
            board.removePlayer();
            space.setRoom(board, levelNum);    
            levelNum--;
            board = space.getRoom(levelNum);
            int x = board.returnPositionX2();
            int y = board.returnPositionY2();
            board.addTile(x,y, play);
            space.setRoom(board, levelNum);
            play.setRoom(board);
            play.setPos(x,y);
            if (play.getStamina() < 10){
                refreshWindow ("You moved back a room! You should probably find something to eat.", play, space, levelNum);   
            }else {
                refreshWindow("You moved back a room!", play, space, levelNum);
            }
        } else {
            inCombat = true;
            turnPhase = 1;
            refreshWindow("You engaged the enemy!", play, space, levelNum);
        }
        repaint();
        return action;
    }

    /**
     * Simply updates variables with what's passed in and
     * repaints the window. VOILA!
     */
    public void refreshWindow(String useMessage, Player player, Stage map, int roomNum) {
        play = player;
        space = map;
        levelNum = roomNum;
        board = space.getRoom(levelNum);     
        room = board.toString();
        roomHeight = board.getHeight();
        inventory = play.getInventory();
        actionMessage = useMessage;

        canvas.repaint();
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
     * Panel inside frame that holds game graphics
     * and stats for user
     */
    private class GameDisplay extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint base background
            setBackground(Color.BLACK);  // set background color for this JPanel

            g.setColor(Color.WHITE);//Displays username and score at top of screen
            g.setFont(new Font("Monospaced", Font.PLAIN, 24));
            String playerInfo = "Name: " + play.getName() + " - Room " + (levelNum + 1);
            int x = centerStringX(playerInfo, CANVAS_WIDTH, g);
            g.drawString(playerInfo, x, 30);

            g.setFont(new Font("Monospaced", Font.PLAIN, 24)); //Displays gameboard
            int yCor = 60; //Starting Y coordinate
            int inc = room.length()/roomHeight; //Size of each row chunk to be displayed
            x = centerStringX(room.substring(0,inc), CANVAS_WIDTH, g);
            for (int i = 1; i <= roomHeight; i++) {//Splits room into rows and displays them
                g.drawString(room.substring((i-1)*inc,i*inc), x, yCor);
                yCor += 25;
            }

            //Shows desired action message to notify user about
            //whatever just happened
            g.setFont(new Font("Monospaced", Font.PLAIN, 28));
            x = centerStringX(actionMessage, CANVAS_WIDTH, g);
            g.drawString(actionMessage, x, CANVAS_HEIGHT - 100);

            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.setColor(Color.YELLOW); //Displays important stats at bottom of screen
            String line1Vars = "HP:"+play.getHealth()+"  Stamina:"+play.getStamina()+"    Attack:"+play.getAttack()+"   Weapon:"+play.weaponName()+"   Weapon Integrity:"+play.getDur();
            x = centerStringX(line1Vars, CANVAS_WIDTH, g);
            g.drawString(line1Vars, x, CANVAS_HEIGHT - 20);

            //draws list of actions
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            if (turnPhase == 0) {
                menuString = "";
            } else if (turnPhase == 1) {
                menuString = ">Next Turn";
            } else if (turnPhase == 2) {
                if (menuSelection == 0) {
                    menuString = ">Use default name Wefwef       Create custom name";
                } else if (menuSelection == 1) {
                    menuString = " Use default name Wefwef      >Create custom name";
                }
            }
            x = centerStringX(menuString, CANVAS_WIDTH, g);
            g.drawString(menuString, x, 500);
        }
    }
}
