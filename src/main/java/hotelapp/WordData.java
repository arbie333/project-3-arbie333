package hotelapp;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordData {
    Map<String, TreeMap<Integer, HashSet<String>>> map = new HashMap<>();

    private static boolean isStopWord(String word) {
        Set<String> junkWord = Set.of("a", "the", "is", "are", "were", "and");
        if (junkWord.contains(word)) {
            return true;
        } else {
            Pattern pattern = Pattern.compile("/[0-9]*,*[0-9]*\\.*[0-9]*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(word);
            return matcher.find();
        }
    }

    public void addReviews (ArrayList<Review> reviews) {
        for (Review review : reviews) {
            // count the word frequency first
            HashMap<String, Integer> wordCount = new HashMap<>();
            for (String word : review.getReviewText().split(" ")) {
                if (!isStopWord(word)) {
                    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                }
            }

            // add to the outer map
            // outer map: key -> word, value -> inner map
            // inner map: key -> frequency, value -> list or review id
            for (String word : review.getReviewText().split(" ")) {
                if (!isStopWord(word)) {
                    map.putIfAbsent(word, new TreeMap<Integer, HashSet<String>>(Comparator.reverseOrder()));
                    TreeMap<Integer, HashSet<String>> innerMap = map.get(word);
                    int freq = wordCount.get(word);
                    innerMap.putIfAbsent(freq, new HashSet<>());
                    innerMap.get(freq).add(review.getReviewId());
                }
            }
        }
    }

    public void getReview(String word, ThreadSafeReviewData reviews) {
        if (isStopWord(word)) {
            System.out.println("Please don't search junky words.");
        }

        TreeMap<Integer, HashSet<String>> freqMap = map.get(word);

        if (freqMap == null) {
            System.out.println("Word not found, please try again.");
            return;
        }

        for (Map.Entry<Integer, HashSet<String>> freq : freqMap.entrySet()) {
            for (String Rid : freq.getValue()) {
                reviews.printReviewByRID(Rid);
                System.out.println(freq.getKey());
                System.out.println("************************");
            }
        }
    }
}
