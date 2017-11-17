
public class Sword extends Weapon
{
    private char symbol;
    private String type;
    private String name;
    private int damage;
    private int durability;
    String category;

    public Sword() {
        type = "Weapon";
        name = "Sword";
        category = "Item";
        damage = 15;
        durability = 4;
        symbol = '$';
    }
    
    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int attack() {
        durability -= 1;
        return damage;
    }

    public int getDamage() {
        return damage;
    }

    public int getDurability() {
        return durability;
    }

    public char getSymbol() {
        return symbol;
    }
}
