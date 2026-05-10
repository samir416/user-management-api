package com.samir.usermanagement.service;

import org.springframework.stereotype.Service;
import com.samir.usermanagement.repository.userRepository;
import com.samir.usermanagement.entity.user;
import com.samir.usermanagement.dto.UserDTO;
import com.samir.usermanagement.exception.UserNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
public class userService {

    private static final Logger logger =
            LoggerFactory.getLogger(userService.class);

    @Autowired
    private userRepository userRepository;

    // SAVE USER
    public user saveUser(UserDTO userDTO) {

        logger.info("Creating a new user");

        user user = new user();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        user savedUser = userRepository.save(user);

        logger.info(
                "User saved successfully with id: {}",
                savedUser.getId()
        );

        return savedUser;
    }

    public List<user> searchUsersByName(String name) {

        logger.info("Searching users with name containing: {}", name);

        return userRepository.findByNameContainingIgnoreCase(name);
    }



    // GET ALL USERS WITH PAGINATION + SORTING
    public Page<user> getAllUsers(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        logger.info("Fetching users with pagination");

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return userRepository.findAll(pageable);
    }

    public List<user> getUsersByStartingLetter(String name) {

    logger.info(
            "Fetching users whose names start with: {}",
            name
    );

    return userRepository.findUsersByNameStartingWith(name);
}

    // GET USER BY ID
    public user getUserById(int id) {

        logger.info("Fetching user with id: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));
    }

    // DELETE USER
    public void deleteUser(int id) {

        logger.info("Deleting user with id: {}", id);

        user existingUser =
                userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));

        userRepository.delete(existingUser);

        logger.info("User deleted successfully");
    }

    // UPDATE USER
    public user updateUser(
            int id,
            UserDTO updatedUserDTO) {

        logger.info("Updating user with id: {}", id);

        user existingUser =
                userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        ));

        existingUser.setName(
                updatedUserDTO.getName()
        );

        existingUser.setEmail(
                updatedUserDTO.getEmail()
        );

        user updatedUser =
                userRepository.save(existingUser);

        logger.info("User updated successfully");

        return updatedUser;
    }

    public user searchUserByEmail(String email) {

        logger.info("Searching user with email: {}", email);

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        ));
    }
}