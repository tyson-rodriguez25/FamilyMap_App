package com.example.familymap.request;

/**gson model class for loginrequest */
public class LoginRequest{
    
    /**username of user logging in */
    private String userName;
    /**password of user logging in */
    private String password;

    public LoginRequest(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}