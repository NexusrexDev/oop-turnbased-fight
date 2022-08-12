import java.util.concurrent.TimeUnit;

/*
 * This class is used to manage the UI components (Character bars, clearing console..etc)
 */

public class UI {
    static AudioPlayer BGPlayer = new AudioPlayer();
    static AudioPlayer SFXPlayer = new AudioPlayer();

    //This method flushes the UI/Console
    public static void clearConsole() {
        final String os = System.getProperty("os.name");
        try {
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (Exception e) {}
    }

    //This method prints the character's (Player or enemy) details
    public static void printCharacter(Character chara) {
        String name = chara.getName();
        String hp = chara.getHP() + "/" + chara.getMaxHP() + " HP ";
        String lvl = "Lvl " + chara.getLevel() + " ";
        String status = "[" + chara.getStatus() + "] ";
        System.out.println(name + " - " + hp + "- " + lvl + "- " + status);
    }

    //This method prints the player's inventory
    public static void printInventory(Player player) {
        String hPotion = player.getInventorySlot(0) + "[H] ";
        String shield = player.getInventorySlot(1) + "[S] ";
        String pPotion = player.getInventorySlot(2) + "[P] ";
        System.out.println(hPotion + "- " + shield + "- " + pPotion);
    }

    public static void printCommandList(Pages currentPage) {
        String commandList[] = {
            //Main menu
            "------------------\n" + "[B] - Go to battle\n[S] - Go to shop\n" + "------------------\n",
            //Battle
            "-----------------------\n" + "[A] - Attack\n[H] - Use Health Potion\n" + 
            "[S] - Use Shield\n[P] - Use Power Potion\n"+"-----------------------\n",
            //Shop
            ""
        };
        System.out.println(commandList[currentPage.getValue()]);
        System.out.print("Your command: ");
    }

    public static void printBattleUI(Player player, Character enemy, int turn) {
        UI.clearConsole();
        UI.printCharacter(player);
        UI.printCharacter(enemy);
        switch(turn) {
            case 0:
                System.out.println("\nIt's " + player.getName() + "'s turn!\n");
                printInventory(player);
                UI.printCommandList(Pages.Fight);
            break;
            case 1:
                System.out.println("\nIt's the enemy's turn!\n");
            break;
        }
    }

    public static void printAttack(Character chara, int damage) {
        String name = chara.getName();
        String weaponName = chara.getWeapon().getName();
        System.out.println(name + " used " + weaponName + "!");
        SFXPlayer.play(Sounds.SFXHit.getValue(), 0);
        UI.waitTime(TimeUnit.SECONDS, 1);
        System.out.println(name + " dealt " + damage + " HP!");
        UI.waitTime(TimeUnit.SECONDS, 1);
    }

    public static void printItemUsage(Player player, boolean status, int type) {
        String name = player.getName();
        switch(type) {
            //Health
            case 0:
                if (status) {
                    System.out.println(name + " used a Health Potion!");
                    SFXPlayer.play(Sounds.SFXPowerup.getValue(), 0);
                    UI.waitTime(TimeUnit.SECONDS, 1);
                    System.out.println(name + "'s health increased by 25!");
                } else {
                    System.out.println("There were no Health Potions to be found..");
                }
            break;

            //Shield
            case 1:
                if (status) {
                    System.out.println(name + " used a Shield!");
                    SFXPlayer.play(Sounds.SFXPowerup.getValue(), 0);
                    UI.waitTime(TimeUnit.SECONDS, 1);
                    System.out.println(name + " is protected until the next attack!");
                } else {
                    System.out.println("There were no shields to be found..");
                }
            break;

            //Buff
            case 2:
                if (status) {
                    System.out.println(name + " used a Power Potion!");
                    SFXPlayer.play(Sounds.SFXPowerup.getValue(), 0);
                    UI.waitTime(TimeUnit.SECONDS, 1);
                    System.out.println(name + "'s damage is increased for the next attack!");
                } else {
                    System.out.println("There were no Power Potions to be found..");
                }
            break;
        }
        UI.waitTime(TimeUnit.SECONDS, 1);
    }

    public static void waitTime(TimeUnit unit, int time) {
        try {
            unit.sleep(time);
        } catch (Exception e) {}
    }

    public static void printbattleEnd(Player player) {
        //Loss variant
        String playerName = player.getName();
        BGPlayer.play(Sounds.BGLoss.getValue(), 0);
        UI.clearConsole();
        System.out.println("You lost..");
        UI.waitTime(TimeUnit.SECONDS, 1);
        System.out.println("May your soul, " + playerName + ", rest in peace..");
        UI.waitTime(TimeUnit.SECONDS, 2);
        UI.clearConsole();
    }

    public static void printbattleEnd(Player player, int exp, boolean leveledUp) {
        //Win variant
        String playerName = player.getName();
        BGPlayer.play(Sounds.BGWin.getValue(), 0);
        UI.clearConsole();
        System.out.println(playerName + ", you win!");
        UI.waitTime(TimeUnit.SECONDS, 1);
        System.out.println("You gain " + exp + " exp!");
        UI.waitTime(TimeUnit.SECONDS, 1);
        if (leveledUp) {
            System.out.println("You leveled up!");
            UI.waitTime(TimeUnit.SECONDS, 1);
        }
    }
}
