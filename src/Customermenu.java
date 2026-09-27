import java.util.ArrayList;
import java.util.List;
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
            System.out.println("3. Cancel an Appointment");
            System.out.println("4. Log Out");
            System.out.println("=================================");

            int choice = InputUtils.readMenuChoice(scanner, 1, 4);

            switch (choice) {
                case 1:
                    BookingSystem.start(scanner, customerName);
                    break;
                case 2:
                    viewMyAppointments(customerName);
                    break;
                case 3:
                    cancelAppointment(scanner, customerName);
                    break;
                case 4:
                    System.out.println("\nLogging out...");
                    loggedIn = false;
                    break;
            }
        }
    }

    private static List<Appointment> getMyAppointments(String customerName) {
        List<Appointment> mine = new ArrayList<>();
        for (Appointment a : AppointmentRegistry.getAllAppointments()) {
            if (a.getCustomerName().equalsIgnoreCase(customerName)) {
                mine.add(a);
            }
        }
        return mine;
    }

    private static void viewMyAppointments(String customerName) {
        List<Appointment> mine = getMyAppointments(customerName);

        System.out.println("\n--- MY APPOINTMENTS ---");
        if (mine.isEmpty()) {
            System.out.println("You have no booked appointments yet.");
            return;
        }
        Appointment.printList(mine, false);
    }

    private static void cancelAppointment(Scanner scanner, String customerName) {
        List<Appointment> cancellable = new ArrayList<>();
        for (Appointment a : getMyAppointments(customerName)) {
            if (a.getStatus() != Appointmentstatus.CANCELLED) {
                cancellable.add(a);
            }
        }

        if (cancellable.isEmpty()) {
            System.out.println("\nYou have no active appointments to cancel.");
            return;
        }

        System.out.println("\n--- YOUR ACTIVE APPOINTMENTS ---");
        Appointment.printList(cancellable, false);
        System.out.println("\nWhich one do you want to cancel? (0 to go back)");

        int choice = InputUtils.readMenuChoice(scanner, 0, cancellable.size());
        if (choice == 0) {
            return;
        }

        Appointment toCancel = cancellable.get(choice - 1);
        toCancel.setStatus(Appointmentstatus.CANCELLED);
        System.out.println("\nAppointment cancelled.");
    }
}