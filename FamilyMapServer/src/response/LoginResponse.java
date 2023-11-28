package response;

/**gson model class for login responses */
public class LoginResponse extends InheritResponse{
    
    /**authorization token for login */
    private String authToken;
    /**username logged in */
    private String userName;
    /**unique identifier of the person object that represents the user */
    private String personID;

    public LoginResponse(String authToken, String userName, String personID) {
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "LoginResponse [authToken=" + authToken + ", personID=" + personID + ", userName=" + userName + "]";
    }

    
}