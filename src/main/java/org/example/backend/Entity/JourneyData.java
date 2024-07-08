package org.example.backend.Entity;

public class JourneyData {
    private double latitude;
    private double longitude;
    private double pred0;
    private String location; // 新增地点信息

    // getters and setters

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getPred0() {
        return pred0;
    }

    public void setPred0(double pred0) {
        this.pred0 = pred0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
