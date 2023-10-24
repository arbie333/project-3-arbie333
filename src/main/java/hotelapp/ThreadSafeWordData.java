package hotelapp;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ThreadSafeWordData class represents a thread-safe data structure for storing and searching words in reviews.
 */
public class ThreadSafeWordData {
    Map<String, TreeMap<Integer, TreeSet<String>>> map = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Checks if a word is a stop word or contains numbers.
     *
     * @param word The word to be checked.
     * @return True if the word is a stop word or contains numbers; false otherwise.
     */
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

    /**
     * Adds reviews to the word data, indexing words by frequency and review ID.
     *
     * @param reviews The list of reviews to be added to the word data.
     */
    public void addReviews (ArrayList<Review> reviews) {
        try {
            lock.writeLock().lock();
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
                        map.putIfAbsent(word, new TreeMap<>(Comparator.reverseOrder()));
                        TreeMap<Integer, TreeSet<String>> innerMap = map.get(word);
                        int freq = wordCount.get(word);
                        innerMap.putIfAbsent(freq, new TreeSet<>());
                        innerMap.get(freq).add(review.getReviewId());
                    }
                }
            }
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Searches for reviews that contain the given word and displays their information.
     *
     * @param word    The word to search for in reviews.
     * @param reviews The data source for reviews.
     */
    public void printReviews(String word, ThreadSafeReviewData reviews) {
        try {
            lock.readLock().lock();
            if (isStopWord(word)) {
                System.out.println("Please don't search stop words.");
            }

            TreeMap<Integer, TreeSet<String>> freqMap = map.get(word);

            if (freqMap == null) {
                System.out.println("Word not found, please try again.");
                return;
            }

            for (Map.Entry<Integer, TreeSet<String>> freq : freqMap.entrySet()) {
                for (String Rid : freq.getValue()) {
                    Set<Review> reviewsSet = reviews.getReviewByRID(Rid);
                    for (Review review : reviewsSet) {
                        System.out.println(review);
                    }
                    System.out.println("Frequency = " + freq.getKey());
                }
            }
        }
        finally {
            lock.readLock().unlock();
        }
    }
}
