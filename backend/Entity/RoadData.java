package backend.Entity;

public class RoadData {
    private int id;
    private String roadName;
    private String location;
    private String roadstatus;
    public RoadData(int id, String roadName, String location, String roadstatus) {
        this.id = id;
        this.roadName = roadName;
        this.location = location;
        this.roadstatus = roadstatus;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRoadName() {
        return roadName;
    }
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getRoadstatus() {
        return roadstatus;
    }
    public void setRoadstatus(String roadstatus) {
        this.roadstatus = roadstatus;
    }
}
