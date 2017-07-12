package hotelpossystem.dao;

import hotelpossystem.Customer;
import hotelpossystem.Order;
import hotelpossystem.PayByCard;
import hotelpossystem.Payment;
import hotelpossystem.Room;
import hotelpossystem.Service;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

/**
 *
 * @author lingyanjiang
 */
public class UserDAOImplement implements UserDAO {

    
    
   
        
    @Override
    /**
     * This method is to insert new order to database
     */
    public void insert(Order order) throws Exception {
        int id = order.getId();
        String service = "";
        String room = "";
        String customerUsername = order.getCustomer().getUserName();
        System.out.println(customerUsername);
        if(order.getRoom().size()>0){
            room = ((Room)(order.getRoom().get(0))).getId().toString();
        }
        if(order.getService()!=null){
            service = order.getService().toString();
        }
        double total = order.getTotalFee();
        boolean isPaid = order.isPaid();
        String sql = String.format("INSERT INTO `hotel`.`order` (`id`, `customer`, `room`, `service`, `total`, `ispaid`)"
                + " VALUES ('%d', '%s', '%s', '%s', '%.2f', '%s')",id,customerUsername,room,service,total,String.valueOf(isPaid));
        try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();) {
            state.execute(sql);
        }
    }

    @Override
    public void insert(Payment payment) throws Exception {
        int paymentNumber = payment.getTransactionNumber();
        Date time = payment.getTime();
        Date date = payment.getDate();
        String type = null;
        if(payment instanceof PayByCard)
            type = "card";
        else
            type = "cash";
        
        Double total = payment.getTotal();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("H:m:s");
        String sql = String.format("INSERT INTO `hotel`.`payment` (`paymentnumber`, `time`, `date`, `type`, `total`) "
                + "VALUES ('%d', '%s', '%s', '%s', '%.2f');",paymentNumber,tf.format(time),df.format(date),type,total);
        
        try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();) {
            state.execute(sql);
        }
    }

    @Override
    public void insert(Customer customer) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insert(Service service) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Order order) throws Exception {
       
    }

    @Override
    public void update(Customer customer) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Room room,int checkinDate,int checkoutDate,String revertOrAdd) throws Exception {
        
        try (Connection conn = new DatabaseConnection().getConnection();
            Statement state = conn.createStatement();) {
            if(revertOrAdd.equals("add")){
            for(int i=checkinDate;i<checkoutDate;i++){
            state.execute(String.format("UPDATE `hotel`.`room` SET `day"+(i+1)+"`"+"='0' WHERE `id`='%s'",room.getId()));
            }
            }else{
            for(int i=checkinDate;i<checkoutDate;i++){
            state.execute(String.format("UPDATE `hotel`.`room` SET `day"+(i+1)+"`"+"='1' WHERE `id`='%s'",room.getId()));
            }
            }
        }
    }

    @Override
    public void updataRenewRoom(Room room, int checkinDate, int checkoutDate) throws Exception{
        try (Connection conn = new DatabaseConnection().getConnection();
            Statement state = conn.createStatement();) {
            int i=checkinDate;
            state.execute(String.format("UPDATE `hotel`.`room` SET `day"+(i+1)+"`"+"='0' WHERE `id`='%s'",room.getId()));
        }
    }
    
    @Override
    public void delete(Order order) throws Exception {
        String sql = "delete from hotel.`order` where id = "+order.getId();
        try (Connection conn = new DatabaseConnection().getConnection();
            Statement state = conn.createStatement();) {
            state.execute(sql);
        }       
    }

    @Override
    public boolean queryLogin(String username, String password) throws Exception {
        String sql = "select * from customer where username= '" + username + "' and password ='" + password + "'";
        try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();) {
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void queryCustomer(String username) {
        String sql = "select * from customer where username='" + username+"'";
        ResultSet rs = null;
        Customer customer = Customer.getCustomerInstance();
        try(Connection conn = new DatabaseConnection().getConnection();
            Statement state = conn.createStatement()){
            rs = state.executeQuery(sql);
            while(rs.next()){
                    customer.setName(rs.getString("name"));
                    customer.setGender(rs.getString("gender"));
                    customer.setUserName(rs.getString("username"));
                    customer.setScore(rs.getInt("score"));
                    customer.setRoomNum(rs.getString("roomstatus"));
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String[] queryRoomAvailableByRoomNumber(String customerName) {
        String[] ss = null;
         try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery("select * from customer where username="+"'"+customerName+"';")) {               
                
                String name = null, room = null,price=null;
                
                Date checkinTime = null, checkoutTime = null;
            while (rs.next()) {
                name = rs.getString("name");
                room = rs.getString("roomstatus");
                checkinTime = rs.getDate("checkintime");
                checkoutTime = rs.getDate("checkouttime");         
            }
            ResultSet rs1= state.executeQuery("select  price from room where ID="+"'"+room+"'");
            while(rs1.next()) {
                price=(rs1.getString("price"));
            }
            ss = new String[]{name, room, price,checkinTime.toString(), checkoutTime.toString()};
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplement.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ss;
    }
    
    @Override
    public int queryOrderMaxId() throws Exception {
        String sql = "select * from hotel.`order` order by id desc limit 1;";
        int id=0;
         try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery(sql)) {   
             while(rs.next())
                 id = rs.getInt("id");
         }
         return id;
    }
    
    @Override
    public int queryTransMaxId() throws Exception {
        String sql = "select * from hotel.`payment` order by paymentnumber desc limit 1;";
        int id=0;
         try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery(sql)) {   
             while(rs.next())
                 id = rs.getInt("paymentnumber");
         }
         return id;
    }

    @Override
    public void queryRoomAvailable(Date checkinDate, Date checkoutDate) throws Exception {

    }

    @Override
    public void updateAfterPayment(Payment payment, Order order, Customer customer) {
        try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement()){
            String roomNum = null;
            if(!order.getRoom().isEmpty()){
                 roomNum = ((Room)(order.getRoom().get(0))).getId();
            }
            int orderId = order.getId();
            String userName = order.getCustomer().getUserName();
            //update payment
            insert(payment);
            //update order status
            order.setPaid(true);
            state.execute(String.format("UPDATE `hotel`.`order` SET `paymentnumber`='%d', `ispaid`='%s' WHERE `id`='%d';",payment.getTransactionNumber(),"true",order.getId()));
            //update customer status
            String s;
            s = String.format("UPDATE `hotel`.`customer` SET `roomstatus`='%s', `orderid`='%d' WHERE `username`='%s';",roomNum,orderId,userName);
            state.execute(s);
            
        } catch (Exception ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
            
    

    /**
     *
     * @param checkinDate
     * @param checkOutDate
     * @return
     * @throws SQLException
     */
    class Result {

        String id;
        String floor;
    }

    @Override
    public HashSet<String[]> queryRoomAvailable(int checkinDate, int checkOutDate) throws SQLException {
        ResultSet r, r1;
        //ArrayList<Result> rs=new ArrayList();
        String id = null, floor = null, type = null, price = null;
        //String[] s=new String[]{id,floor,type,price};
        HashSet<String[]> as = new HashSet<>();
        HashSet<String> idHash = new HashSet<>();
        String[] s;
        try {
            Connection conn = new DatabaseConnection().getConnection();
            conn.setAutoCommit(false);
            Statement state = conn.createStatement();
            int in = checkinDate;
            int out = checkOutDate;
            int idInt;
            for (in = checkinDate; in < out; in++) {
                String ins = String.valueOf(in);
                r1 = state.executeQuery("select * from room where day" + ins + "=true");
                while (r1.next()) {
                    id = r1.getString("ID");
                    floor = r1.getString("floor");
                    type = r1.getString("type");
                    price = r1.getString("price");
                    //idInt=Integer.parseInt(id);                   
                    if (idHash.add(id)) {
                        s = new String[]{id, floor, type, price};
                        as.add(s);
                    }
                }

            }

        } catch (Exception e) {
        }

        return as;
    }

    @Override
    public boolean queryRoomAvailable(int checkinDate, int checkoutDate, String roomNumber) throws Exception {
        String sql = "SELECT * FROM hotel.room where id = "+roomNumber;
        Connection conn = new DatabaseConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet rs = state.executeQuery(sql);
        while(rs.next()){
            for(int i=checkinDate;i<checkoutDate;i++){
                if(rs.getInt("day"+(i+1))==0)
                    return false;
                }
        }
        return true;
    }

    @Override
    public void queryRoomAvailableFloor(Date checkinDate, Date checkoutDate, String floor) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void queryRoomAvailable(Date checkinDate, Date checkoutDate, String floor, String roomtype) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void queryRoomAvailableByRoomNumber(String roomnumber) {
//        try (Connection conn = new DatabaseConnection().getConnection();
//                Statement state = conn.createStatement();
//                ResultSet rs = state.executeQuery("select * from room")) {
//            while (rs.next()) {
//                System.out.println(rs.getString("ID"));
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAOImplement.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void queryTest() throws SQLException, ClassNotFoundException {
        try (Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery("select * from room")) {
            while (rs.next()) {
                System.out.println(rs.getString("ID"));
            }
        }
    }
    
    public ResultSet queryGetOrder() {
        ResultSet rs = null;
        try (Connection conn = new DatabaseConnection().getConnection();
            Statement state = conn.createStatement();
            ) { 
            rs = state.executeQuery("select * from order");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAOImplement.class.getName()).log(Level.SEVERE, null, ex);
        }return rs; 
    }
    
    @Override
    public boolean queryRoomAvailableToRenew(String customerName) throws ClassNotFoundException, SQLException {
        boolean flag=true;
        try(Connection conn = new DatabaseConnection().getConnection();
                Statement state = conn.createStatement();
                ResultSet rs = state.executeQuery("select roomstatus from customer where username="+customerName)){
                ResultSet rs2;
            while(rs.next()) {
                rs2=state.executeQuery("select day2 from room where id="+rs.getString("id"));
                if(rs2.getBoolean("day2")) {
                    flag=true;
                }
                else
                    flag=false;
            }
        }
        if(flag)
            return true;
        else
            return false;
    }

}
