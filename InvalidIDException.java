package lab3;

/**
 * An Exception used to output a specified error message whenever an invalid
 * Employee ID is entered. 
 * @author Joshua DeNoble and Paul Bayruns
 */
public class InvalidIDException extends Throwable
{
	    private String message = null;
	 
	    public InvalidIDException() {
	        super();
	    }
	 
	    public InvalidIDException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public InvalidIDException(Throwable cause) {
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
