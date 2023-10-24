package hotelapp;

/**
 * The Coordinates class represents geographical coordinates with latitude and longitude.
 */
public class Coordinates {
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    public String toString() {
        return lat + " " + lng;
    }
}
