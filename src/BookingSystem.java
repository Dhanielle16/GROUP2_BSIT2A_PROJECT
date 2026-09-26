import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {

    public static void start(Scanner scanner) {
        System.out.println("\n--- BOOK AN APPOINTMENT ---");


        ServiceMenu serviceMenu = new ServiceMenu();
        Service chosenService = serviceMenu.chooseService(scanner);


        LocalDate date = Appointment.askDate(scanner);


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


        Stylist chosenStylist = stylistMenu.chooseStylist(scanner, availableStylists);


        Appointment appointment = new Appointment(chosenService, chosenStylist, date, startTime);
        AppointmentRegistry.addAppointment(appointment);

        appointment.printSummary();
    }
}