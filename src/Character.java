/*
 * This class is going to be the base for both the player and the enemies
 */

public class Character {
    //Variables
    private int HP, maxHP, lvl;
    private String name;
    private Status currentStatus;
    private Weapon weapon;
    private Boolean death;

    Character() {
        maxHP = 50;
        HP = maxHP;
        lvl = 1;
        name = "";
        currentStatus = Status.Normal;
        weapon = new Weapon(5, "Attack");
        death = false;
    }

    Character(int level, String charName, Weapon newWeapon) {
        lvl = level;
        maxHP = 50 + ((level-1) * 10);
        HP = maxHP;
        name = charName;
        currentStatus = Status.Normal;
        weapon = newWeapon;
        death = false;
    }

    int getHP() {
        return HP;
    }

    void setHP(int newHP) {
        HP = newHP;
    }

    int getMaxHP() {
        return maxHP;
    }

    void setMaxHP(int newMaxHP) {
        maxHP = newMaxHP;
    }

    int getLevel() {
        return lvl;
    }

    void setLevel(int newLvl) {
        lvl = newLvl;
    }

    String getName() {
        return name;
    }

    void setName(String newName) {
        name = newName;
    }

    Status getStatus() {
        return currentStatus;
    }

    void setStatus(Status newStatus) {
        currentStatus = newStatus;
    }

    Weapon getWeapon() {
        return weapon;
    }

    void setWeapon(Weapon newWeapon) {
        weapon = newWeapon;
    }

    Boolean getDeath() {
        return death;
    }

    void setDeath(Boolean newDeath) {
        death = newDeath;
    }

    int Attack() {
        int damage = weapon.getDamage();
        if (currentStatus.equals(Status.Buffed)) {
            damage += (damage * 0.5);
            currentStatus = Status.Normal;
        }
        int random = (int)(Math.random() * 6);
        if (random > 4) {
            damage += damage * 0.1;
        }
        return damage;
    }

    int Damage(int dmg) {
        int defenseValue = 0;
        //Checks if the character in defense mode, thus sets the defense value to an actual number
        if (currentStatus.equals(Status.Defense)) {
            defenseValue = (int)(Math.random() * 3) + 1;
            currentStatus = Status.Normal;
        }

        int damage = (int)(dmg - ( (dmg * 0.375) * defenseValue ));

        HP -= damage;

        if (HP <= 0) {
            death = true;
        }

        return damage;
    }
}
