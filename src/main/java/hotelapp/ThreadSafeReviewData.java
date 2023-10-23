package hotelapp;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeReviewData {
    // contains two maps
    private final Map<String, TreeSet<Review>> reviewsByHID = new HashMap<>(); // for findReviews
    private final Map<String, TreeSet<Review>> reviewsByRID = new HashMap<>(); // for findWord
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

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

    public Set<Review> getReviewByHID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByHID.get(targetId);

            if (reviews == null) {
//                System.out.println("Hotel not found, please try again.");
                return null;
            }

            // display the result
            return Collections.unmodifiableSet(reviews);
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public Set<Review> getReviewByRID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByRID.get(targetId);

            if (reviews == null) {
//                System.out.println("Reviews not found, please try again.");
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
