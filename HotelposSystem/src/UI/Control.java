package UI;

import hotelpossystem.Customer;
import hotelpossystem.Order;
import hotelpossystem.Room;
import hotelpossystem.Service;
import hotelpossystem.dao.DAOFactory;
import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lingyanjiang
 */
public class Control {
    public int count = 0;
    
    public void runn(JTable jTableR, int from, int to) throws Exception {
        count=0;
        DefaultTableModel tableModel = (DefaultTableModel) jTableR.getModel();
        int in =from+1;
        int out =to+1;
       
        HashSet<String[]> as = new HashSet<>();
        as=DAOFactory.getUserDAOInstance().queryRoomAvailable(in, out);          
        for(String[] a: as) {
            tableModel.addRow(a);
            count++;
        }
    }
    
    public double displayRoomOrder(JTable jTableR, JTable jTableC) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableR.getModel();
        DefaultTableModel tableModel2 = (DefaultTableModel) jTableR.getModel();
        String id=tableModel.getValueAt(jTableR.getSelectedRow(),0).toString();
        String floor=tableModel.getValueAt(jTableR.getSelectedRow(),1).toString();
        String type=tableModel.getValueAt(jTableR.getSelectedRow(),2).toString();
        String price=tableModel.getValueAt(jTableR.getSelectedRow(),3).toString();;
        tableModel2.setValueAt(id, 0, 0); 
        tableModel2.setValueAt(floor, 0, 1);
        tableModel2.setValueAt(type, 0, 2);
        tableModel2.setValueAt(price, 0, 3);
        double roomPrice=Integer.valueOf(price);
        return roomPrice;
    }
    
    public void activeSearch(JComboBox jCBF,JComboBox jCBT,JTable jTableR,int from, int to) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableR.getModel();
        tableModel.setRowCount(0);
        try {
            runn(jTableR, from, to);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(jCBF.getSelectedIndex()==1) {
            if(jCBT.getSelectedIndex()==0) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==1) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")|| !tableModel.getValueAt(index,2).equals("normal1")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==2) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")|| !tableModel.getValueAt(index,2).equals("normal2")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==3) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")|| !tableModel.getValueAt(index,2).equals("big1")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==4) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")|| !tableModel.getValueAt(index,2).equals("big2")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
        }
        if(jCBF.getSelectedIndex()==2) {
            if(jCBT.getSelectedIndex()==0) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level2")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==1) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level2")||!tableModel.getValueAt(index,2).equals("normal1")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==2) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level1")||!tableModel.getValueAt(index,2).equals("normal2")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==3) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level2")||!tableModel.getValueAt(index,2).equals("big1")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }
            if(jCBT.getSelectedIndex()==4) {
                for(int index=0; index < count;index++ ) {
                    if(!tableModel.getValueAt(index,1).equals("level2")|| !tableModel.getValueAt(index,2).equals("big2")) {
                        tableModel.removeRow(index);
                        index--; count--;
                    }
                }
            }

        }
    }
    
    public void intiateDate(JComboBox jCbFrom,JComboBox jCbTo,JLabel jLbWarning,int from, int to) {                                  
        Date dt = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String today = "";
        today = sf.format(dt);
        String[] next = new String[6];
        Calendar cal = Calendar.getInstance();
        jCbFrom.addItem(today);
        
        //jCbTo.addItem(today);
        for (int i = 0; i < 6; i++) {
            try {
                cal.setTime(sf.parse(today));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            cal.add(Calendar.DAY_OF_YEAR, +(i+1));
            next[i] = sf.format(cal.getTime());
            jCbFrom.addItem(next[i]);
            jCbTo.addItem(next[i]);
        }
        
       
    }
    
    
    public boolean ifTimeRight(JComboBox jCbFrom,JComboBox jCbTo,JLabel jLbWarning,int from, int to) {                                  
     if (jCbTo.getSelectedIndex() < jCbFrom.getSelectedIndex()) {
                jLbWarning.setText("The check out time should after the check in time");
                return false;
            } 
            else {
                from = jCbFrom.getSelectedIndex();           
                to = jCbTo.getSelectedIndex();            
                return true;
            }
    }
     public void renewRoom(JTable jTableR, String customerName) throws Exception {
       String[] s=new String[4];
       s=DAOFactory.getUserDAOInstance().queryRoomAvailableByRoomNumber(customerName);
       DefaultTableModel tableModel = (DefaultTableModel) jTableR.getModel();
       tableModel.addRow(s);
    }
    
    public void displayModel(JTable jT, ArrayList<Service> customerService, Order order) {
            DefaultTableModel model = (DefaultTableModel) jT.getModel();
//            model.setRowCount(0);
            Object[] object = new Object[3];
            //service name; service time; service price; total;
            for (Service s : customerService) {
                object[0] = s.getName();
                object[1] = s.getDate();
                object[2] = s.getPrice();
                model.addRow(object);
            }
            object[0] = "Total";
            object[1] = null;
            object[2] = order.getTotalFee();
            
            model.addRow(object);
    }
    
    public void confirmPay(JLabel jLbDeal, JTextField jTfQuantity, JLabel jLbChange) {
        double change = Double.parseDouble(jTfQuantity.getText()) - Double.parseDouble(jLbDeal.getText());
        jLbChange.setText(String.valueOf(change));
    }

    public void payByCard(JLabel jLbDeal, String name) throws Exception {

        double payment = Customer.getCustomerInstance().getCurrentOrder().getTotalFee();
        jLbDeal.setText(String.valueOf(payment));
    }

    public void payByCash(JLabel jLbDeal, String name) throws Exception {
        try {
            double payment = Customer.getCustomerInstance().getCurrentOrder().getTotalFee();
            jLbDeal.setText(String.valueOf(payment));
        } catch (Exception e) {
        }

    }
    
    public void displayRoomOrder(JTable jTableOrder, Room room, String from){//
        DefaultTableModel model = (DefaultTableModel) jTableOrder.getModel();
        String s1="room: "+room.getId();
        String s2=from;
        String s3=String.valueOf(room.getPrice())+"*"+(MainFrame.to-MainFrame.from);
        String[] s=new String[]{s1,s2,s3};              
        model.addRow(s);
    }
    
    public void getSelectedRoom(JTable jTableR, Room room){
        DefaultTableModel tableModel = (DefaultTableModel) jTableR.getModel();
        int row=jTableR.getSelectedRow();         
        room.setId(tableModel.getValueAt(row, 0).toString()); 
        room.setFloor(tableModel.getValueAt(row, 1).toString()); 
        room.setType(tableModel.getValueAt(row, 2).toString());
        room.setPrice(Double.valueOf(tableModel.getValueAt(row, 3).toString()));  
    }
    
    public void getRenewRoom(JTable jTableRenew, Room room){
        DefaultTableModel tableModel = (DefaultTableModel) jTableRenew.getModel();
        int row=jTableRenew.getSelectedRow();
        room.setId(tableModel.getValueAt(row, 1).toString());  
        room.setPrice(Double.valueOf(tableModel.getValueAt(row, 2).toString()));
    }
    
    public void displayRenewRoom(JTable jTableOrder, Room room){
        Date dt = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String today = "";
        today = sf.format(dt);
        DefaultTableModel model = (DefaultTableModel) jTableOrder.getModel();
        String s1="room: "+room.getId();
        String s3=String.valueOf(room.getPrice());
        String s2=today.toString();

        String[] s=new String[]{s1,s2,s3};              
        model.addRow(s);
    }
    
        public void displayRoomModel(JTable jT, ArrayList<Room> rooms, Order order) {
            DefaultTableModel model = (DefaultTableModel) jT.getModel();
            model.setRowCount(0);
            Object[] object = new Object[3];
            //service name; service time; service price; total;
            for (Room r : rooms) {
                object[0] = r.getId();
                object[1] = r.getType();
                object[2] = r.getPrice();
            }
            model.addRow(object);
            object[0] = null;
            object[1] = null;
            object[2] = order.getTotalFee();
    }
    
        
    
    
    public void showDate(JComboBox jCb) {
        Date dt = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sf.format(dt);
        String[] next = new String[6];
        Calendar cal = Calendar.getInstance();
        jCb.addItem(today);

        for (int i = 0; i < 6; i++) {
          
            try {
                cal.setTime(sf.parse(today));
                cal.add(Calendar.DAY_OF_YEAR, +(i+1));
                next[i] = sf.format(cal.getTime());
                jCb.addItem(next[i]);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    

    //create each service
    private Service setService(JComboBox jCb, int i, ArrayList<Service> customerService) {
        Service s = new Service(i);
        String date = jCb.getSelectedItem().toString();
        s.setDate(date);
        return s;
    }
    
    //get all services for a given type
    public ArrayList<Service> getService(int i, ArrayList<Service> customerService, JPanel jPanel1, JPanel jPanel2, JComboBox jCb11, JComboBox jCb6, JComboBox jCb10) {
        customerService.add(setService(jCb11, i, customerService));
        if (jPanel1.isVisible()) {
            customerService.add(setService(jCb6, i, customerService));
            if (jPanel2.isVisible()){
                customerService.add(setService(jCb10, i, customerService));
        }          
    }       
        return customerService; 
    }
    
    
    public ArrayList serviceAdd(JComboBox jCb, ArrayList<Service> customerService, JPanel jPanel1, JPanel jPanel2, JComboBox jCb11, JComboBox jCb6, JComboBox jCb10) {
        if (jCb.getSelectedIndex() == 0) 
            customerService = getService(2, customerService, jPanel1, jPanel2, jCb11, jCb6, jCb10);
        else if (jCb.getSelectedIndex() == 1)
            customerService = getService(3, customerService, jPanel1, jPanel2, jCb11, jCb6, jCb10);
        else if (jCb.getSelectedIndex() == 2)
            customerService = getService(4, customerService, jPanel1, jPanel2, jCb11, jCb6, jCb10);
        else
            customerService = getService(1,  customerService, jPanel1, jPanel2, jCb11, jCb6, jCb10);  

        return customerService;
    }  
}

   
