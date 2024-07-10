package org.example.backend.Entity;

public class JourneyData {
    private Double latitude;//纬度
    private Double longitude;//经度
    private Double pred0;//拥堵程度
    private String location;//地点

    // Getters and setters

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPred0() {
        return pred0;
    }

    public void setPred0(Double pred0) {
        this.pred0 = pred0;
    }
}
