import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Appointment {

    public static final int BUFFER_MINUTES = 15;

    private static final DateTimeFormatter LIST_DATE_FMT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy");

    private static final DateTimeFormatter LIST_TIME_FMT =
            DateTimeFormatter.ofPattern("hh:mm a");

    private String customerName;
    private List<Service> services;
    private Stylist stylist;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Appointmentstatus status = Appointmentstatus.PENDING;

    public Appointment(
            String customerName,
            List<Service> services,
            Stylist stylist,
            LocalDate date,
            LocalTime startTime) {

        this.customerName = customerName;
        this.services = services;
        this.stylist = stylist;
        this.date = date;
        this.startTime = startTime;

        int totalMinutes = getTotalServiceDuration();

        // One 15-minute buffer for the entire appointment
        this.endTime = startTime.plusMinutes(
                totalMinutes + BUFFER_MINUTES
        );
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<Service> getServices() {
        return services;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Appointmentstatus getStatus() {
        return status;
    }

    public void setStatus(Appointmentstatus status) {
        this.status = status;
    }

    // Calculate total duration of all selected services
    public int getTotalServiceDuration() {

        int total = 0;

        for (Service service : services) {
            total += service.getDurationMinutes();
        }

        return total;
    }

    // Calculate total price of all selected services
    public double getTotalPrice() {

        double total = 0;

        for (Service service : services) {
            total += service.getPrice();
        }

        return total;
    }

    // Total time occupied by the stylist, including buffer
    public int getTotalOccupiedMinutes() {
        return getTotalServiceDuration() + BUFFER_MINUTES;
    }

    public boolean overlaps(
            LocalDate otherDate,
            LocalTime otherStart,
            LocalTime otherEnd) {

        if (!this.date.equals(otherDate)) {
            return false;
        }

        return this.startTime.isBefore(otherEnd)
                && otherStart.isBefore(this.endTime);
    }

    public static LocalDate askDate(Scanner scanner) {

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("MM/dd/yyyy");

        while (true) {

            System.out.print(
                    "Enter appointment date (MM/DD/YYYY): ");

            String input = scanner.nextLine();

            try {

                LocalDate parsed =
                        LocalDate.parse(input, fmt);

                if (parsed.isBefore(LocalDate.now())) {

                    System.out.println(
                            "[!] Date cannot be in the past.");

                } else {

                    return parsed;
                }

            } catch (DateTimeParseException e) {

                System.out.println(
                        "[!] Invalid date format. Please use MM/DD/YYYY.");
            }
        }
    }

    public static LocalTime askTime(Scanner scanner) {

        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("hh:mm a");

        while (true) {

            System.out.print(
                    "Enter preferred start time (e.g. 02:30 PM): ");

            String input =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            try {

                return LocalTime.parse(input, fmt);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "[!] Invalid time format. Please use hh:mm AM/PM.");
            }
        }
    }

    public static void printList(
            List<Appointment> appointments,
            boolean showCustomer) {

        for (int i = 0; i < appointments.size(); i++) {

            Appointment a = appointments.get(i);

            if (showCustomer) {

                System.out.printf(
                        "%d. %s | %s-%s | Customer: %-15s | Stylist: %-15s | Services: %-35s | Status: %s%n",
                        i + 1,
                        a.date.format(LIST_DATE_FMT),
                        a.startTime.format(LIST_TIME_FMT),
                        a.endTime.format(LIST_TIME_FMT),
                        a.customerName,
                        a.stylist.getName(),
                        a.getServiceNames(),
                        a.status
                );

            } else {

                System.out.printf(
                        "%d. %s | %s-%s | Stylist: %-15s | Services: %-35s | Status: %s%n",
                        i + 1,
                        a.date.format(LIST_DATE_FMT),
                        a.startTime.format(LIST_TIME_FMT),
                        a.endTime.format(LIST_TIME_FMT),
                        a.stylist.getName(),
                        a.getServiceNames(),
                        a.status
                );
            }
        }
    }

    // Get all service names in this appointment
    public String getServiceNames() {

        StringBuilder names = new StringBuilder();

        for (int i = 0; i < services.size(); i++) {

            names.append(services.get(i).getName());

            if (i < services.size() - 1) {
                names.append(", ");
            }
        }

        return names.toString();
    }

    public void printSummary() {

        DateTimeFormatter dateFmt =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        DateTimeFormatter timeFmt =
                DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println(
                "\n===== APPOINTMENT CONFIRMATION =====");

        System.out.println(
                "Customer      : " + customerName);

        System.out.println("\nServices:");

        for (Service service : services) {

            System.out.printf(
                    "  - %-25s %3d mins | PHP %.2f%n",
                    service.getName(),
                    service.getDurationMinutes(),
                    service.getPrice()
            );
        }

        System.out.println(
                "\nStylist       : "
                        + stylist.getName()
                        + " (" + stylist.getSpecialty() + ")");

        System.out.println(
                "Date          : "
                        + date.format(dateFmt));

        System.out.println(
                "Start Time    : "
                        + startTime.format(timeFmt));

        System.out.println(
                "Total Service : "
                        + getTotalServiceDuration()
                        + " mins");

        System.out.println(
                "Buffer Time   : "
                        + BUFFER_MINUTES
                        + " mins");

        System.out.println(
                "End Time      : "
                        + endTime.format(timeFmt));

        System.out.printf(
                "Total Price   : PHP %.2f%n",
                getTotalPrice());

        System.out.println(
                "Status        : "
                        + status
                        + " (awaiting admin approval)");

        System.out.println(
                "=====================================");
    }
}