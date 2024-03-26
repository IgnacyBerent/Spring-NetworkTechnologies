package edu.lb.spring_networktechnologies.infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name="_blacklisted_tokens", schema = "library")
public class BlacklistedTokenEntity {

    @GeneratedValue
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
