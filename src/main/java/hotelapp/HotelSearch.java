package hotelapp;

import java.util.Scanner;
import java.util.Set;

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
    public static void main(String[] args) {
        // get args and parse json
        ArgParser.parseArg(args);
        ThreadSafeHotelData threadSafeHotelData = new ThreadSafeHotelData();
        ThreadSafeReviewData threadSafeReviewData = new ThreadSafeReviewData();
        ThreadSafeWordData wordData = new ThreadSafeWordData();

        String nThreads = ArgParser.getValue("-thread");
        String pathHotel = ArgParser.getValue("-hotels");
        String pathReview = ArgParser.getValue("-reviews");

        JsonFileParser jsonFileParser = new JsonFileParser(threadSafeHotelData, threadSafeReviewData, wordData, nThreads, pathHotel, pathReview);
        jsonFileParser.parse();

        if (!ArgParser.getValue("-output").isEmpty()) {
            OutputWriter ow = new OutputWriter(threadSafeHotelData, threadSafeReviewData);
            ow.writeToFile(ArgParser.getValue("-output"));
            return;
        }

        // run the searching interface
        while (true) {
            UserInterfaceContent.printMessage();

            Scanner sc = new Scanner(System.in);
            String inputString = sc.nextLine();

            if (inputString.equals("q")) {
                UserInterfaceContent.printBye();
                break;
            }

            String[] inputs = inputString.split(" ");

            if (inputs.length == 1) {
                System.out.println("Invalid input, please try again.");
                continue;
            }

            switch (inputs[0]) {
                case "find" -> {
                    Hotel hotel = threadSafeHotelData.getHotel(inputs[1]);
                    if (hotel == null) {
                        System.out.println("Hotel not found");
                        continue;
                    }
                    System.out.println(hotel);
                }
                case "findReviews" -> {
                    Set<Review> reviewSet = threadSafeReviewData.getReviewByHID(inputs[1]);
                    if (reviewSet == null) {
                        System.out.println("Reviews not found");
                        continue;
                    }
                    for (Review review : threadSafeReviewData.getReviewByHID(inputs[1])) {
                        System.out.println(review);
                    }
                }
                case "findWord" -> wordData.printReviews(inputs[1], threadSafeReviewData);
                default -> System.out.println("Invalid input. Please try again.");
            }
        }
    }
}

