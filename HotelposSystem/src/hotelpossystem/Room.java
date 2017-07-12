package hotelpossystem;

/**
 *
 * @author lingyanjiang
 */
public class Room {
    private double price;
    private String floor;
    private String type;
    private String id;
    
    public Room(){
        
    }
    
    public Room (String id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }
    
    
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getFloor() {
        return floor;
    }
    
    public String getType() {
        return type;
    }
    
    public String getId() {
        return id;
    }
}
