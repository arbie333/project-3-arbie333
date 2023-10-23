package hotelapp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeHotelData {
    private final Map<String, Hotel> map = new TreeMap<>();
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

    public Hotel getHotel(String targetId) {
        try {
            lock.readLock().lock();
//            System.out.println("Find Hotel");
//            Hotel hotel = ;

//            if (hotel == null) {
//                System.out.println("Hotel not found, please try again.");
//                return null;
//            }
            return map.get(targetId);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public Set<String> getHotelIds() {
        return Collections.unmodifiableSet(map.keySet());
    }
}
