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
                        handleLogIn(scanner);
                        break;
                    case 2:
                        handleSignUp(scanner);
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
        System.out.println("     WELCOME TO THE SALON SYSTEM  ");
        System.out.println("=================================");
        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");
        System.out.println("=================================");
    }


    private static int askRole(Scanner scanner, String action) {
        while (true) {
            System.out.println("\n--- " + action.toUpperCase() + " ---");
            System.out.println("1. " + action + " as Customer");
            System.out.println("2. " + action + " as Clerk");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
                System.out.println("\n[!] Invalid choice. Please select 1, 2, or 3.");
            } else {
                System.out.println("\n[!] Input must be a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void handleLogIn(Scanner scanner) {
        int role = askRole(scanner, "Log In");

        if (role == 1) {
            String username = customerLoginPrompt(scanner);
            Customermenu.start(scanner, username);
        } else if (role == 2) {
            if (clerkLoginPrompt(scanner)) {
                Clerkmenu.start(scanner);
            }
        }

    }

    private static void handleSignUp(Scanner scanner) {
        int role = askRole(scanner, "Sign Up");

        if (role == 1) {
            String username = customerSignUpPrompt(scanner);
            Customermenu.start(scanner, username);
        } else if (role == 2) {
            if (clerkSignUpPrompt(scanner)) {
                Clerkmenu.start(scanner);
            }
        }

    }

    private static String customerLoginPrompt(Scanner scanner) {
        System.out.println("\n--- CUSTOMER LOG IN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.println("Logging in customer: " + username + "...");
        return username;
    }

    private static String customerSignUpPrompt(Scanner scanner) {
        System.out.println("\n--- CUSTOMER SIGN UP ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Account created successfully for: " + username);
        return username;
    }

    private static boolean clerkLoginPrompt(Scanner scanner) {
        System.out.println("\n--- CLERK LOG IN ---");
        System.out.print("Clerk Username: ");
        String username = scanner.nextLine();
        System.out.print("Clerk Password: ");
        String password = scanner.nextLine();

        Clerk clerk = new Clerk(username);
        System.out.println("Logging in clerk: " + clerk.getUsername() + "...");
        return true;
    }

    private static boolean clerkSignUpPrompt(Scanner scanner) {
        System.out.println("\n--- CLERK SIGN UP ---");
        System.out.print("Enter Clerk Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Clerk clerk = new Clerk(username);
        System.out.println("Clerk account created for: " + clerk.getUsername());
        return true;
    }
}