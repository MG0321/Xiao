package hotelpossystem;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Shuang
 */
public class Service {
    private String date;
    private double price;
    private String name;
    
    public Service(int i) {
        switch (i) {
            case (0) : this.name = "morning call";
                       this.price = 0;
                       break;
            case (1) : this.name = "laundry";
                       this.price = 3;
                       break;
            case (2) : this.name = "breakfast";
                       this.price = 10;
                       break;
            case (3) : this.name = "lunch";
                       this.price = 25;
                       break;
            case (4) : this.name = "supper";
                       this.price = 25; 
                       break;
    }
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name+" "+this.price;
    }
    
    
    
}
