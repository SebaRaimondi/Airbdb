package ar.edu.info.unlp.bd2.etapa2.model;

public class PrivateRoom extends Property {
    private int beds;

    public PrivateRoom() {
    }

    public PrivateRoom(String name, String description, double price, int capacity, City city, int beds) {
        super(name, description, price, capacity, city);
        this.beds = beds;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    @Override
    public String toString() {
        return "PrivateRoom{" +
                "beds=" + beds +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", city='" + city + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
