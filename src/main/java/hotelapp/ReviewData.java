package hotelapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ReviewData {
    // contains two maps
    private final Map<String, TreeSet<Review>> reviewMapByHID = new HashMap<>(); // for findReviews
    private final Map<String, TreeSet<Review>> reviewMapByRID = new HashMap<>(); // for findWord

    public void build(ArrayList<Review> reviews) {
        for (Review review : reviews) {
            String hotelId = review.getHotelId();
            reviewMapByHID.putIfAbsent(hotelId, new TreeSet<>());
            reviewMapByHID.get(hotelId).add(review);
        }

        for (Review review : reviews) {
            String reviewId = review.getReviewId();
            reviewMapByRID.putIfAbsent(reviewId, new TreeSet<>());
            reviewMapByRID.get(reviewId).add(review);
        }
    }

    public void printReviewByHID(String targetId) {
        TreeSet<Review> reviews = reviewMapByHID.get(targetId);

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

    public void printReviewByRID(String targetId) {
        TreeSet<Review> reviews = reviewMapByRID.get(targetId);

        if (reviews == null) {
            System.out.println("Reviews not found, please try again.");
            return;
        }

        // display the result
        for (Review review : reviews) {
            System.out.println(review);
        }
    }
}
