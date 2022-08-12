/*
 * The player's class!
 */

public class Player extends Character {
    //Variables
    private int exp, maxExp;
    private int[] inventory = new int[3];
    private String[] weaponList = {"Punch", "Dagger", "Wooden Hammer", "Broadsword",
                                   "Battle Axe", "War Hammer", "Longsword", "Great Axe",
                                   "Sledgehammer", "Longsword", "Viking Axe", "Greatsword"};

    Player() {
        super();
        maxExp = 100;
        exp = 0;
        inventory[0] = 1;
        inventory[1] = 1;
        inventory[2] = 1;
    }

    int getExp() {
        return exp;
    }

    void setExp(int newExp) {
        exp = newExp;
    }

    int getMaxExp() {
        return maxExp;
    }

    int getInventorySlot(int invSlot) {
        return inventory[invSlot];
    }

    void setMaxExp(int newMaxExp) {
        maxExp = newMaxExp;
    }

    boolean gainExp(int progExp) {
        boolean leveledUp = false;
        int currentExp = exp + progExp;
        int currentLevel = getLevel();
        if (currentExp >= maxExp) {
            //Checking if the player actually levels up
            leveledUp = true;
            levelUp(currentExp, currentLevel);
        } else {
            exp = currentExp;
        }
        //Used for the UI to know if the player leveled up or not
        return leveledUp;
    }

    void levelUp(int currentExp, int currentLevel) {
        currentLevel++;
        //Increasing the max exp
        exp = currentExp - maxExp;
        maxExp = 100 + (30 * currentLevel);
        //Levelling the weapon up
        Weapon currentWeapon = getWeapon();
        currentWeapon.setDamage(10 + ((currentLevel - 1) * 5));
        if (currentLevel < 13) {
            currentWeapon.setName(weaponList[currentLevel-1]);
        }
        //Setting the level
        setLevel(currentLevel);
        //Levelling the health up
        int newHP = 50 + ((currentLevel-1) * 10);
        setHP(newHP);
        setMaxHP(newHP);
    }

    void Heal(int health) {
        int currentHP = getHP() + health;
        int maxHP = getMaxHP();
        if (currentHP > maxHP) {
            setHP(maxHP);
        }
        else {
            setHP(currentHP);
        }
    }

    boolean useInventory(int inventorySlot) {
        boolean itemExists = false;
        if (inventory[inventorySlot] > 0) {
            itemExists = true;
            //Use item based on the slot
            switch(inventorySlot) {
                //Health Potion
                case 0:
                    Heal(25);
                break;
                //Shield
                case 1:
                    setStatus(Status.Defense);
                break;
                //Power Potion
                case 2:
                    setStatus(Status.Buffed);
                break;
            }
            inventory[inventorySlot]--;
        }
        return itemExists;
    }

    void gainInventory(int inventorySlot) {
        inventory[inventorySlot]++;
    }
}
