import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = InputUtils.readMenuChoice(scanner, 1, 3);

            switch (choice) {
                case 1:
                    handleLogIn(scanner);
                    break;
                case 2:
                    String username = customerSignUpPrompt(scanner);
                    Customermenu.start(scanner, username);
                    break;
                case 3:
                    System.out.println("\nExiting system. Goodbye!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("=================================");
        System.out.println("     WELCOME TO THE SALON SYSTEM  ");
        System.out.println("=================================");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.println("=================================");
    }

    private static void handleLogIn(Scanner scanner) {
        System.out.println("\n--- LOG IN ---");
        System.out.println("1. Log In as Customer");
        System.out.println("2. Log In as Admin");
        System.out.println("3. Back");

        int role = InputUtils.readMenuChoice(scanner, 1, 3);

        switch (role) {
            case 1:
                String customerUsername = customerLoginPrompt(scanner);
                Customermenu.start(scanner, customerUsername);
                break;
            case 2:
                String adminUsername = adminLoginPrompt(scanner);
                Adminmenu.start(scanner, adminUsername);
                break;
            case 3:
                break; // back to main menu
        }
    }

    private static String customerLoginPrompt(Scanner scanner) {
        System.out.println("\n--- CUSTOMER LOG IN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        scanner.nextLine();

        System.out.println("Logging in customer: " + username + "...");
        return username;
    }

    private static String customerSignUpPrompt(Scanner scanner) {
        System.out.println("\n--- CUSTOMER SIGN UP ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Email: ");
        scanner.nextLine();
        System.out.print("Enter Password: ");
        scanner.nextLine();

        System.out.println("Account created successfully for: " + username);
        return username;
    }

    private static String adminLoginPrompt(Scanner scanner) {
        System.out.println("\n--- ADMIN LOG IN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        scanner.nextLine();

        System.out.println("Logging in admin: " + username + "...");
        return username;
    }
}