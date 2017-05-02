package lab3;

/**
 * An Exception used to output a specified error message whenever an invalid number of
 * Production Goals is entered.
 * @author Joshua DeNoble and Paul Bayruns
 */
public class InvalidProductionGoalsException extends Exception 
{
	    private String message = null;
	 
	    public InvalidProductionGoalsException() {
	        super();
	    }
	 
	    public InvalidProductionGoalsException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public InvalidProductionGoalsException(Throwable cause) {
	        super(cause);
	    }
	 
	    @Override
	    public String toString() {
	        return message;
	    }
	 
	    @Override
	    public String getMessage() {
	        return message;
	    }
}
