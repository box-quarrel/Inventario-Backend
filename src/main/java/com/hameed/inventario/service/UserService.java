package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.create.UserCreateDTO;
import com.hameed.inventario.model.dto.update.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // Create a new user
    public void addUser(UserCreateDTO userCreateDTO);

    // Update an existing user
    public void updateUser(String username, UserCreateDTO userCreateDTO);

    // Remove a user
    public void removeUser(String username);

    // Get all users with pagination
    public Page<UserDTO> getAllUsers(Pageable pageable);

    // Get a user by username
    public UserDTO getUserByUsername(String username);

    // add a new role to the user
    public void authorizeUser(String role);
}

