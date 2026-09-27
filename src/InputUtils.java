import java.util.Scanner;


public class InputUtils {


    public static int readMenuChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("[!] Please enter a number between " + min + " and " + max + ".");
            } else {
                System.out.println("[!] Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }
            System.out.println("[!] Please enter a valid whole number.");
            scanner.nextLine();
        }
    }

    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            }
            System.out.println("[!] Please enter a valid number.");
            scanner.nextLine();
        }
    }
}