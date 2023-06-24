package SARL.agents.utils.geolocation.mapbox;

public class Node {
    private double latitude;
    private double longitude;

    public Node(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Node{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}