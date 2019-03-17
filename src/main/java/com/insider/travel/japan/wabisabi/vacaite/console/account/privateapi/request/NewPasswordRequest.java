package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.request;

import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Email;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.NewPassword;

/**
 *
 * @author JapanTravelInsider
 */
public class NewPasswordRequest {
    
    @Email(name = "email", maxSize = 256, required = true)
    private String email;

    @NewPassword(name = "newPassword", minSize = 1, maxSize = 256, required = true)
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
