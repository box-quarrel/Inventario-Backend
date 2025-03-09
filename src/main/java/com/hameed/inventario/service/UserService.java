package com.hameed.inventario.service;

import com.hameed.inventario.model.dto.basic.UserDTO;
import com.hameed.inventario.model.dto.request.RoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface UserService {
    Page<UserDTO> getAllUsers(PageRequest pageRequest);
    void createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
    void deleteUser(String username);
    boolean userExists(String username);
    void changePassword(String oldPassword, String newPassword);
    void changePasswordForUser(String username, String newPassword);
    void authorizeUser(String username, RoleRequest roleRequest);
}

