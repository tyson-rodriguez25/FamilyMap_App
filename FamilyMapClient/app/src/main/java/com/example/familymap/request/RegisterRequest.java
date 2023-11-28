package com.example.familymap.request;

/**gson model class for registerrequest */
public class RegisterRequest{
    
    /**username being registered */
    private String userName;
    /**password being registered */
    private String password;
    /**email being registered */
    private String email;
    /**first name of user */
    private String firstName;
    /**last name of user */
    private String lastName;
    /**gender of user */
    private String gender;

    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}