package hotelpossystem.dao;

import hotelpossystem.Order;
import hotelpossystem.Payment;

/**
 * This interface defines query statements
 * @author lingyanjiang
 */
import hotelpossystem.Customer;
import hotelpossystem.Room;
import hotelpossystem.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public interface UserDAO {
    
    public void insert(Order order) throws Exception;
    public void insert(Payment payment) throws Exception;
    public void insert(Customer customer) throws Exception;
    public void insert(Service service) throws Exception;
    public void update(Order order) throws Exception;
    public void update(Customer customer) throws Exception;
    public void update(Room room,int checkinDate, int checkoutDate,String revertOrAdd) throws Exception;
    public void updataRenewRoom(Room room, int checkinDate, int checkoutDate) throws Exception;
    public void delete(Order order) throws Exception;
    public boolean queryLogin(String username, String password) throws Exception;
    public int queryOrderMaxId() throws Exception;
    public void queryRoomAvailable(Date checkinDate, Date checkoutDate) throws Exception;
    public boolean queryRoomAvailable(int checkinDate, int checkoutDate, String roomNumber) throws Exception;  
    public void queryRoomAvailableFloor(Date checkinDate, Date checkoutDate, String floor) throws Exception;
    public void queryRoomAvailable(Date checkinDate, Date checkoutDate, String floor, String roomtype) throws Exception;
    public boolean queryRoomAvailableToRenew(String customerName) throws ClassNotFoundException, SQLException;
    public HashSet<String[]> queryRoomAvailable(int checkin, int checkout) throws Exception;
    public ResultSet queryGetOrder();
    public void queryCustomer(String username);
    public String[] queryRoomAvailableByRoomNumber(String customerName)throws Exception;
    public void updateAfterPayment(Payment payment,Order order,Customer customer);
    public int queryTransMaxId() throws Exception;
}
