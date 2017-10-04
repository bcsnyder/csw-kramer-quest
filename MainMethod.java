import javax.swing.JOptionPane;

public class MainMethod
{
    public static void main(String [] args) {
        Stage map = new Stage(1);
        map.makeRooms();
        Room startRoom = map.getRoom(1);

        Player p = new Player();
        p.setName(JOptionPane.showInputDialog("Enter your name."));
        p.setHealth(25);
        p.setAttack(3);
        p.setRoom(startRoom);

        int xPos = 1;
        int yPos = 1;
        
        while(startRoom.getTile(xPos, yPos) != '.') {
            while(startRoom.getTile(xPos, yPos) != '.') {
                yPos += 1;
            }
            xPos += 1;
        }
        
        p.setPos(xPos, yPos);
        startRoom.addPlayer(xPos, yPos);
        
        System.out.println(startRoom.toString());
        
        SoundSystem player = new SoundSystem();
        String audioFilePath = "Sound/Menu_Theme.wav";
        player.setPath(audioFilePath);
        
        p.setWeapon(new Axe());
        
        UseWindow w = new UseWindow();
        w.setRoom(startRoom);
        w.setPlayer(p);
        w.showMenu();
        player.play();
    }
}
