package hotelapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class JsonFileParser {
    private final ExecutorService poolManager;
    private final Logger logger = LogManager.getLogger();
    private final Phaser phaser = new Phaser();
    private final ThreadSafeHotelData hotelData;
    private final ThreadSafeReviewData reviewData;
    private final WordData wordData;
    private String pathHotel = "";
    private String pathReview = "";

    public JsonFileParser(ThreadSafeHotelData hotelData, ThreadSafeReviewData reviewData, WordData wordData, String nThreadsStr, String pathHotel, String pathReview) {
        int nThreads = 3;
        if (nThreadsStr != null) {
            nThreads = Integer.parseInt(nThreadsStr);
        }

        this.hotelData = hotelData;
        this.reviewData = reviewData;
        this.wordData = wordData;
        this.poolManager = Executors.newFixedThreadPool(nThreads);
        this.pathHotel = pathHotel;
        this.pathReview = pathReview;
    }

    private class ParseWorker implements Runnable {
        private final Path dir;

        public ParseWorker(Path dir) {
            this.dir = dir;
        }

        @Override
        public void run() {
            getReviews(dir.toString());

            phaser.arriveAndDeregister();
            logger.debug("Worker working on " + dir.toString() + " finished work");
        }
    }

    private void getReviews(String filename) {
        ArrayList<Review> reviews;
        Gson gson = new Gson();

        // FILL IN CODE
        try(FileReader fr = new FileReader(filename)) {
            // get json files
            JsonObject jo = (JsonObject) JsonParser.parseReader(fr);
            JsonObject reviewDetails = jo.getAsJsonObject("reviewDetails");
            JsonObject reviewCollection = reviewDetails.getAsJsonObject("reviewCollection");
            JsonArray review = reviewCollection.getAsJsonArray("review");

            // get array of reviews
            Type reviewType = new TypeToken<ArrayList<Review>>(){}.getType();
            reviews = gson.fromJson(review, reviewType);

            // build maps
            reviewData.build(reviews);
            wordData.addReviews(reviews);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getHotels(String filename) {
        ArrayList<Hotel> hotels;
        Gson gson = new Gson();

        // FILL IN CODE
        try(FileReader fr = new FileReader(filename)) {
            JsonObject jo = (JsonObject) JsonParser.parseReader(fr);
            JsonArray jsonArr = jo.getAsJsonArray("sr");

            Type hotelType = new TypeToken<ArrayList<Hotel>>(){}.getType();
            hotels = gson.fromJson(jsonArr, hotelType);
            hotelData.addHotels(hotels);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void findAndParseJsonFiles(String directory) {
        // FILL IN CODE
        Path p = Paths.get(directory);

        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {
                String pathStr = path.toString();
                if (Files.isDirectory(path)) {
                    findAndParseJsonFiles(pathStr);
                } else if (pathStr.endsWith(".json")) {
                    ParseWorker firstWorker = new ParseWorker(path);
                    poolManager.submit(firstWorker);
                    phaser.register();
                    logger.debug("Created a worker for " + pathStr);
                }
            }
        } catch (IOException e) {
            System.out.println("Can not open directory: " + directory);
        }
    }

    public void parse() {
        if (pathHotel != null) {
            getHotels(pathHotel);
        }
        if (pathReview != null) {
            findAndParseJsonFiles(pathReview);
            phaser.awaitAdvance(phaser.getPhase()); // getPhase -> 到了沒
            poolManager.shutdown();
            try {
                poolManager.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
