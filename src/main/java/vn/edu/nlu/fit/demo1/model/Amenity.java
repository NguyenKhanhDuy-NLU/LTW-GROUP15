package vn.edu.nlu.fit.demo1.model;
public class Amenity {
    private int id;
    private String amenityName;
    private String amenityIcon;
    private String description;

    public Amenity() {
    }

    public Amenity(int id, String amenityName, String amenityIcon) {
        this.id = id;
        this.amenityName = amenityName;
        this.amenityIcon = amenityIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String amenityName) {
        this.amenityName = amenityName;
    }

    public String getAmenityIcon() {
        return amenityIcon;
    }

    public void setAmenityIcon(String amenityIcon) {
        this.amenityIcon = amenityIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Amenity{" +
                "id=" + id +
                ", amenityName='" + amenityName + '\'' +
                ", amenityIcon='" + amenityIcon + '\'' +
                '}';
    }
}