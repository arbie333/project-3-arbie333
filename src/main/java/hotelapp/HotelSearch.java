package hotelapp;

import java.io.FileNotFoundException;
import java.util.Scanner;

/** The driver class for project 3.
 * The main function should take the following command line arguments:
 * -hotels hotelFile -reviews reviewsDirectory -threads numThreads -output filepath
 * (order may be different)
 * and read general information about the hotels from the hotelFile (a JSON file),
 * as well as concurrently read hotel reviews from the json files in reviewsDirectory.
 * The data should be loaded into data structures that allow efficient search.
 * If the -output flag is provided, hotel information (about all hotels and reviews)
 * should be output into the given file.
 * See pdf description of the project for the requirements.
 * You are expected to add other classes and methods from project 1 to this project,
 * and take instructor's / TA's feedback from a code review of project 1 into account.
 * Your program should still be able to support search functions like in project 1
 * (search does not need to be concurrent).
 */
public class HotelSearch {
    public static void main(String[] args) throws FileNotFoundException {
        // get args and parse json
        ArgParser.parseArg(args);
        ThreadSafeHotelData threadSafeHotelData = new ThreadSafeHotelData();
        ThreadSafeReviewData threadSafeReviewData = new ThreadSafeReviewData();
        WordData wordData = new WordData();

        JsonFileParser jsonFileParser = new JsonFileParser(threadSafeHotelData, threadSafeReviewData, wordData);
        jsonFileParser.getHotels(ArgParser.getPath("-hotel"));
        jsonFileParser.findAndParseJsonFiles(ArgParser.getPath("-review"));

        // run the searching interface
        while (true) {
            System.out.println("===========================================");
            System.out.println("===== Welcome to Hotel Review Search! =====");
            System.out.println("===========================================");
            System.out.println("Please enter these following commands:");
            System.out.println("find <hotelId> / findReviews <hotelId> / findWord <word> / q");
            System.out.println("===========================================");
            System.out.print("user input: ");

            Scanner sc = new Scanner(System.in);
            String inputString = sc.nextLine();

            if (inputString.equals("q")) {
                System.out.println("Bye");
                break;
            }

            String[] inputs = inputString.split(" ");
            switch (inputs[0]) {
                case "find" -> threadSafeHotelData.getHotel(inputs[1]);
                case "findReviews" -> threadSafeReviewData.printReviewByHID(inputs[1]);
                case "findWord" -> wordData.getReview(inputs[1], threadSafeReviewData);
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

