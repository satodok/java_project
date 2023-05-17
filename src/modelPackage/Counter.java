package modelPackage;

public class Counter {
    private String typeName;
    private int stationNumber;
    private int bikesRemaining;
    public Counter(){

    }

    public int getBikesRemaining() {
        return bikesRemaining;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setBikesRemaining(int bikesRemaining) {
        this.bikesRemaining = bikesRemaining;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
