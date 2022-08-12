public class Weapon {
    //Variables
    private int damage;
    private String name;

    Weapon() {
        damage = 0;
        name = "";
    }

    Weapon(int newDamage, String newName) {
        damage = newDamage;
        name = newName;
    }

    int getDamage() {
        return damage;
    }

    void setDamage(int newDamage) {
        damage = newDamage;
    }

    String getName() {
        return name;
    }

    void setName(String newName) {
        name = newName;
    }
}
