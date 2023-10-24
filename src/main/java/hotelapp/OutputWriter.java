package hotelapp;

import java.io.*;
import java.util.Set;

/**
 * The OutputWriter class is responsible for writing hotel and review data to a file.
 */
public class OutputWriter {
    private final ThreadSafeHotelData hotelData;
    private final ThreadSafeReviewData reviewData; // for findReviews

    /**
     * Creates an OutputWriter instance with the specified data sources.
     *
     * @param hotelData The data source for hotel information.
     * @param reviewData The data source for review information.
     */
    public OutputWriter(ThreadSafeHotelData hotelData, ThreadSafeReviewData reviewData) {
        this.hotelData = hotelData;
        this.reviewData = reviewData;
    }

    /**
     * Writes hotel and review data to a file specified by the given path.
     *
     * @param path The path to the file where data will be written.
     */
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
