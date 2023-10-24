package hotelapp;

import java.util.HashMap;
import java.util.Map;

/**
 * The ArgParser class is used to parse command line arguments
 */
public class ArgParser {
    private String review;
    private String reviewDiractory;
    private String hotel;
    private String hotelFile;

    // A map to store command line arguments
    private static final Map<String, String> argMap = new HashMap<>();

    /**
     * Parses command line arguments and populates the argument map.
     *
     * @param args An array of command line arguments.
     * @throws IllegalArgumentException if an argument is not preceded by a hyphen ('-').
     */
    public static void parseArg(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].charAt(0) != '-') {
                throw new IllegalArgumentException();
            }
            argMap.put(args[i], args[i+1]);
        }
    }

    /**
     * Retrieves the value associated with a given key from the argument map.
     *
     * @param key The key for which the value is to be retrieved.
     * @return The value associated with the key, or an empty string if the key is not found.
     */
    public static String getValue(String key) {
        return argMap.getOrDefault(key, "");
    }
}
