import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceMenu {
    private List<Service> services;

    public ServiceMenu() {
        services = new ArrayList<>();
        // name, duration in minutes, price
        services.add(new Service("Haircut", 30, 250.00));
        services.add(new Service("Hair Color", 60, 800.00));
        services.add(new Service("Haircut and Color", 90, 950.00));
        services.add(new Service("Hair Treatment", 45, 600.00));
        services.add(new Service("Blow Dry", 20, 200.00));
        services.add(new Service("Rebond", 180, 1800.00));
        services.add(new Service("Perm", 120, 1500.00));
    }

    public Service chooseService(Scanner scanner) {
        Service selected = null;

        while (selected == null) {
            System.out.println("\n--- SALON SERVICES ---");
            for (int i = 0; i < services.size(); i++) {
                Service s = services.get(i);
                System.out.printf("%d. %-20s (%3d mins) - PHP %.2f%n",
                        i + 1, s.getName(), s.getDurationMinutes(), s.getPrice());
            }
            System.out.print("Choose a service: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= services.size()) {
                    selected = services.get(choice - 1);
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