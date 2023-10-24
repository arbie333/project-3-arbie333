package hotelapp;

import com.google.gson.annotations.SerializedName;
/**
 * The Hotel class represents a hotel with various properties, including its name, ID, coordinates, and address.
 */
public final class Hotel {

    @SerializedName(value = "f")
    private String name;

    private int id;

    @SerializedName(value = "ll")
    private Coordinates coordinates;

    // Address
    @SerializedName(value = "ad")
    private String address;
    @SerializedName(value = "ci")
    private String city;
    @SerializedName(value = "pr")
    private String state;
    @SerializedName(value = "c")
    private String country;

    /**
     * Get the name of the hotel.
     *
     * @return The name of the hotel.
     */
    public String getName() { return name; }

    /**
     * Get the ID of the hotel as a string.
     *
     * @return The ID of the hotel as a string.
     */
    public String getId() {
        return String.valueOf(id);
    }

    /**
     * Get the geographical coordinates of the hotel.
     *
     * @return The coordinates of the hotel.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Get the formatted address of the hotel, including street address, city, and state.
     *
     * @return The formatted address of the hotel.
     */
    public String getAddress() {
        return address + System.lineSeparator() + city + ", " + state;
    }

    /**
     * Returns a string representation of the hotel with its name, ID, and address.
     *
     * @return A string representation of the hotel.
     */
    @Override
    public String toString() {
        return System.lineSeparator() + "********************" + System.lineSeparator()
                + getName() + ": " + id + System.lineSeparator() + getAddress() + System.lineSeparator();
    }
}
