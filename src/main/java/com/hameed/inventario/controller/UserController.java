package com.hameed.inventario.controller;


import com.hameed.inventario.model.dto.basic.UserDTO;
import com.hameed.inventario.model.dto.request.PasswordChangeRequest;
import com.hameed.inventario.model.dto.request.RoleRequest;
import com.hameed.inventario.model.dto.response.PaginatedResponseDTO;
import com.hameed.inventario.model.dto.response.ResponseDTO;
import com.hameed.inventario.service.UserService;
import com.hameed.inventario.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("v1/users")
public class UserController {

    // properties
    @Value("${pageSize}")
    private int pageSize;

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Only Admin Operations

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<UserDTO>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size) {
        int finalPageSize = (size == null) ? pageSize : size;
        Page<UserDTO> userDTOPage = userService.getAllUsers(PageRequest.of(page, finalPageSize));
        return ResponseEntity.ok(new PaginatedResponseDTO<>(200, "Users Retrieved Successfully", userDTOPage)); // 200 OK
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public  ResponseEntity<ResponseDTO<UserDTO>> addUser(@Valid @RequestBody UserDTO UserDTO) {
        UserDTO resultUserDTO = userService.addUser(UserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "User Created Successfully", resultUserDTO));  // 201 CREATED
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/authorize/{username}")
    public ResponseEntity<ResponseDTO<UserDTO>> authorizeUser(@PathVariable String username,
                                                              @RequestBody RoleRequest roleRequest) {
        UserDTO resultUserDTO = userService.authorizeUser(username, roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Authorization Granted Successfully", resultUserDTO));  // 201 CREATED
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseDTO<UserDTO>> deleteUser(@PathVariable String username) {
        userService.removeUser(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/changePassword/{username}")
    public ResponseEntity<ResponseDTO<?>> changePasswordForUser(@PathVariable String username,
            @RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(username, passwordChangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Password updated Successfully"));  // 201 CREATED
    }

    // User Operations
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/changePassword")
    public ResponseEntity<ResponseDTO<?>> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        String username = SecurityUtil.getUsername();
        userService.changePassword(username, passwordChangeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(201, "Password updated Successfully"));  // 201 CREATED
    }
}
