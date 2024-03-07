package edu.lb.spring_networktechnologies.dtos.user;

import java.util.List;

public class UserDTO {
    private String username;
    private List<Long> loans;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getLoans() {
        return loans;
    }

    public void setLoans(List<Long> loans) {
        this.loans = loans;
    }
}


