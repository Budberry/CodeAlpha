import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private String location;
    private double price;
    private List<Room> rooms;

    public Hotel(String name, String location, double price) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.rooms = new ArrayList<>();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
}