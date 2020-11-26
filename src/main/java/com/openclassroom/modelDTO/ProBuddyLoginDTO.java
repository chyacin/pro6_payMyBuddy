package com.openclassroom.modelDTO;

import java.sql.Timestamp;

public class ProBuddyLoginDTO {
    private String email;
    private Timestamp date;
    private Boolean success;


    public ProBuddyLoginDTO(String email, Timestamp date, Boolean success) {
        this.email = email;
        this.date = date;
        this.success = success;
    }

    public ProBuddyLoginDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
