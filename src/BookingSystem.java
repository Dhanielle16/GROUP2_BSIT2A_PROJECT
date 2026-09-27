import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {

    public static void start(Scanner scanner, String customerName) {
        System.out.println("\n--- BOOK AN APPOINTMENT ---");


        Service chosenService = ServiceMenu.chooseService(scanner);


        LocalDate date = Appointment.askDate(scanner);


        LocalTime startTime;
        List<Stylist> availableStylists;

        while (true) {
            startTime = Appointment.askTime(scanner);
            LocalTime endTime = startTime.plusMinutes(chosenService.getDurationMinutes() + Appointment.BUFFER_MINUTES);

            availableStylists = new ArrayList<>();
            for (Stylist s : StylistMenu.getAllStylists()) {
                if (s.isAvailable() && AppointmentRegistry.isStylistAvailable(s, date, startTime, endTime)) {
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


        Stylist chosenStylist = StylistMenu.chooseStylist(scanner, availableStylists);


        Appointment appointment = new Appointment(customerName, chosenService, chosenStylist, date, startTime);
        AppointmentRegistry.addAppointment(appointment);

        appointment.printSummary();
    }
}