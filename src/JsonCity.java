public class JsonCity implements AstarableNode{
    private String name;
    private double latitude;
    private double longitude;

    public JsonCity(){
        this(null, 0, 0);
    }

    public JsonCity(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double distance(JsonCity otherCity) {
        double xDistance = Math.abs(this.longitude - otherCity.longitude);
        double yDistance = Math.abs(this.latitude - otherCity.latitude);
        return Math.sqrt(xDistance*xDistance + yDistance*yDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonCity jsonCity = (JsonCity) o;

        if (Double.compare(jsonCity.latitude, latitude) != 0) return false;
        if (Double.compare(jsonCity.longitude, longitude) != 0) return false;
        return name.equals(jsonCity.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String toString(){
        return name + " latitude " + latitude + " longitude " + longitude;
    }
}