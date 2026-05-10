package com.samir.usermanagement.controller;

import org.springframework.web.bind.annotation.*;
import com.samir.usermanagement.service.userService;
import com.samir.usermanagement.entity.user;
import com.samir.usermanagement.dto.UserDTO;
import com.samir.usermanagement.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;


@RestController
public class userController {

    @Autowired
    private userService userService;

    // CREATE USER
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<user>> saveUser(
            @Valid @RequestBody UserDTO userDTO) {

        user savedUser = userService.saveUser(userDTO);

        ApiResponse<user> response =
                new ApiResponse<>(
                        "User created successfully",
                        savedUser
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    // GET ALL USERS WITH PAGINATION + SORTING
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getAllUsers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction

    ) {

        var users = userService.getAllUsers(
                page,
                size,
                sortBy,
                direction
        );

        ApiResponse<Object> response =
                new ApiResponse<>(
                        "Users fetched successfully",
                        users
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }


    @GetMapping("/users/start-with")
public ResponseEntity<ApiResponse<List<user>>> 
getUsersByStartingLetter(
        @RequestParam String name
) {

    List<user> users =
            userService.getUsersByStartingLetter(name);

    ApiResponse<List<user>> response =
            new ApiResponse<>(
                    "Users fetched successfully",
                    users
            );

    return new ResponseEntity<>(
            response,
            HttpStatus.OK
    );
}

    // GET USER BY ID
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<user>> getUserById(
            @PathVariable int id) {

        user foundUser = userService.getUserById(id);

        ApiResponse<user> response =
                new ApiResponse<>(
                        "User fetched successfully",
                        foundUser
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    // UPDATE USER
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<user>> updateUser(
            @PathVariable int id,
            @Valid @RequestBody UserDTO updatedUser) {

        user updated = userService.updateUser(id, updatedUser);

        ApiResponse<user> response =
                new ApiResponse<>(
                        "User updated successfully",
                        updated
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    // DELETE USER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable int id) {

        userService.deleteUser(id);

        ApiResponse<String> response =
                new ApiResponse<>(
                        "User deleted successfully",
                        "Deleted user id: " + id
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

   @GetMapping("/users/search")
public ResponseEntity<ApiResponse<List<user>>>searchUsersByName(@RequestParam String name)
 {
    List<user> users =
            userService.searchUsersByName(name);

    ApiResponse<List<user>> response =
            new ApiResponse<>(
                    "Users fetched successfully",
                    users
            );

    return new ResponseEntity<>(
            response,
            HttpStatus.OK
    );
}

@GetMapping("/users/searchByEmail")
public ResponseEntity<ApiResponse<user>> searchUserByEmail(@RequestParam String email) {

    user foundUser =
            userService.searchUserByEmail(email);

    ApiResponse<user> response =
            new ApiResponse<>(
                    "User fetched successfully",
                    foundUser
            );

    return new ResponseEntity<>(
            response,
            HttpStatus.OK
    );


}
}