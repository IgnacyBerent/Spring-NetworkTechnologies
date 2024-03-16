package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.LoginForm;
import edu.lb.spring_networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
        String token = userService.loginWithEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid login or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
