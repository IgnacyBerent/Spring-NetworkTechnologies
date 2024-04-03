package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.DeleteUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserResponseDto;
import edu.lb.spring_networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the currently logged-in user
     * @param principal - Principal object containing information about the currently logged in user
     * @return GetUserDto object containing information about the user
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetUserDto> getMe(Principal principal) {
        String username = principal.getName();
        GetUserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Get all users from the database
     * @return List of GetUserDto objects containing information about the users
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<GetUserDto> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Get a single user by its id
     * @param id - id of the user
     * @return GetUserDto object containing information about the user
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GetUserDto getUser(@PathVariable Long id) {
        return userService.getOne(id);
    }

    /**
     * Delete a user by its id
     * @param id - id of the user
     * @return DeleteUserDto object containing information about the deleted user
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteUserDto> delete(@PathVariable Long id) {
        DeleteUserDto dto =  userService.delete(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Update a user by its id
     * @param id - id of the user
     * @param dto - UpdateUserDto object containing information about the user
     * @return UpdateUserResponseDto object containing information about the updated user
     */
    @PatchMapping("/update/{id}")
    public ResponseEntity<UpdateUserResponseDto> update(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        UpdateUserResponseDto responseDto = userService.update(id, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}