package hotelpossystem;

import UI.MainFrame;
import java.util.ArrayList;

/**
 * @author Shuang
 */
public class Order {
    private ArrayList <Room> rooms ;
    private ArrayList <Service> services;
    private Customer customer;
    private double serviceFee;
    private double roomFee;
    private double discount = 0.9;
    private int id;
    private boolean paid = false;
    
    public Order(Customer customer){
        this.customer = customer;
        this.rooms = new ArrayList<>();
    }
    
    public void addService(Service service) {
        services.add(service);
    }
    
    public void addService(ArrayList services) {
        this.services = services;
        for (int i=0; i < services.size(); i++) {
            setServiceFee(getServiceFee() + this.services.get(i).getPrice());
        }
    }
    
    public void subtractService(Service service) {
        services.remove(service);
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public void subtractRoom(Room room) {
        rooms.remove(room);
    }
    
    
    public void getDiscount(double discount) {
        this.setDiscount(discount);
    }
    
    public double getTotalFee() {
        double totalFee = 0;
        setServiceFee(0);
        setRoomFee(0);
        if(services==null)
            setServiceFee(0);
        if(services!=null){
            for (Service s : services) {
                setServiceFee(getServiceFee() + s.getPrice());
            }
        }
        if(rooms.size()<2){
            for(Room room:rooms){
                setRoomFee((getRoomFee() + room.getPrice())*(MainFrame.to-MainFrame.from));
            }
        }
        if(rooms.size()==2){
            setRoomFee((getRoomFee() + rooms.get(1).getPrice())*(MainFrame.to-MainFrame.from));
        }
        totalFee = getServiceFee()+getRoomFee();
        return Math.abs(totalFee);
    }
    
    
    public ArrayList getService() {
        return services;
    }
    
    public ArrayList getRoom() {
        return rooms;
    }
    

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public void setTotal(){
        
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * @return the paid
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    /**
     * @return the serviceFee
     */
    public double getServiceFee() {
        return serviceFee;
    }

    /**
     * @param serviceFee the serviceFee to set
     */
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    /**
     * @return the roomFee
     */
    public double getRoomFee() {
        return roomFee;
    }

    /**
     * @param roomFee the roomFee to set
     */
    public void setRoomFee(double roomFee) {
        this.roomFee = roomFee;
    }
    
    
}
