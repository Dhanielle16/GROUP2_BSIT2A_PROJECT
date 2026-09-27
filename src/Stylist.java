public class Stylist {
    private String name;
    private String specialty;
    private boolean available = true; // manual front-desk status, set by an admin

    public Stylist(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}