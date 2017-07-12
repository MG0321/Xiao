package hotelpossystem;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is to control the automatically systems out
 * @author lingyanjiang
 */
public class Time implements Subject{

    private boolean timeOut = false;
    private ArrayList<Observer> list = new ArrayList<>();
    private int time;
    
    public Time(int time,Observer o){
        this.time = time ;
        list.add(o);
        ThreadTask t1 = new ThreadTask();
        t1.start();
    }
    
    class ThreadTask extends Thread{  
        @Override
        public void run(){ 
            try {
                sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);
            }
            setTimeOut(true);
        }  
    }  
    
    @Override
    public void attach(Observer observer) {
        list.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer o:list)
            o.update();
    }
    


    /**
     * @return the timeOut
     */
    public boolean isTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(boolean timeOut) {
        this.timeOut=timeOut;
        
        if(timeOut==true){
            notifyObservers();
        }
        
        //revert
        this.timeOut=false;
    }
    
}
