package hotelapp;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Review implements Comparable<hotelapp.Review> {
    private String hotelId;
    private String reviewId;
    private double ratingOverall;
    private String title;
    private String reviewText;
    private String userNickname;

    @SerializedName(value = "reviewSubmissionTime")
    private String date;

    public Review(String hotelId, String reviewId, double ratingOverall, String title, String reviewText,
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

    public double getRatingOverall() {
        return ratingOverall;
    }

    public String getTitle() {
        return title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public LocalDate getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date.split("T")[0], formatter);
    }

    @Override
    public String toString() {
        String re = "hotelId = " + hotelId + "\t\n" +
                "reviewId = " + reviewId + "\t\n" +
                "averageRating = " + ratingOverall + "\t\n" +
                "title = " + title + "\t\n" +
                "reviewText = " + reviewText + "\t\n" +
                "userNickname = " + userNickname + "\t\n" +
                "submissionDate = " + date;

        return re;
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
