package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.DeleteUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public @ResponseBody List<GetUserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/get/{id}")
    public GetUserDto getUser(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteUserDto> delete(@PathVariable Long id) {
        DeleteUserDto dto =  userService.delete(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}