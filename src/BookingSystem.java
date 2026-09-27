import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {

    public static void start(Scanner scanner, String customerName) {
        System.out.println("\n--- BOOK AN APPOINTMENT ---");

        // 1. Choose the service first (its duration determines how long the slot needs to be)
        ServiceMenu serviceMenu = new ServiceMenu();
        Service chosenService = serviceMenu.chooseService(scanner);

        // 2. Choose the date
        LocalDate date = Appointment.askDate(scanner);

        // 3. Choose a time, then check which stylists are free for that slot.
        //    Keep asking for a time until at least one stylist is available.
        StylistMenu stylistMenu = new StylistMenu();
        LocalTime startTime;
        LocalTime endTime;
        List<Stylist> availableStylists;

        while (true) {
            startTime = Appointment.askTime(scanner);
            endTime = startTime.plusMinutes(chosenService.getDurationMinutes() + Appointment.BUFFER_MINUTES);

            availableStylists = new ArrayList<>();
            for (Stylist s : stylistMenu.getAllStylists()) {
                if (AppointmentRegistry.isStylistAvailable(s, date, startTime, endTime)) {
                    availableStylists.add(s);
                }
            }

            if (availableStylists.isEmpty()) {
                System.out.println("\n[!] No stylists are available at that time (including buffer). "
                        + "Please choose a different time.");
            } else {
                break;
            }
        }

        // 4. Choose a stylist from only the ones available at that time
        Stylist chosenStylist = stylistMenu.chooseStylist(scanner, availableStylists);

        // 5. Create and register the appointment so it blocks that stylist's slot going forward
        Appointment appointment = new Appointment(customerName, chosenService, chosenStylist, date, startTime);
        AppointmentRegistry.addAppointment(appointment);

        appointment.printSummary();
    }
}