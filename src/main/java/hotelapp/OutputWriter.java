package hotelapp;

import java.io.*;
import java.util.Map;

public class OutputWriter {
    private final ThreadSafeHotelData hotelData;
    private final ThreadSafeReviewData reviewData; // for findReviews

    public OutputWriter(ThreadSafeHotelData hotelData, ThreadSafeReviewData reviewData) {
        this.hotelData = hotelData;
        this.reviewData = reviewData;
    }

    public void writeToFile(String path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Map.Entry<String, Hotel> entry : hotelData.getHotels().entrySet()) {
                bw.write(entry.getValue().toString());

                if (reviewData.getReviewByHID(entry.getKey()) == null) {
                    continue;
                }

                for (Review review : reviewData.getReviewByHID(entry.getKey())) {
                    bw.write("--------------------");
                    bw.write(System.lineSeparator());
                    bw.write(review.toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
