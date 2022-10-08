import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.Clip;

//test
/*
 * The active gameplay works go here
 */

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //Starting the game
        entrance();
    }

    //This method starts the game
    public static void entrance() {
        UI.BGPlayer.play(Sounds.BGEntry.getValue(), Clip.LOOP_CONTINUOUSLY);
        UI.clearConsole();
        System.out.println("Greetings, brave warrior!");
        UI.waitTime(TimeUnit.SECONDS, 1);
        System.out.println("Enter your name!");
        String name = scanner.nextLine();
        UI.clearConsole();
        System.out.println("Are you ready, " + name + "?");
        UI.waitTime(TimeUnit.SECONDS, 1);
        for (int i = 3; i > 0; i--) {
            System.out.println(i);
            UI.waitTime(TimeUnit.SECONDS, 1);
        }
        Player player = new Player();
        player.setName(name);
        player.setWeapon(new Weapon(10, "Punch"));
        battle(player);
    }

    //This method is the battle loop
    public static void battle(Player player) {
        int turn = 0; //0 for the player, 1 for the enemy
        boolean battleEnd = false;
        Character enemy = enemyCreator(player);
        UI.BGPlayer.play(Sounds.BGFight.getValue(), Clip.LOOP_CONTINUOUSLY);
        do {
            //Print the UI parts
            UI.printBattleUI(player, enemy, turn);
            //Logic management (player or enemy)
            switch(turn) {
                //Player's turn
                case 0:
                    while(true) {
                        String command = scanner.nextLine();
                        //Attack
                        if (command.equalsIgnoreCase("a")) {
                            int playerAtk = player.Attack();
                            int enemyDmg = enemy.Damage(playerAtk);
                            UI.printAttack(player, enemyDmg);
                            break;
                        }
                        //Health
                        else if (command.equalsIgnoreCase("h")) {
                            boolean healSuccess = player.useInventory(0);
                            UI.printItemUsage(player, healSuccess, 0);
                            if (healSuccess) {
                                break;
                            } else {
                                UI.printBattleUI(player, enemy, turn);
                            }
                        }
                        //Shield
                        else if (command.equalsIgnoreCase("s")) {
                            boolean shieldSuccess = player.useInventory(1);
                            UI.printItemUsage(player, shieldSuccess, 1);
                            if (shieldSuccess) {
                                break;
                            } else {
                                UI.printBattleUI(player, enemy, turn);
                            }
                        }
                        //Buff
                        else if (command.equalsIgnoreCase("p")) {
                            boolean powerSuccess = player.useInventory(2);
                            UI.printItemUsage(player, powerSuccess, 2);
                            if (powerSuccess) {
                                break;
                            } else {
                                UI.printBattleUI(player, enemy, turn);
                            }
                        }
                    }
                break;
                //Enemy's turn
                case 1:
                    int random = (int)(Math.random() * 4);
                    UI.waitTime(TimeUnit.SECONDS, 1);
                    //Attack
                    if (random < 3) {
                        int enemyAtk = enemy.Attack();
                        int playerDmg = player.Damage(enemyAtk);
                        UI.printAttack(enemy, playerDmg);
                    }
                    //Do nothing
                    else {
                        System.out.println(enemy.getName() + " did nothing..");
                        UI.waitTime(TimeUnit.SECONDS, 1);
                    }
                break;
            }
            //Check if the player or the enemy are dead
            battleEnd = (player.getDeath() || enemy.getDeath());
            if (battleEnd) break;
            //Changing turns
            turn = (turn == 0) ? 1 : 0;
        }
        while (!battleEnd);

        //Win or game over
        if (turn == 0) {
            int playerLevel = player.getLevel();
            int winExp = 50 - (int)(2.5 * (playerLevel-1));
            Boolean leveledUp = player.gainExp(winExp);
            UI.printbattleEnd(player, winExp, leveledUp);
            rewardProvider(player);
            System.out.println("Would you start another fight? (Y/N)");
            String command = scanner.nextLine();
            while(true) {
                if (command.equalsIgnoreCase("Y")) {
                    battle(player);
                    break;
                } else if (command.equalsIgnoreCase("N")) {
                    endGame();
                    break;
                }
            }
        } else {
            UI.printbattleEnd(player);
            System.out.println("Game Over\nWould you create another warrior? (Y/N)");
            String command = scanner.nextLine();
            while(true) {
                if (command.equalsIgnoreCase("Y")) {
                    entrance();
                    break;
                } else if (command.equalsIgnoreCase("N")) {
                    endGame();
                    break;
                }
            }
        }
    }

    //This method creates an enemy based on the player's current values
    public static Character enemyCreator(Player player) {
        int level = player.getLevel() + (int)(Math.random() * 2);
        int weaponDamage = 5 + ((level - 1) * 5);
        int enemySelector = (int)(Math.random() * 6);
        String enemyNames[] = {"Slime", "Wolf", "Orc", "Skeleton", "Knight", "Dragon"};
        String weaponNames[] = {"Pounce", "Claw", "Battle Axe", "Bone Club", "Broad Sword", "Fire Breath"};
        Weapon nWeapon = new Weapon(weaponDamage,weaponNames[enemySelector]);
        Character enemy = new Character(level, enemyNames[enemySelector], nWeapon);
        return enemy;
    }

    //This method randomly chooses a reward and gives it to the player
    public static void rewardProvider(Player player) {
        int random = (int)(Math.random() * 100);
        if (random >= 25) {
            int randomSlot = (int)(Math.random()*3);
            player.gainInventory(randomSlot);
            switch(randomSlot) {
                case 0:
                    System.out.println("You get a Health Potion!");
                break;
                case 1:
                    System.out.println("You get a Shield!");
                break;
                case 2:
                    System.out.println("You get a Power Potion!");
                break;
            }
            UI.waitTime(TimeUnit.SECONDS, 1);
        }
    }

    public static void endGame() {
        UI.BGPlayer.stop();
        UI.SFXPlayer.stop();
    }
}