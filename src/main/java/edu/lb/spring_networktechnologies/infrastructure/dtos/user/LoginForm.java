package edu.lb.spring_networktechnologies.infrastructure.dtos.user;


public class LoginForm {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String login) {
        this.email = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
