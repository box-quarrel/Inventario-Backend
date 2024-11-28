package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.request.UserRequestDTO;
import com.hameed.inventario.model.dto.basic.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // Create a new user
    public void addUser(UserRequestDTO userRequestDTO);

    // Update an existing user
    public void updateUser(String username, UserRequestDTO userRequestDTO);

    // Remove a user
    public void removeUser(String username);

    // Get all users with pagination
    public Page<UserDTO> getAllUsers(Pageable pageable);

    // Get a user by username
    public UserDTO getUserByUsername(String username);

    // add a new role to the user
    public void authorizeUser(String role);
}

