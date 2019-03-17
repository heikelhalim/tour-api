package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.request;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Email;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Password;

/**
 *
 * @author JapanTravelInsider
 */
public class NewAccountRequest {
    
    @Email(name = "email", maxSize = 256, required = true)
    private String email;
    
    @Password(name = "password", minSize = 1, maxSize = 256, required = true)
    private String password;
    
    private String fullName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
}
