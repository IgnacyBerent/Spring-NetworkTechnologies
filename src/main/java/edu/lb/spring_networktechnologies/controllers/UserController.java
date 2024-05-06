package edu.lb.spring_networktechnologies.controllers;

import edu.lb.spring_networktechnologies.infrastructure.dtos.user.GetUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserDto;
import edu.lb.spring_networktechnologies.infrastructure.dtos.user.UpdateUserResponseDto;
import edu.lb.spring_networktechnologies.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the currently logged-in user
     *
     * @param principal - Principal object containing information about the currently logged-in user
     * @return GetUserDto object containing information about the user
     * @throws EntityNotFoundException - if the user is not found
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "401", description = "User not found", content = @Content),
                    @ApiResponse(responseCode = "200", description = "User found"),
            }
    )
    public ResponseEntity<GetUserDto> getMe(Principal principal) {
        String username = principal.getName();
        GetUserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Get all users from the database
     *
     * @return List of GetUserDto objects containing information about the users
     */
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponse(responseCode = "200", description = "Users found")
    public @ResponseBody List<GetUserDto> getAllUsers() {
        return userService.getAll();
    }

    /**
     * Get a single user by its id
     *
     * @param id - id of the user
     * @return GetUserDto object containing information about the user
     * @throws EntityNotFoundException - if the user is not found
     */
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User found"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            }
    )
    public GetUserDto getUser(@PathVariable Long id) {
        return userService.getOne(id);
    }

    /**
     * Delete a user by its id
     *
     * @param id - id of the user
     * @return DeleteUserDto object containing information about the deleted user
     * @throws EntityNotFoundException - if the user is not found
     */
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User deleted", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update a user by its id
     *
     * @param id  - id of the user
     * @param dto - UpdateUserDto object containing information about the user
     * @return UpdateUserResponseDto object containing information about the updated user
     * @throws EntityNotFoundException - if the user is not found
     */
    @PatchMapping("/update/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User updated"),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            }
    )
    public ResponseEntity<UpdateUserResponseDto> update(@PathVariable Long id, @RequestBody UpdateUserDto dto) {
        UpdateUserResponseDto responseDto = userService.update(id, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}