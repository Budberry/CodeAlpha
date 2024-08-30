import java.time.*;
import java.util.*;

public class HotelReservationSystem {
    private static List<Hotel> hotels = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        // Initialize hotels
        hotels.add(new Hotel("Hotel A", "City A", 500.0));
        hotels.add(new Hotel("Hotel B", "City B", 400.0));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Search for hotels");
            System.out.println("2. Make a reservation");
            System.out.println("3. View booking details");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    searchHotels(scanner);
                    break;
                case 2:
                    makeReservation(scanner);
                    break;
                case 3:
                    viewBookingDetails(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void searchHotels(Scanner scanner) {
        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        System.out.println("Available hotels:");
        for (Hotel hotel : hotels) {
            if (hotel.getLocation().equals(city)) {
                System.out.println(hotel.getName() + " - $" + hotel.getPrice());
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your contact number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        LocalDate checkIn = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        LocalDate checkOut = LocalDate.parse(scanner.nextLine());

        Hotel selectedHotel = null;
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(hotelName)) {
                selectedHotel = hotel;
                break;
            }
        }

        if (selectedHotel != null) {
            
            Room availableRoom = null;
            for (Room room : selectedHotel.getRooms()) {
                if (room.isAvailable() && room.getCategory().toString().equalsIgnoreCase(selectedHotel.getRoomCategory())) {
                    availableRoom = room;
                    break;
                }
            }

            if (availableRoom != null) {
                // Create a reservation
                Customer customer = new Customer(name, contact);
                Reservation reservation = new Reservation(customer, availableRoom, checkIn, checkOut);
                reservations.add(reservation);
                availableRoom.setAvailable(false);

                System.out.println("Reservation confirmed!");
            } else {
                System.out.println("No rooms available for the selected dates.");
            }
        } else {
            System.out.println("Hotel not found.");
        }
    }

    private static void viewBookingDetails(Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getName().equals(name)) {
                System.out.println("Your reservation details:");
                System.out.println("Hotel: " + reservation.getHotel().getName());
                System.out.println("Room: " + reservation.getRoom().getRoomNumber());
                System.out.println("Check-in: " + reservation.getCheckIn());
                System.out.println("Check-out: " + reservation.getCheckOut());
            }
        }
    }
}