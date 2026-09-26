public class Service {
    private String name;
    private int durationMinutes;
    private double price;

    public Service(String name, int durationMinutes, double price) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getPrice() {
        return price;
    }
}