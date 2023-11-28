package response;

/**gson model class for success responses */
public class SuccessResponse extends InheritResponse{
    /**success message returned */
    private String message;

    public SuccessResponse(String message) {
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessResponse [message=" + message + "]";
    }
    
}