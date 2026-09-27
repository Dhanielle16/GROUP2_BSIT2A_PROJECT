import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StylistMenu {
    // Static/shared so that stylists added or removed by a clerk
    // persist across every part of the program (not reset per instance).
    private static List<Stylist> stylists = new ArrayList<>();

    static {
        stylists.add(new Stylist("Ana Reyes", "Haircuts & Styling"));
        stylists.add(new Stylist("Mark Santos", "Hair Color Specialist"));
        stylists.add(new Stylist("Liza Cruz", "Treatments & Rebonding"));
        stylists.add(new Stylist("Joel Ramos", "All-around Stylist"));
    }

    public List<Stylist> getAllStylists() {
        return stylists;
    }

    public static void addStylist(Stylist stylist) {
        stylists.add(stylist);
    }

    public static boolean removeStylist(String name) {
        return stylists.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

    /**
     * Lets the user choose from a pre-filtered list of stylists
     * (e.g. only those available at the requested time slot).
     */
    public Stylist chooseStylist(Scanner scanner, List<Stylist> availableStylists) {
        Stylist selected = null;

        while (selected == null) {
            System.out.println("\n--- STYLISTS AVAILABLE FOR THIS TIME SLOT ---");
            for (int i = 0; i < availableStylists.size(); i++) {
                Stylist s = availableStylists.get(i);
                System.out.printf("%d. %-15s - %s%n", i + 1, s.getName(), s.getSpecialty());
            }
            System.out.print("Choose your hairstylist: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= availableStylists.size()) {
                    selected = availableStylists.get(choice - 1);
                } else {
                    System.out.println("[!] Invalid choice. Please pick a number from the list.");
                }
            } else {
                System.out.println("[!] Please enter a valid number.");
                scanner.nextLine();
            }
        }

        return selected;
    }
}