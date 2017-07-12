package hotelpossystem;

import hotelpossystem.dao.DAOFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lingyanjiang
 */
public class Customer {

    private String name;
    private int id;
    private String userName;
    private String gender;
    private Order currentOrder;
    private Payment payment;
    private boolean logedin;
    private static Customer customer;
    private int score;
    private String roomNum;
    
    private Customer() {
        Order order = new Order(this);
        order.setTotal();
        this.currentOrder = order;
        try {
            //Set order id
            currentOrder.setId((DAOFactory.getUserDAOInstance().queryOrderMaxId()+1));
        } catch (Exception ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Customer(String name, int id, String gender) {
        this.name = name;
        this.id = id;
        this.gender = gender;
    }

    public static Customer getCustomerInstance() {
        if(customer==null){
            customer = new Customer();
            return customer;
        }else{
            return customer;
        }
    }
    
    public void logout(){
        this.id = 0;
        this.name = null;
        this.gender = null;
        this.payment = null;
        this.score = 0;
        this.roomNum = null;
        this.logedin = false;
        this.currentOrder = new Order(this);
    }
    
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }



    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * @return the login
     */
    public boolean isLogedin() {
        return logedin;
    }

    /**
     * @param login the login to set
     */
    public void setLogedin(boolean login) {
        this.logedin = login;
    }

    /**
     * @return the currentOrder
     */
    public Order getCurrentOrder() {
        if(currentOrder==null){
            return new Order(this);
        }
        else
            return currentOrder;
    }

    /**
     * @param currentOrder the currentOrder to set
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the roomNum
     */
    public String getRoomNum() {
        return roomNum;
    }

    /**
     * @param roomNum the roomNum to set
     */
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
