import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {

    public static void start(Scanner scanner, String customerName) {

        System.out.println("\n--- BOOK AN APPOINTMENT ---");

        // ==========================================
        // STEP 1: CHOOSE SERVICES
        // ==========================================

        List<Service> selectedServices = new ArrayList<>();

        boolean addingServices = true;

        while (addingServices) {

            Service chosenService =
                    ServiceMenu.chooseService(scanner);

            selectedServices.add(chosenService);

            System.out.println(
                    "\n[+] " + chosenService.getName()
                            + " added to your appointment.");

            System.out.println(
                    "Current total service time: "
                            + getTotalDuration(selectedServices)
                            + " minutes");

            System.out.printf(
                    "Current total price: PHP %.2f%n",
                    getTotalPrice(selectedServices));

            while (true) {

                System.out.print(
                        "\nWould you like to add another service? (Y/N): ");

                String answer =
                        scanner.nextLine()
                                .trim()
                                .toUpperCase();

                if (answer.equals("Y")) {

                    break;

                } else if (answer.equals("N")) {

                    addingServices = false;
                    break;

                } else {

                    System.out.println(
                            "[!] Please enter Y or N.");
                }
            }
        }

        // ==========================================
        // DISPLAY SELECTED SERVICES
        // ==========================================

        System.out.println(
                "\n===== SELECTED SERVICES =====");

        for (int i = 0; i < selectedServices.size(); i++) {

            Service service = selectedServices.get(i);

            System.out.printf(
                    "%d. %-25s %3d mins | PHP %.2f%n",
                    i + 1,
                    service.getName(),
                    service.getDurationMinutes(),
                    service.getPrice()
            );
        }

        int totalDuration =
                getTotalDuration(selectedServices);

        double totalPrice =
                getTotalPrice(selectedServices);

        int totalOccupiedMinutes =
                totalDuration + Appointment.BUFFER_MINUTES;

        System.out.println(
                "------------------------------------------");

        System.out.println(
                "Total Service Time : "
                        + totalDuration
                        + " minutes");

        System.out.println(
                "Buffer Time        : "
                        + Appointment.BUFFER_MINUTES
                        + " minutes");

        System.out.println(
                "Total Occupied Time: "
                        + totalOccupiedMinutes
                        + " minutes");

        System.out.printf(
                "Total Price        : PHP %.2f%n",
                totalPrice);

        System.out.println(
                "==========================================");

        // ==========================================
        // STEP 2: CHOOSE DATE
        // ==========================================

        LocalDate date =
                Appointment.askDate(scanner);

        // ==========================================
        // STEP 3: CHOOSE TIME
        // ==========================================

        LocalTime startTime;

        List<Stylist> availableStylists;

        while (true) {

            startTime =
                    Appointment.askTime(scanner);

            LocalTime endTime =
                    startTime.plusMinutes(
                            totalOccupiedMinutes
                    );

            // ==========================================
            // FIND AVAILABLE STYLISTS
            // ==========================================

            availableStylists = new ArrayList<>();

            for (Stylist stylist :
                    StylistMenu.getAllStylists()) {

                if (AppointmentRegistry.isStylistAvailable(
                        stylist,
                        date,
                        startTime,
                        endTime)) {

                    availableStylists.add(stylist);
                }
            }

            // ==========================================
            // NO STYLIST AVAILABLE
            // ==========================================

            if (availableStylists.isEmpty()) {

                System.out.println(
                        "\n[!] No stylists are available for "
                                + "the entire requested time.");

                System.out.println(
                        "    Your appointment requires "
                                + totalOccupiedMinutes
                                + " minutes including buffer.");

                System.out.println(
                        "    Please choose a different time.");

            } else {

                break;
            }
        }

        // ==========================================
        // STEP 4: CHOOSE STYLIST
        // ==========================================

        Stylist chosenStylist =
                StylistMenu.chooseStylist(
                        scanner,
                        availableStylists
                );

        // ==========================================
        // STEP 5: CREATE ONE APPOINTMENT
        // ==========================================

        Appointment appointment =
                new Appointment(
                        customerName,
                        selectedServices,
                        chosenStylist,
                        date,
                        startTime
                );

        // ==========================================
        // STEP 6: DOUBLE-BOOKING SAFETY CHECK
        // ==========================================

        if (!AppointmentRegistry.isStylistAvailable(
                chosenStylist,
                date,
                startTime,
                appointment.getEndTime())) {

            System.out.println(
                    "\n[!] Sorry, that stylist is no longer "
                            + "available for the selected time.");

            System.out.println(
                    "Please start the booking again.");

            return;
        }

        // ==========================================
        // STEP 7: SAVE APPOINTMENT
        // ==========================================

        AppointmentRegistry.addAppointment(
                appointment
        );

        // ==========================================
        // STEP 8: DISPLAY CONFIRMATION
        // ==========================================

        appointment.printSummary();
    }

    // ==============================================
    // CALCULATE TOTAL SERVICE DURATION
    // ==============================================

    private static int getTotalDuration(
            List<Service> services) {

        int total = 0;

        for (Service service : services) {

            total += service.getDurationMinutes();
        }

        return total;
    }

    // ==============================================
    // CALCULATE TOTAL PRICE
    // ==============================================

    private static double getTotalPrice(
            List<Service> services) {

        double total = 0;

        for (Service service : services) {

            total += service.getPrice();
        }

        return total;
    }
}