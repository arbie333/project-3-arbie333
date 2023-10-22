package hotelapp;

import com.google.gson.annotations.SerializedName;

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

    public String getName() { return name; }

    public String getId() {
        return String.valueOf(id);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getAddress() {
        return address + System.lineSeparator() + city + ", " + state;
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "********************" + System.lineSeparator()
                + getName() + ": " + id + System.lineSeparator() + getAddress() + System.lineSeparator();
    }
}
