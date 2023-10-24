package hotelapp;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The ThreadSafeReviewData class represents a thread-safe data structure for storing and retrieving review information.
 */
public class ThreadSafeReviewData {
    // contains two maps
    private final Map<String, TreeSet<Review>> reviewsByHID = new HashMap<>(); // for findReviews
    private final Map<String, TreeSet<Review>> reviewsByRID = new HashMap<>(); // for findWord
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * Builds the data structure with a list of reviews.
     *
     * @param reviews The list of reviews to be added to the data structure.
     */
    public void build(ArrayList<Review> reviews) {
        try {
            lock.writeLock().lock();
            for (Review review : reviews) {
                String hotelId = review.getHotelId();
                reviewsByHID.putIfAbsent(hotelId, new TreeSet<>());
                reviewsByHID.get(hotelId).add(review);
            }

            for (Review review : reviews) {
                String reviewId = review.getReviewId();
                reviewsByRID.putIfAbsent(reviewId, new TreeSet<>());
                reviewsByRID.get(reviewId).add(review);
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Retrieves a set of reviews by hotel ID.
     *
     * @param targetId The ID of the hotel for which reviews are to be retrieved.
     * @return A set of reviews for the specified hotel or null if not found.
     */
    public Set<Review> getReviewByHID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByHID.get(targetId);

            if (reviews == null) {
                return null;
            }

            // display the result
            return Collections.unmodifiableSet(reviews);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Retrieves a set of reviews by review ID.
     *
     * @param targetId The ID of the review to be retrieved.
     * @return A set of reviews for the specified review ID or null if not found.
     */
    public Set<Review> getReviewByRID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByRID.get(targetId);

            if (reviews == null) {
                return null;
            }

            // display the result
            return Collections.unmodifiableSet(reviews);
        }
        finally {
            lock.readLock().unlock();
        }
    }
}
