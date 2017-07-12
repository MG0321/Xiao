package hotelpossystem;

import UI.MainFrame;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author XiaoSong
 */
public abstract class Payment {

    public static double getTAX_RATE() {
        return TAX_RATE;
    }

    /**
     * @param aTAX_RATE the TAX_RATE to set
     */
    public static void setTAX_RATE(double aTAX_RATE) {
        TAX_RATE = aTAX_RATE;
    }
    private Date date;
    private Date time;
    private double total;
    private Customer buyer;
    private int transactionNumber;
    private double moneyPaid;
    private Order order;
    private static double TAX_RATE = 0.10;
    private double tax;
    private double balance;

    protected abstract boolean isPaymentValide();

    protected abstract void confirmPayment();

    protected abstract void cancelPayment();

    protected abstract void displayPayment();

    protected abstract void printReceipt();

    protected abstract void storePayment();

    protected abstract void pay(JLabel label);

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the tax
     */
    public double getTax() {
        return order.getTotalFee()*TAX_RATE;
       
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return order.getTotalFee()*(1+TAX_RATE);
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the buyer
     */
    public Customer getBuyer() {
        return buyer;
    }

    /**
     * @param buyer the buyer to set
     */
    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    /**
     * @return the transactionNumber
     */
    public int getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    /**
     * @return the moneyPaid
     */
    public double getMoneyPaid() {
        return moneyPaid;
    }

    /**
     * @param moneyPaid the moneyPaid to set
     */
    public void setMoneyPaid(double moneyPaid) {
        this.moneyPaid = moneyPaid;
    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

}
