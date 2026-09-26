import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class AppointmentRegistry {
    private static List<Appointment> bookedAppointments = new ArrayList<>();

    public static boolean isStylistAvailable(Stylist stylist, LocalDate date, LocalTime start, LocalTime end) {
        for (Appointment appt : bookedAppointments) {
            if (appt.getStylist().getName().equals(stylist.getName())
                    && appt.overlaps(date, start, end)) {
                return false;
            }
        }
        return true;
    }

    public static void addAppointment(Appointment appointment) {
        bookedAppointments.add(appointment);
    }

    public static List<Appointment> getAllAppointments() {
        return bookedAppointments;
    }
}
