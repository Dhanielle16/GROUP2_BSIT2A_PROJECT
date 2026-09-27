import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class AppointmentRegistry {
    private static final List<Appointment> BOOKED_APPOINTMENTS = new ArrayList<>();

    public static boolean isStylistAvailable(Stylist stylist, LocalDate date, LocalTime start, LocalTime end) {
        for (Appointment appt : BOOKED_APPOINTMENTS) {
            if (appt.getStatus() != Appointmentstatus.CANCELLED
                    && appt.getStylist().getName().equals(stylist.getName())
                    && appt.overlaps(date, start, end)) {
                return false;
            }
        }
        return true;
    }

    public static void addAppointment(Appointment appointment) {
        BOOKED_APPOINTMENTS.add(appointment);
    }

    public static List<Appointment> getAllAppointments() {
        return BOOKED_APPOINTMENTS;
    }
}