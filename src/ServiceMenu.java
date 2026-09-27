import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Static list of salon services. Add new services here.
 */
public class ServiceMenu {
    private static final List<Service> SERVICES = new ArrayList<>();

    static {
        SERVICES.add(new Service("Haircut", 30, 250.00));
        SERVICES.add(new Service("Hair Color", 60, 800.00));
        SERVICES.add(new Service("Haircut and Color", 90, 950.00));
        SERVICES.add(new Service("Hair Treatment", 45, 600.00));
        SERVICES.add(new Service("Blow Dry", 20, 200.00));
        SERVICES.add(new Service("Rebond", 180, 1800.00));
    }

    public static List<Service> getAllServices() {
        return SERVICES;
    }

    public static void addService(Service service) {
        SERVICES.add(service);
    }

    public static boolean removeService(String name) {
        return SERVICES.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

    public static Service findService(String name) {
        for (Service s : SERVICES) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public static Service chooseService(Scanner scanner) {
        System.out.println("\n--- SALON SERVICES ---");
        for (int i = 0; i < SERVICES.size(); i++) {
            Service s = SERVICES.get(i);
            System.out.printf("%d. %-20s (%3d mins) - PHP %.2f%n",
                    i + 1, s.getName(), s.getDurationMinutes(), s.getPrice());
        }

        int choice = InputUtils.readMenuChoice(scanner, 1, SERVICES.size());
        return SERVICES.get(choice - 1);
    }
}