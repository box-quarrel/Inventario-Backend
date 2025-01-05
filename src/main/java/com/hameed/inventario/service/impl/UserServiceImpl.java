package com.hameed.inventario.service.impl;

import com.hameed.inventario.enums.RoleType;
import com.hameed.inventario.model.dto.basic.UserDTO;
import com.hameed.inventario.model.dto.request.PasswordChangeRequest;
import com.hameed.inventario.model.dto.request.RoleRequest;
import com.hameed.inventario.model.entity.Authority;
import com.hameed.inventario.model.entity.UserEntity;
import com.hameed.inventario.repository.UserRepository;
import com.hameed.inventario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        // Build the user entity from dto
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<Authority> authorities = userDTO.getRoles().stream().map(
                role -> {
                    RoleType roleType = RoleType.fromString(role);
                    String formattedRole = "ROLE_" + roleType.toString().toUpperCase();
                    return Authority.builder()
                            .authority(formattedRole)
                            .build();
                }
        ).collect(Collectors.toSet());
        authorities.forEach(userEntity::addAuthority);
        if (userEntity.getAuthorities().isEmpty()) {
            // add user role by default if now roles provided
            userEntity.getAuthorities().add(Authority.builder().authority("ROLE_USER").build());
        }

        UserEntity resultUser = userRepository.save(userEntity);
        UserDTO resultUserDTO = UserDTO.builder()
                .username(resultUser.getUsername())
                .roles(resultUser.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toSet()))
                .creationDate(resultUser.getCreationDate())
                .build();
        // save
        return resultUserDTO;
    }

    @Override
    public UserDTO updateUser(String username, UserDTO userDTO) {
        // TODO: Implement to update user profile when we create it later
        return null;
    }

    @Override
    public void changePassword(String username, PasswordChangeRequest passwordChangeRequest) {
        UserEntity user = getUserByUsername(username);
        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void removeUser(String username) {
        UserEntity user = getUserByUsername(username);
        userRepository.delete(user);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        // map all users to user dto
        return usersPage.map(
                userEntity -> UserDTO.builder()
                        .username(userEntity.getUsername())
                        .roles(userEntity.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toSet()))
                        .creationDate(userEntity.getCreationDate())
                        .build()
        );
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public UserDTO authorizeUser(String username, RoleRequest roleRequest) {

        RoleType roleType = RoleType.fromString(roleRequest.getRole());
        String formattedRole = "ROLE_" + roleType.toString().toUpperCase();
        UserEntity user = this.getUserByUsername(username);
        // check whether authority already exists or not
        if (user.getAuthorities().stream().map(Authority::getAuthority).anyMatch(formattedRole::equals))
            throw new IllegalArgumentException("User Already has the Authority: " + roleRequest.getRole());

        Authority authority = Authority.builder()
            .authority(formattedRole)
            .build();
        user.addAuthority(authority);
        UserEntity resultUser = userRepository.save(user);
        UserDTO resultUserDTO = UserDTO.builder()
                .username(resultUser.getUsername())
                .roles(resultUser.getAuthorities().stream().map(Authority::getAuthority).collect(Collectors.toSet()))
                .creationDate(resultUser.getCreationDate())
                .build();
        // save
        return resultUserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserByUsername(username);

        List<GrantedAuthority> authorities = userEntity.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList());

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(), true, true, true, authorities);
    }
}
