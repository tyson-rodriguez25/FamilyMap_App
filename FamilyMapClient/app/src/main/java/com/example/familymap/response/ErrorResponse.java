package com.example.familymap.response;

/**gson model class for error responses */
public class ErrorResponse extends InheritResponse{
    /**error message returned */
    private String message;

    public ErrorResponse(String message) {
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
        return "ErrorResponse [message=" + message + "]";
    }

    
}