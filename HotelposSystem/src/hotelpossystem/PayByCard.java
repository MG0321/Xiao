
package hotelpossystem;

import hotelpossystem.Customer;
import hotelpossystem.dao.DAOFactory;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author XiaoSong
 */
public class PayByCard extends Payment {
    
    public PayByCard(){
        System.out.println("new payment");
        this.setDate(new Date());
        this.setTime(new Date());
        try {
            this.setTransactionNumber(DAOFactory.getUserDAOInstance().queryTransMaxId()+1);
        } catch (Exception ex) {
            Logger.getLogger(PayByCard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void pay(JLabel jLbDeal) {
        try {
            double payment = Customer.getCustomerInstance().getCurrentOrder().getTotalFee();
            jLbDeal.setText(String.valueOf(payment));
        } catch (Exception e) {
        }

    }

    @Override
    protected boolean isPaymentValide() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void confirmPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void cancelPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void displayPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void printReceipt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void storePayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
