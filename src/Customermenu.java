import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Customermenu {

    public static void start(Scanner scanner, String customerName) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=================================");
            System.out.println("   WELCOME, " + customerName.toUpperCase());
            System.out.println("=================================");
            System.out.println("1. Book Appointment");
            System.out.println("2. View My Appointments");
            System.out.println("3. Log Out");
            System.out.println("=================================");

            int choice = InputUtils.readMenuChoice(scanner, 1, 3);

            switch (choice) {
                case 1:
                    BookingSystem.start(scanner, customerName);
                    break;
                case 2:
                    viewMyAppointments(customerName);
                    break;
                case 3:
                    System.out.println("\nLogging out...");
                    loggedIn = false;
                    break;
            }
        }
    }

    private static void viewMyAppointments(String customerName) {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println("\n--- MY APPOINTMENTS ---");
        boolean found = false;

        for (Appointment a : AppointmentRegistry.getAllAppointments()) {
            if (a.getCustomerName().equalsIgnoreCase(customerName)) {
                found = true;
                System.out.printf("%s | %s-%s | Stylist: %-12s | Service: %s%n",
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