package response;

/**gson model class for register responses */
public class RegisterResponse extends InheritResponse{
    /**authentification token assigned to the user */
    private String authToken;
    /**username registered */
    private String userName;
    /**unique identifier for the person object representing the user  */
    private String personID;

    public RegisterResponse(String authToken, String userName, String personID) {
        setAuthToken(authToken);
        setUserName(userName);
        setPersonID(personID);
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTokenID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "RegisterResponse [authToken=" + authToken + ", personID=" + personID + ", userName=" + userName + "]";
    }

    
}