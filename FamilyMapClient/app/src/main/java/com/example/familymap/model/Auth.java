package com.example.familymap.model;

/**model class for objects in auth table */
public class Auth{

    /** userName associated with authtoken */
    private String userName;
    /**actual authentification string */
    private String authToken;

    public Auth(String userName, String authToken) {
        setUserName(userName);
        setAuthToken(authToken);
    }
    
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authToken == null) ? 0 : authToken.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Auth other = (Auth) obj;
        if (authToken == null) {
            if (other.authToken != null)
                return false;
        } else if (!authToken.equals(other.authToken))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Auth [authToken=" + authToken + ", userName=" + userName + "]";
    }


}