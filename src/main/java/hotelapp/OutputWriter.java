package hotelapp;

import java.io.*;
import java.util.Set;

public class OutputWriter {
    private final ThreadSafeHotelData hotelData;
    private final ThreadSafeReviewData reviewData; // for findReviews

    public OutputWriter(ThreadSafeHotelData hotelData, ThreadSafeReviewData reviewData) {
        this.hotelData = hotelData;
        this.reviewData = reviewData;
    }

    public void writeToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String id : hotelData.getHotelIds()) {
                bw.write(hotelData.getHotel(id).toString());

                Set<Review> reviews = reviewData.getReviewByHID(id);
                if (reviews == null) {
                    continue;
                }

                for (Review review : reviews) {
                    bw.write(review.toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
