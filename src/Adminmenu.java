import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Adminmenu {

    public static void start(Scanner scanner, String adminUsername) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=================================");
            System.out.println("   ADMIN DASHBOARD - " + adminUsername.toUpperCase());
            System.out.println("=================================");
            System.out.println("1. View All Appointments");
            System.out.println("2. Cancel an Appointment");
            System.out.println("3. Manage Stylists");
            System.out.println("4. Manage Services");
            System.out.println("5. Log Out");
            System.out.println("=================================");

            int choice = InputUtils.readMenuChoice(scanner, 1, 5);

            switch (choice) {
                case 1:
                    viewAllAppointments();
                    break;
                case 2:
                    cancelAppointment(scanner);
                    break;
                case 3:
                    manageStylists(scanner);
                    break;
                case 4:
                    manageServices(scanner);
                    break;
                case 5:
                    System.out.println("\nLogging out...");
                    loggedIn = false;
                    break;
            }
        }
    }



    private static void viewAllAppointments() {
        List<Appointment> appointments = AppointmentRegistry.getAllAppointments();

        System.out.println("\n--- ALL BOOKED APPOINTMENTS ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments booked yet.");
            return;
        }

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            System.out.printf("%d. %s | %s-%s | Customer: %-10s | Stylist: %-12s | Service: %s%n",
                    i + 1,
                    a.getDate().format(dateFmt),
                    a.getStartTime().format(timeFmt),
                    a.getEndTime().format(timeFmt),
                    a.getCustomerName(),
                    a.getStylist().getName(),
                    a.getService().getName());
        }
    }

    private static void cancelAppointment(Scanner scanner) {
        List<Appointment> appointments = AppointmentRegistry.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments to cancel.");
            return;
        }

        viewAllAppointments();
        System.out.print("\nEnter the number to cancel (0 to go back): ");

        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    return;
                }
                if (choice >= 1 && choice <= appointments.size()) {
                    Appointment removed = appointments.remove(choice - 1);
                    System.out.println("\nCancelled appointment for " + removed.getCustomerName()
                            + " with " + removed.getStylist().getName());
                    return;
                }
                System.out.print("[!] Enter a number between 0 and " + appointments.size() + ": ");
            } else {
                System.out.print("[!] Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }



    private static void manageStylists(Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n--- MANAGE STYLISTS ---");
            System.out.println("1. View Stylists");
            System.out.println("2. Add Stylist");
            System.out.println("3. Remove Stylist");
            System.out.println("4. Mark Stylist as Busy");
            System.out.println("5. Mark Stylist as Available");
            System.out.println("6. Back");

            int choice = InputUtils.readMenuChoice(scanner, 1, 6);

            switch (choice) {
                case 1:
                    printStylists();
                    break;
                case 2:
                    addStylist(scanner);
                    break;
                case 3:
                    removeStylist(scanner);
                    break;
                case 4:
                    setStylistStatus(scanner, false);
                    break;
                case 5:
                    setStylistStatus(scanner, true);
                    break;
                case 6:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printStylists() {
        List<Stylist> stylists = StylistMenu.getAllStylists();

        System.out.println("\n--- CURRENT STYLISTS ---");
        if (stylists.isEmpty()) {
            System.out.println("No stylists on record.");
            return;
        }
        for (int i = 0; i < stylists.size(); i++) {
            Stylist s = stylists.get(i);
            String status = s.isAvailable() ? "Available" : "Busy";
            System.out.printf("%d. %-15s - %-22s [%s]%n", i + 1, s.getName(), s.getSpecialty(), status);
        }
    }

    private static void addStylist(Scanner scanner) {
        System.out.print("\nStylist Name: ");
        String name = scanner.nextLine();
        System.out.print("Specialty: ");
        String specialty = scanner.nextLine();

        StylistMenu.addStylist(new Stylist(name, specialty));
        System.out.println("\nStylist \"" + name + "\" added.");
    }

    private static void removeStylist(Scanner scanner) {
        printStylists();

        while (true) {
            System.out.print("\nEnter exact name of stylist to remove (0 to cancel): ");
            String name = scanner.nextLine();

            if (name.equals("0")) {
                return;
            }
            if (StylistMenu.removeStylist(name)) {
                System.out.println("\nStylist \"" + name + "\" removed.");
                return;
            }
            System.out.println("[!] Stylist not found. Please enter a valid name.");
        }
    }


    private static void setStylistStatus(Scanner scanner, boolean available) {
        printStylists();
        String label = available ? "Available" : "Busy";

        while (true) {
            System.out.print("\nEnter exact name of stylist to mark as " + label + " (0 to cancel): ");
            String name = scanner.nextLine();

            if (name.equals("0")) {
                return;
            }

            boolean success = available ? StylistMenu.markAvailable(name) : StylistMenu.markBusy(name);
            if (success) {
                System.out.println("\n" + name + " is now marked as " + label + ".");
                return;
            }
            System.out.println("[!] Stylist not found. Please enter a valid name.");
        }
    }



    private static void manageServices(Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n--- MANAGE SERVICES ---");
            System.out.println("1. View Services");
            System.out.println("2. Add Service");
            System.out.println("3. Update Service");
            System.out.println("4. Delete Service");
            System.out.println("5. Back");

            int choice = InputUtils.readMenuChoice(scanner, 1, 5);

            switch (choice) {
                case 1:
                    printServices();
                    break;
                case 2:
                    addService(scanner);
                    break;
                case 3:
                    updateService(scanner);
                    break;
                case 4:
                    deleteService(scanner);
                    break;
                case 5:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printServices() {
        List<Service> services = ServiceMenu.getAllServices();

        System.out.println("\n--- CURRENT SERVICES ---");
        if (services.isEmpty()) {
            System.out.println("No services on record.");
            return;
        }
        for (int i = 0; i < services.size(); i++) {
            Service s = services.get(i);
            System.out.printf("%d. %-20s (%3d mins) - PHP %.2f%n",
                    i + 1, s.getName(), s.getDurationMinutes(), s.getPrice());
        }
    }

    private static void addService(Scanner scanner) {
        System.out.print("\nService Name: ");
        String name = scanner.nextLine();
        int duration = InputUtils.readInt(scanner, "Duration (minutes): ");
        double price = InputUtils.readDouble(scanner, "Price (PHP): ");

        ServiceMenu.addService(new Service(name, duration, price));
        System.out.println("\nService \"" + name + "\" added.");
    }

    private static void updateService(Scanner scanner) {
        printServices();

        Service service;
        while (true) {
            System.out.print("\nEnter exact name of service to update (0 to cancel): ");
            String name = scanner.nextLine();

            if (name.equals("0")) {
                return;
            }
            service = ServiceMenu.findService(name);
            if (service != null) {
                break;
            }
            System.out.println("[!] Service not found. Please enter a valid name.");
        }

        System.out.print("New name (currently \"" + service.getName() + "\"): ");
        String newName = scanner.nextLine();
        int newDuration = InputUtils.readInt(scanner, "New duration in minutes (currently " + service.getDurationMinutes() + "): ");
        double newPrice = InputUtils.readDouble(scanner, "New price (currently PHP " + service.getPrice() + "): ");

        service.setName(newName);
        service.setDurationMinutes(newDuration);
        service.setPrice(newPrice);

        System.out.println("\nService updated successfully.");
    }

    private static void deleteService(Scanner scanner) {
        printServices();

        while (true) {
            System.out.print("\nEnter exact name of service to delete (0 to cancel): ");
            String name = scanner.nextLine();

            if (name.equals("0")) {
                return;
            }
            if (ServiceMenu.removeService(name)) {
                System.out.println("\nService \"" + name + "\" deleted.");
                return;
            }
            System.out.println("[!] Service not found. Please enter a valid name.");
        }
    }
}