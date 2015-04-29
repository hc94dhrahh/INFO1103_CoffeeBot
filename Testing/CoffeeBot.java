/**
 * info1103 - assignment 3
 * <Felix Hu>
 * <fehu4705>
 */


import java.util.Arrays;
import java.util.Scanner;

public class CoffeeBot {
    static Scanner kyb;
    static final String Y_VARIABLE = "Y";
    static final String N_VARIABLE = "N";
    static final int[] MONEY = new int[]{10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5};
    static final String[] MONEY_FORMAT = new String[]{"$100.00", "$50.00", "$20.00", "$10.00"
            , "$5.00", "$2.00", "$1.00", "$0.50", "$0.20", "$0.10", "$0.05"};
    static final int CUPS = 200;
    static final int SHOTS = 100;
    static String pay;

    public static void main(String[] args) {
        {
            if (args.length > 2) {
                System.out.println("Too many Arguments, Terminating...");
                return;
            }
            if (args.length < 2) {
                System.out.println("Not enough Arguments, Terminating...");
                return;
            }
        }
        int stock[] = new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1])};
        {
            if (stock[0] < 1 && stock[1] < 1) {
                System.out.println("Can't have 0 or negative cups and shots. Terminating...");
                return;
            }
            if (stock[0] < 1) {
                System.out.println("Can't have 0 or negative cups. Terminating...");
                return;
            }
            if (stock[1] < 1) {
                System.out.println("Can't have 0 or negative shots. Terminating...");
                return;
            }
        }

        System.out.print("Hello, what is your name good sir or good lady?\nName: ");
        kyb = new Scanner(System.in);

        String name = kyb.nextLine();

        System.out.print("Would you like to order some coffee, " + name
                + "?\n(Y/N): ");

        if (YesNoQuestion()) {
            System.out.println("Great! Let's get started.\n");

        } else {
            System.out.println("Come back next time " + name + ".");
            return;
        }

        System.out.println("Pricing");
        System.out.println("-------");

        System.out.println("$" + String.format("%.2f", (float) CUPS / 100)
                + " per cup and there is " + stock[0] + " in stock.\n"
                + "$" + String.format("%.2f", (float) SHOTS / 100)
                + " per shot in each cup and there is "
                + stock[1] + " in stock.\n");

        System.out.print("How many cups of coffee do you want?\nNumber of Cups: ");
        int numberCups = kyb.nextInt();

        if (numberCups > stock[0]) {
            System.out.println("Not enough cups, comeback later.");
            return;
        }

        if (numberCups < 0) {
            System.out.println("Can't have negative stock, Goodbye.");
            return;
        }

        if (numberCups == 0) {
            System.out.println("You want no Coffee, Goodbye.");
            return;
        }

        if (numberCups <= stock[0]) {
            System.out.println("\nYou want " + numberCups + " cups of coffee.");
        }
        int totShots = 0;
        int[] cupIDarray = new int[numberCups + 1];
        int[] cupNarray = new int[numberCups + 1];

        for (int cupNumber = 1; cupNumber <= numberCups; cupNumber++) {
            System.out.print("\nHow many shots do you want in cup " + (cupNumber) + "? The amount in stock is "
                    + stock[1] + ".\nNumber of Shots: ");

            int shotNumber = kyb.nextInt();

            while (shotNumber < 0 || shotNumber > stock[1] || shotNumber == 0) {
                if (stock[1] == 0) {
                    System.out.println("Oh we are out of stock for shots.");
                    return;
                }

                if (shotNumber < 0) {
                    System.out.println("You can't have negative amount of shots. Answer again");
                    System.out.println("How many shots do you want in cup " + (cupNumber) + "? The amount in stock is " +
                            stock[1] + ".");
                    shotNumber = kyb.nextInt();
                    continue;
                }

                if (shotNumber > stock[1]) {
                    System.out.println("There isn't enough stock, answer again.");
                    System.out.println("How many shots do you want in cup " + (cupNumber) + "? The amount in stock is " +
                            +stock[1] + ".");
                    shotNumber = kyb.nextInt();
                    continue;
                }

                if (shotNumber == 0) {
                    System.out.println("It isn't coffee without any coffee shots. Try again");
                    System.out.println("How many shots do you want in cup " + (cupNumber) + "? The amount in stock is " +
                            +stock[1] + ".");
                    shotNumber = kyb.nextInt();
                }
            }

            cupIDarray[cupNumber] = cupNumber;
            cupNarray[cupNumber] = shotNumber;
            totShots = totShots + shotNumber;
            stock[1] = stock[1] - shotNumber;
        }
        // Print out the cost, and proceed to payment question
        System.out.println("\nOrder Summary");
        System.out.println("---------------");

        for (int orderCups = 1; orderCups <= numberCups; orderCups++) {
            System.out.print("Cup " + orderCups + " has " + cupNarray[orderCups] + " shots which will cost you $");
            System.out.println(String.format("%.2f", ((float) (CUPS + (cupNarray[orderCups] * SHOTS)) / 100)));
        }

        int totPrice = ((totShots * SHOTS) + (numberCups * CUPS));

        if(cupIDarray.length == 2){
            System.out.println(numberCups + " coffee to purchase.");
        } else {
            System.out.println(numberCups + " coffees to purchase.");
        }
        System.out.println("Total price of the coffee's is $" + String.format("%.2f", (float) totPrice / 100) + ".\n");
        System.out.print("Proceed to payment? \n(Y/N): ");

        while (!YesNoQuestion()) {
            System.out.println("Come back next time," + name);
            return;
        }

        System.out.println("\nOrder Payment");
        System.out.println("-------------");
        System.out.println("$" + String.format("%.2f", (float) totPrice / 100) + " is to be paid. Enter note or coin.\n" +
                "In the format $##.##");

        int moneyRemain = totPrice;

        while (moneyRemain > 0) {
            System.out.print("\nRemaining to be paid is $" + String.format("%.2f", (float) moneyRemain / 100));
            System.out.println("\nApplicable Australian Currency\n------------------------------");
            for (int i = 0; i < MONEY.length; i++) {
                System.out.print("$" + String.format("%.2f", (float) MONEY[i] / 100) + " ");
            }
            System.out.print("\nMoney Input: ");

            pay = kyb.next();
            while (!Arrays.asList(MONEY_FORMAT).contains(pay)) {
                System.out.println("Invalid coin or note. Try again.");
                System.out.print("\nRemaining to be paid is $" + String.format("%.2f", (float) moneyRemain / 100));
                System.out.println("\nApplicable Australian Currency\n------------------------------");
                for (int i = 0; i < MONEY.length; i++) {
                    System.out.print("$" + String.format("%.2f", (float) MONEY[i] / 100) + " ");
                }
                System.out.print("\nMoney Input: ");
                pay = kyb.next();
            }
            moneyRemain = moneyRemain - (CheckPay());

        }

        // CHANGE

        if (moneyRemain == 0) {
            System.out.println("You gave " + pay);
            System.out.println("Perfect! No change given. Thank you, " + name);
            System.out.println("See you next time.");
        }

        if (moneyRemain < 0) {
            System.out.println("You gave " + pay);
            System.out.println("Your change: ");
            int change = moneyRemain * (-1);
            for (int z = 0; change > 0; z++) {
                int coinNumber = change / MONEY[z];
                change = change % MONEY[z];
                if (coinNumber != 0) {
                    System.out.println("$" + coinNumber + " x " + "$" + String.format("%.2f", (float) MONEY[z] / 100));
                }
            }
            System.out.println("\nThank you, " + name);
            System.out.println("See you next time.");
        }

    }

    // Method that is used for handling boolean based inputs/outputs.
    public static boolean YesNoQuestion() {

        String yesNoInput = kyb.next().toUpperCase();
        while ((!yesNoInput.equals("Y")) && (!yesNoInput.equals("N"))) {
            System.out.print("Try again, with the input of Y or N.\n(Y/N): ");
            yesNoInput = kyb.nextLine().toUpperCase();
        }
        if (yesNoInput.equals("N")) {
            return false;
        }
        return true;
    }


    // Method that is used to call integer value up for user string input of payment.
    public static int CheckPay() {
        for (int i = 0; i < MONEY.length; i++) {
            if (pay.equals(MONEY_FORMAT[i])) {
                return MONEY[i];
            }
        }
        return 0;
    }
}









