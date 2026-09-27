import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Appointment {


    public static final int BUFFER_MINUTES = 15;

    private static final DateTimeFormatter LIST_DATE_FMT = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private static final DateTimeFormatter LIST_TIME_FMT = DateTimeFormatter.ofPattern("hh:mm a");

    private String customerName;
    private Service service;
    private Stylist stylist;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Appointmentstatus status = Appointmentstatus.PENDING;

    public Appointment(String customerName, Service service, Stylist stylist, LocalDate date, LocalTime startTime) {
        this.customerName = customerName;
        this.service = service;
        this.stylist = stylist;
        this.date = date;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(service.getDurationMinutes() + BUFFER_MINUTES);
    }

    public String getCustomerName() {
        return customerName;
    }

    public Service getService() {
        return service;
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


    public boolean overlaps(LocalDate otherDate, LocalTime otherStart, LocalTime otherEnd) {
        if (!this.date.equals(otherDate)) {
            return false;
        }
        return this.startTime.isBefore(otherEnd) && otherStart.isBefore(this.endTime);
    }

    public static LocalDate askDate(Scanner scanner) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        while (true) {
            System.out.print("Enter appointment date (MM/DD/YYYY): ");
            String input = scanner.nextLine();
            try {
                LocalDate parsed = LocalDate.parse(input, fmt);
                if (parsed.isBefore(LocalDate.now())) {
                    System.out.println("[!] Date cannot be in the past.");
                } else {
                    return parsed;
                }
            } catch (DateTimeParseException e) {
                System.out.println("[!] Invalid date format. Please use MM/DD/YYYY.");
            }
        }
    }

    public static LocalTime askTime(Scanner scanner) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");

        while (true) {
            System.out.print("Enter preferred start time (e.g. 02:30 PM): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return LocalTime.parse(input, fmt);
            } catch (DateTimeParseException e) {
                System.out.println("[!] Invalid time format. Please use hh:mm AM/PM.");
            }
        }
    }

    public static void printList(List<Appointment> appointments, boolean showCustomer) {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            if (showCustomer) {
                System.out.printf("%d. %s | %s-%s | Customer: %-10s | Stylist: %-12s | Service: %-18s | Status: %s%n",
                        i + 1,
                        a.date.format(LIST_DATE_FMT),
                        a.startTime.format(LIST_TIME_FMT),
                        a.endTime.format(LIST_TIME_FMT),
                        a.customerName,
                        a.stylist.getName(),
                        a.service.getName(),
                        a.status);
            } else {
                System.out.printf("%d. %s | %s-%s | Stylist: %-12s | Service: %-18s | Status: %s%n",
                        i + 1,
                        a.date.format(LIST_DATE_FMT),
                        a.startTime.format(LIST_TIME_FMT),
                        a.endTime.format(LIST_TIME_FMT),
                        a.stylist.getName(),
                        a.service.getName(),
                        a.status);
            }
        }
    }

    public void printSummary() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("hh:mm a");

        System.out.println("\n===== APPOINTMENT CONFIRMATION =====");
        System.out.println("Customer      : " + customerName);
        System.out.println("Service       : " + service.getName());
        System.out.println("Stylist       : " + stylist.getName() + " (" + stylist.getSpecialty() + ")");
        System.out.println("Date          : " + date.format(dateFmt));
        System.out.println("Start Time    : " + startTime.format(timeFmt));
        System.out.println("Service Time  : " + service.getDurationMinutes() + " mins");
        System.out.println("Buffer Time   : " + BUFFER_MINUTES + " mins (cleanup/prep)");
        System.out.println("End Time      : " + endTime.format(timeFmt));
        System.out.printf("Total Price   : PHP %.2f%n", service.getPrice());
        System.out.println("Status        : " + status + " (awaiting admin approval)");
        System.out.println("=====================================");
    }
}