package edu.lb.spring_networktechnologies.infrastructure.dtos.auth;

import edu.lb.spring_networktechnologies.commonTypes.UserRole;

public class RegisterResponseDto {
    private Long uid;
    private String username;
    private UserRole role;

    public RegisterResponseDto(Long uid, String username, UserRole role) {
        this.uid = uid;
        this.username = username;
        this.role = role;
    }

    public RegisterResponseDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
