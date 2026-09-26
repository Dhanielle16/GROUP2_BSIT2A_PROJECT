import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        if (handleLogin(scanner)) {
                            BookingSystem.start(scanner);
                        }
                        break;
                    case 2:
                        if (handleSignUp(scanner)) {
                            BookingSystem.start(scanner);
                        }
                        break;
                    case 3:
                        System.out.println("\nExiting system. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("\n[!] Invalid choice. Please select 1, 2, or 3.");
                }
            } else {
                System.out.println("\n[!] Input must be a valid number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("=================================");
        System.out.println("     WELCOME TO LUXEBOOK SALON!  ");
        System.out.println("=================================");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.println("=================================");
    }

    private static boolean handleLogin(Scanner scanner) {
        System.out.println("\n--- LOG IN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Logging in user: " + username + "...");
        return true;
    }

    private static boolean handleSignUp(Scanner scanner) {
        System.out.println("\n--- SIGN UP ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Account created successfully for: " + username);
        return true;
    }
}