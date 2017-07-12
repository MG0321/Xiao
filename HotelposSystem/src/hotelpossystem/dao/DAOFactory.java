package hotelpossystem.dao;

/**
 * @author lingyanjiang
 */
public class DAOFactory {

    private static UserDAO us;
    
    /**
     * @return 
     */
    public static UserDAO getUserDAOInstance() {
        if(us==null){
            return new UserDAOImplement();
        }
        else
            return us;
    }
}
