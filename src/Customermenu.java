import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Customermenu {

    public static void start(Scanner scanner, String customerName) {
        boolean inCustomerMenu = true;

        while (inCustomerMenu) {
            System.out.println("\n=================================");
            System.out.println("   WELCOME, " + customerName.toUpperCase());
            System.out.println("=================================");
            System.out.println("1. Book Appointment");
            System.out.println("2. View My Appointments");
            System.out.println("3. Log Out");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        BookingSystem.start(scanner, customerName);
                        break;
                    case 2:
                        viewMyAppointments(customerName);
                        break;
                    case 3:
                        System.out.println("\nLogging out...");
                        inCustomerMenu = false;
                        break;
                    default:
                        System.out.println("\n[!] Invalid choice. Please select 1, 2, or 3.");
                }
            } else {
                System.out.println("\n[!] Input must be a valid number.");
                scanner.nextLine();
            }
        }
    }

    private static void viewMyAppointments(String customerName) {
        List<Appointment> all = AppointmentRegistry.getAllAppointments();

        System.out.println("\n--- MY APPOINTMENTS ---");
        boolean found = false;

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        for (Appointment a : all) {
            if (a.getCustomerName().equalsIgnoreCase(customerName)) {
                found = true;
                System.out.printf("%s | %s - %s | Stylist: %-12s | Service: %s%n",
                        a.getDate().format(dateFmt),
                        a.getStartTime().format(timeFmt),
                        a.getEndTime().format(timeFmt),
                        a.getStylist().getName(),
                        a.getService().getName());
            }
        }

        if (!found) {
            System.out.println("You have no booked appointments yet.");
        }
    }
}