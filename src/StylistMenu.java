import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Static list of stylists, shared across the whole program so that
 * additions/removals by an admin are immediately visible to customers.
 */
public class StylistMenu {
    private static final List<Stylist> STYLISTS = new ArrayList<>();

    static {
        STYLISTS.add(new Stylist("Ana Reyes", "Haircuts & Styling"));
        STYLISTS.add(new Stylist("Mark Santos", "Hair Color Specialist"));
        STYLISTS.add(new Stylist("Liza Cruz", "Treatments & Rebonding"));
        STYLISTS.add(new Stylist("Joel Ramos", "All-around Stylist"));
    }

    public static List<Stylist> getAllStylists() {
        return STYLISTS;
    }

    public static void addStylist(Stylist stylist) {
        STYLISTS.add(stylist);
    }

    public static boolean removeStylist(String name) {
        return STYLISTS.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

    public static boolean markBusy(String name) {
        return setAvailability(name, false);
    }

    public static boolean markAvailable(String name) {
        return setAvailability(name, true);
    }

    private static boolean setAvailability(String name, boolean available) {
        for (Stylist s : STYLISTS) {
            if (s.getName().equalsIgnoreCase(name)) {
                s.setAvailable(available);
                return true;
            }
        }
        return false;
    }

    /** Lets the user pick from a pre-filtered list (e.g. only stylists free at a given time). */
    public static Stylist chooseStylist(Scanner scanner, List<Stylist> available) {
        System.out.println("\n--- STYLISTS AVAILABLE FOR THIS TIME SLOT ---");
        for (int i = 0; i < available.size(); i++) {
            Stylist s = available.get(i);
            System.out.printf("%d. %-15s - %s%n", i + 1, s.getName(), s.getSpecialty());
        }

        int choice = InputUtils.readMenuChoice(scanner, 1, available.size());
        return available.get(choice - 1);
    }
}