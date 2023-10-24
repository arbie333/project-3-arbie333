package hotelapp;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Review class represents a hotel review with various properties, including the hotel ID, review ID, rating,
 * title, review text, user nickname, and submission date.
 */
public final class Review implements Comparable<hotelapp.Review> {
    private final String hotelId;
    private final String reviewId;
    private final int ratingOverall;
    private final String title;
    private final String reviewText;
    private String userNickname;

    @SerializedName(value = "reviewSubmissionTime")
    private final String date;

    public Review(String hotelId, String reviewId, int ratingOverall, String title, String reviewText,
                  String userNickname, String date) {
        this.hotelId = hotelId;
        this.reviewId = reviewId;
        this.ratingOverall = ratingOverall;
        this.title = title;
        this.reviewText = reviewText;
        this.userNickname = userNickname;
        this.date = date;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public int getRatingOverall() {
        return ratingOverall;
    }

    public String getTitle() {
        return title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getUserNickname() {
        if (userNickname.length() == 0) {
            this.userNickname = "Anonymous";
        }
        return userNickname;
    }

    public LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.split("T")[0], formatter);
    }

    @Override
    public String toString() {
        return "--------------------" + System.lineSeparator() +
                "Review by " + getUserNickname() + " on " + getDate() + System.lineSeparator() +
                "Rating: " + ratingOverall + System.lineSeparator() +
                "ReviewId: " + reviewId + System.lineSeparator() +
                title + System.lineSeparator() +
                reviewText + System.lineSeparator();
    }

    @Override
    public int compareTo(hotelapp.Review other) {
        int comparebyDate = other.getDate().compareTo(this.getDate());
        if (comparebyDate == 0) {
            return this.getReviewId().compareTo(other.getReviewId());
        }
        return comparebyDate;
    }
}
