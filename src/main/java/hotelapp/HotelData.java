package hotelapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HotelData {
    private Map<String, Hotel> map = new HashMap<>();

    public void addHotels(ArrayList<Hotel> hotels) {
        for (Hotel hotel : hotels) {
            map.put(hotel.getId(), hotel);
        }
    }

    public void getHotel(String targetId) {
        System.out.println("Find Hotel");
        Hotel hotel = map.get(targetId);

        if (hotel == null) {
            System.out.println("Hotel not found, please try again.");
            return;
        }

        System.out.println(hotel);
    }
}
