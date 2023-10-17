package hotelapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeHotelData {
    private Map<String, Hotel> map = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void addHotels(ArrayList<Hotel> hotels) {
        try {
            lock.writeLock().lock();
            for (Hotel hotel : hotels) {
                map.put(hotel.getId(), hotel);
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void getHotel(String targetId) {
        try {
            lock.readLock().lock();
            System.out.println("Find Hotel");
            Hotel hotel = map.get(targetId);

            if (hotel == null) {
                System.out.println("Hotel not found, please try again.");
                return;
            }

            System.out.println(hotel);
        }
        finally {
            lock.readLock().unlock();
        }
    }
}
