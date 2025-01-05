package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.basic.UserDTO;
import com.hameed.inventario.model.dto.request.PasswordChangeRequest;
import com.hameed.inventario.model.dto.request.RoleRequest;
import com.hameed.inventario.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    // Create a new userEntity
    public UserDTO addUser(UserDTO userDTO);

    // Update an existing userEntity
    public UserDTO updateUser(String username, UserDTO userDTO);

    // Remove a userEntity
    public void removeUser(String username);

    // Change user password
    public void changePassword(String username, PasswordChangeRequest passwordChangeRequest);

    // Get all users with pagination
    public Page<UserDTO> getAllUsers(Pageable pageable);

    // Get userEntity by username
    public UserEntity getUserByUsername(String username);

    // add a new role to the userEntity
    public UserDTO authorizeUser(String username, RoleRequest roleRequest);
}

