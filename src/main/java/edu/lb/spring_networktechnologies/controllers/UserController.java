package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UserCreateDTO;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UserDTO;
import edu.lb.spring_networktechnologies.infrastructure.entities.User;
import edu.lb.spring_networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED) //code 201
    public @ResponseBody UserDTO addUser(@RequestBody UserCreateDTO userCreateDTO) {
        System.out.println("addUser method called with UserCreateDTO: " + userCreateDTO);
        User user = userService.toUser(userCreateDTO);
        User savedUser = userService.saveUser(user);
        System.out.println("User saved: " + savedUser);
        return userService.toUserDTO(savedUser);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}