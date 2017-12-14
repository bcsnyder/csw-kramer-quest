import javax.swing.JOptionPane;

public class MainMethod
{
    public static void main(String [] args) {
        MenuWindow menu = new MenuWindow();
        menu.displayWindow(new SoundSystem());
    }
}
