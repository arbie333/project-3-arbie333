package hotelapp;

import java.util.HashMap;
import java.util.Map;

public class ArgParser {
    private String review;
    private String reviewDiractory;
    private String hotel;
    private String hotelFile;

    private static final Map<String, String> argMap = new HashMap<>();

    public static void parseArg(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].charAt(0) != '-') {
                throw new IllegalArgumentException();
            }
            argMap.put(args[i], args[i+1]);
        }
    }

    public static String getValue(String key) {
        return argMap.get(key);
    }
}
