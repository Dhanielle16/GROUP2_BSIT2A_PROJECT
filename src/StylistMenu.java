import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StylistMenu {
    private List<Stylist> stylists;

    public StylistMenu() {
        stylists = new ArrayList<>();
        stylists.add(new Stylist("Ana Reyes", "Haircuts & Styling"));
        stylists.add(new Stylist("Mark Santos", "Hair Color Specialist"));
        stylists.add(new Stylist("Liza Cruz", "Treatments & Rebonding"));
        stylists.add(new Stylist("Joel Ramos", "All-around Stylist"));
    }

    public List<Stylist> getAllStylists() {
        return stylists;
    }


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