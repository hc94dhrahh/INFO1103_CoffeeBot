/**
 * info1103 - assignment 3
 * <Felix Hu>
 * <fehu4705>
 */


import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CoffeeBot {
    static Scanner kyb;
    static final int[] MONEY = new int[]{10000, 5000, 2000, 1000, 500, 200, 100, 50, 20, 10, 5};
    static final String[] MONEY_FORMAT = new String[]{"$100.00", "$50.00", "$20.00", "$10.00"
            , "$5.00", "$2.00", "$1.00", "$0.50", "$0.20", "$0.10", "$0.05"};
    static final int CUPS_PRICE = 200;
    static final int SHOTS_PRICE = 100;
    static String pay;

    public static void main(String[] args) {
        // Checks if the input to args is valid.
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
        // Asks for intial details and if they want to order coffee.
        System.out.println("Hello, what's your name?");
        kyb = new Scanner(System.in);

        String name = kyb.next();

        System.out.println("Would you like to order some coffee, " + name + "? (y/n)");
        while (!YesNoQuestion()) {
            System.out.println("Come back next time, " + name + ".");
            return;
        }
        System.out.println("Great! Let's get started.\n");


        System.out.println("Order selection");
        System.out.println("---------------");

        System.out.println("There are " + stock[0] + " coffee cups in stock and each costs $"
                + String.format("%.2f", (float) CUPS_PRICE / 100) + ".");
        System.out.println("There are " + stock[1] + " coffee shots in stock and each costs $"
                + String.format("%.2f", (float) SHOTS_PRICE / 100) + ".");

        // Code that handles the amount of coffee cups.
        System.out.println("How many cups of coffee would you like?");
        int numberCups = kyb.nextInt();

        if (numberCups > stock[0])

        {
            System.out.println("Not enough stock. Comeback later.");
            return;
        }

        if (numberCups < 0)

        {
            System.out.println("Does not compute. System terminating.");
            return;
        }

        if (numberCups == 0)

        {
            System.out.println("No cups, no coffee. Goodbye.");
            return;
        }


        int totShots = 0;
        int[] cupIDarray = new int[numberCups + 1];
        int[] cupShotNarray = new int[numberCups + 1];

        // Code that handles the amount of shots.
        for (
                int cupNumber = 1;
                cupNumber <= numberCups; cupNumber++)

        {
            System.out.println("How many coffee shots in cup " + (cupNumber) + "?");

            int shotNumber = kyb.nextInt();

            while (shotNumber < 0 || shotNumber > stock[1] || shotNumber == 0) {
                if (shotNumber < 0) {
                    System.out.println("Does not compute. Try again.");
                    System.out.println("How many coffee shots in cup " + (cupNumber) + "?");
                    shotNumber = kyb.nextInt();
                    continue;
                }

                if (shotNumber > stock[1]) {
                    System.out.println("There are only" + stock[1] + "coffee shots left. Try again.");
                    System.out.println("How many coffee shots in cup " + (cupNumber) + "?");
                    shotNumber = kyb.nextInt();
                    continue;
                }
            }

            cupIDarray[cupNumber] = cupNumber;
            cupShotNarray[cupNumber] = shotNumber;
            totShots = totShots + shotNumber;
            stock[1] = stock[1] - shotNumber;
        }
        // Print out the cost, and proceed to payment question.
        System.out.println("\nOrder summary");
        System.out.println("-------------");

        for (
                int orderCups = 1;
                orderCups <= numberCups; orderCups++) {
            if (cupShotNarray[orderCups] == 1) {
                System.out.print("Cup " + orderCups + " has " + cupShotNarray[orderCups] + " shot and will cost $");
                System.out.println(String.format("%.2f", ((float) (CUPS_PRICE + (cupShotNarray[orderCups] * SHOTS_PRICE)) / 100)));
            } else {
                System.out.print("Cup " + orderCups + " has " + cupShotNarray[orderCups] + " shots and will cost $");
                System.out.println(String.format("%.2f", ((float) (CUPS_PRICE + (cupShotNarray[orderCups] * SHOTS_PRICE)) / 100)));
            }
        }

        int totPrice = ((totShots * SHOTS_PRICE) + (numberCups * CUPS_PRICE));
        if (cupIDarray.length == 2) {
            System.out.println(numberCups + " coffee to purchase.");
        } else {
            System.out.println(numberCups + " coffees to purchase.");
        }
        System.out.println("Purchase price is $" + String.format("%.2f", (float) totPrice / 100));
        System.out.println("Proceed to payment? (y/n)");

        while (!YesNoQuestion())

        {
            System.out.println("Come back next time, " + name + ".");
            return;
        }

        System.out.println("\nOrder payment");
        System.out.println("-------------");

        int moneyRemain = totPrice;

        // Code that checks the user input against the preset array.
        while (moneyRemain > 0)

        {
            System.out.println("$" + String.format("%.2f", (float) moneyRemain / 100)
                    + " remains to be paid. Enter coin or note:");


            pay = kyb.next();
            while (!Arrays.asList(MONEY_FORMAT).contains(pay)) {
                System.out.println("Invalid coin or note. Try again.");
                pay = kyb.next();
            }
            moneyRemain = moneyRemain - (CheckPay());

        }

        // Code handles change amount output.
        if (moneyRemain == 0)

        {
            System.out.println("\nYou gave " + pay);
            System.out.println("Perfect! No change given. Thank you, " + name);
            System.out.println("See you next time.");
        }

        if (moneyRemain < 0)

        {
            System.out.println("\nYou gave " + pay);
            System.out.println("Your change: ");
            int change = moneyRemain * (-1);
            for (int z = 0; change > 0; z++) {
                int coinNumber = change / MONEY[z];
                change = change % MONEY[z];
                if (coinNumber != 0) {
                    System.out.println(coinNumber + " x " + "$" + String.format("%.2f", (float) MONEY[z] / 100));
                }
            }
            System.out.println("\nThank you, " + name + ".");
            System.out.println("See you next time.");
        }

    }

    // Method that is used for handling boolean based inputs/outputs.
    public static boolean YesNoQuestion() {

        String yesNoInput = kyb.next().toUpperCase();
        
        while ((!yesNoInput.equals("Y")) && (!yesNoInput.equals("N"))) {
            System.out.println("Invalid response. Try again.");
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
