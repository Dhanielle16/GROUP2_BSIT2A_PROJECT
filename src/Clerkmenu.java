import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Clerkmenu {

    public static void start(Scanner scanner) {
        boolean inClerkMenu = true;

        while (inClerkMenu) {
            System.out.println("\n=================================");
            System.out.println("         CLERK DASHBOARD          ");
            System.out.println("=================================");
            System.out.println("1. View All Appointments");
            System.out.println("2. Cancel an Appointment");
            System.out.println("3. Manage Stylists");
            System.out.println("4. Log Out");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

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
                        System.out.println("\nLogging out of clerk dashboard...");
                        inClerkMenu = false;
                        break;
                    default:
                        System.out.println("\n[!] Invalid choice. Please select 1-4.");
                }
            } else {
                System.out.println("\n[!] Input must be a valid number.");
                scanner.nextLine();
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
            System.out.printf("%d. %s | %s - %s | Customer: %-12s | Stylist: %-12s | Service: %s%n",
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
        System.out.print("\nEnter the number of the appointment to cancel (0 to go back): ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                return;
            }
            if (choice >= 1 && choice <= appointments.size()) {
                Appointment removed = appointments.remove(choice - 1);
                DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");
                System.out.println("\nCancelled: " + removed.getStylist().getName()
                        + " on " + removed.getDate() + " at " + removed.getStartTime().format(timeFmt));
            } else {
                System.out.println("\n[!] Invalid selection.");
            }
        } else {
            System.out.println("\n[!] Please enter a valid number.");
            scanner.nextLine();
        }
    }

    private static void manageStylists(Scanner scanner) {
        boolean inStylistMenu = true;

        while (inStylistMenu) {
            System.out.println("\n--- MANAGE STYLISTS ---");
            System.out.println("1. View Stylists");
            System.out.println("2. Add Stylist");
            System.out.println("3. Remove Stylist");
            System.out.println("4. Back to Clerk Dashboard");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

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
                        inStylistMenu = false;
                        break;
                    default:
                        System.out.println("\n[!] Invalid choice. Please select 1-4.");
                }
            } else {
                System.out.println("\n[!] Input must be a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void printStylists() {
        List<Stylist> stylists = new StylistMenu().getAllStylists();

        System.out.println("\n--- CURRENT STYLISTS ---");
        if (stylists.isEmpty()) {
            System.out.println("No stylists on record.");
            return;
        }
        for (int i = 0; i < stylists.size(); i++) {
            Stylist s = stylists.get(i);
            System.out.printf("%d. %-15s - %s%n", i + 1, s.getName(), s.getSpecialty());
        }
    }

    private static void addStylist(Scanner scanner) {
        System.out.print("\nEnter new stylist's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter specialty: ");
        String specialty = scanner.nextLine();

        StylistMenu.addStylist(new Stylist(name, specialty));
        System.out.println("\nStylist \"" + name + "\" added successfully.");
    }

    private static void removeStylist(Scanner scanner) {
        printStylists();
        System.out.print("\nEnter the exact name of the stylist to remove: ");
        String name = scanner.nextLine();

        boolean removed = StylistMenu.removeStylist(name);
        if (removed) {
            System.out.println("\nStylist \"" + name + "\" removed successfully.");
        } else {
            System.out.println("\n[!] Stylist not found.");
        }
    }
}