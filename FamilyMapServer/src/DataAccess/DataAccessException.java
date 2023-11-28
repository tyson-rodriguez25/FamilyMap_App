package DataAccess;


public class DataAccessException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DataAccessException(String message)
    {
        super(message);
    }

    DataAccessException()
    {
        super();
    }
}
