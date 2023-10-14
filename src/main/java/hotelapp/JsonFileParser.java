package hotelapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonFileParser {
    private static void getReviews(String filename, ReviewData reviewCol, WordData wordCol) {
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
            reviewCol.build(reviews);
            wordCol.addReviews(reviews);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void getHotels(String filename, HotelData hotelData) {
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
            System.out.println(e);
        }
    }

    public static void findAndParseJsonFiles(String directory, ReviewData reviewCol, WordData wordCol) {
        // FILL IN CODE
        Path p = Paths.get(directory);
        try (DirectoryStream<Path> pathsInDir = Files.newDirectoryStream(p)) {
            for (Path path : pathsInDir) {
                String pathStr = path.toString();
                if (Files.isDirectory(path)) {
                    findAndParseJsonFiles(pathStr, reviewCol, wordCol);
                } else if (pathStr.endsWith(".json")) {
                    getReviews(pathStr, reviewCol, wordCol);
                }
            }
        } catch (IOException e) {
            System.out.println("Can not open directory: " + directory);
        }
    }
}
