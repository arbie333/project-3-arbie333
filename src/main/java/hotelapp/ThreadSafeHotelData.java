package hotelapp;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The ThreadSafeHotelData class represents a thread-safe data structure for storing hotel information.
 */
public class ThreadSafeHotelData {
    private final Map<String, Hotel> map = new TreeMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Adds a list of hotels to the data structure.
     *
     * @param hotels The list of hotels to be added.
     */
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

    /**
     * Retrieves a hotel by its ID.
     *
     * @param targetId The ID of the hotel to be retrieved.
     * @return The hotel with the specified ID or null if not found.
     */
    public Hotel getHotel(String targetId) {
        try {
            lock.readLock().lock();
            return map.get(targetId);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves an unmodifiable set of hotel IDs.
     *
     * @return An unmodifiable set containing the IDs of all stored hotels.
     */
    public Set<String> getHotelIds() {
        return Collections.unmodifiableSet(map.keySet());
    }
}
