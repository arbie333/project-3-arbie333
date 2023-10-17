package hotelapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
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

    public void printReviewByHID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByHID.get(targetId);

            if (reviews == null) {
                System.out.println("Hotel not found, please try again.");
                return;
            }

            // display the result
            for (Review review : reviews) {
                System.out.println(review);
                System.out.println("************************");
            }
        }
        finally {
            lock.readLock().unlock();
        }
    }

    public void printReviewByRID(String targetId) {
        try {
            lock.readLock().lock();
            TreeSet<Review> reviews = reviewsByRID.get(targetId);

            if (reviews == null) {
                System.out.println("Reviews not found, please try again.");
                return;
            }

            // display the result
            for (Review review : reviews) {
                System.out.println(review);
            }
        }
            finally {
            lock.readLock().unlock();
        }
    }
}
