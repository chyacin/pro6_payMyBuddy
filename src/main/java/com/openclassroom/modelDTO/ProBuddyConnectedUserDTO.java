package com.openclassroom.modelDTO;

public class ProBuddyConnectedUserDTO {

    private String connectionEmail;

    public ProBuddyConnectedUserDTO() {
    }

    public ProBuddyConnectedUserDTO(String connectionEmail) {
        this.connectionEmail = connectionEmail;
    }

    public String getConnectionEmail() {
        return connectionEmail;
    }

    public void setConnectionEmail(String connectionEmail) {
        this.connectionEmail = connectionEmail;
    }
}
