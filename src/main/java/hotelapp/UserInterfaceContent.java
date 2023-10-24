package hotelapp;

/**
 * The UserInterfaceContent class provides methods for displaying messages and user instructions.
 */
public class UserInterfaceContent {
    /**
     * Prints a welcome message and user instructions.
     */
    public static void printMessage() {
        System.out.println("===========================================");
        System.out.println("===== Welcome to Hotel Review Search! =====");
        System.out.println("===========================================");
        System.out.println("Please enter these following commands:");
        System.out.println("find <hotelId> / findReviews <hotelId> / findWord <word> / q");
        System.out.println("===========================================");
        System.out.print("user input: ");
    }

    /**
     * Prints a goodbye message and user instructions.
     */
    public static void printBye() {
        System.out.println("""
                   -X$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$X-  \s
                 ^$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^\s
                >$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^
                $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                $$$$$$$$#,      .*$$$$$$$$$$$$$$$$$$$$$X,   >$$$$$$$$$$$
                $$$$$$$$#,   **   *?--~?$*--^##^,..-^#$#-   ?$$$$$$$$$$$
                $$$$$$$$#,   >>   ~X,  ^$.  *>   $$   =#~   #$$$$$$$$$$$
                $$$$$$$$#,      .>$$~  -X   =~   $$   -$*  .#$$$$$$$$$$$
                $$$$$$$$#,   >>   .$?   >   X~   _____=$^  .$$$$$$$$$$$$
                $$$$$$$$#,   >=    $$,  .  .$~   $$   -$$$$$$$$$$$$$$$$$
                $$$$$$$$#,        .$$=     ^$>        >#~   #$$$$$$$$$$$
                $$$$$$$$$$$$$$$$$$$$>,     #$$$=~..->#$$>***#$$$$$$$$$$$
                $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$>
                $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$^\s
                $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$#?~  \s
                $$$$$$                                                 \s
                $$$$$                                                  \s
                $$$                                                    \s
                X                       \s""");
    }
}
