package com.hameed.inventario.service.impl;

import com.hameed.inventario.enums.RoleType;
import com.hameed.inventario.model.dto.basic.UserDTO;
import com.hameed.inventario.model.dto.request.RoleRequest;
import com.hameed.inventario.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(JdbcUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDTO> getAllUsers(PageRequest pageRequest) {
        assert userDetailsManager.getJdbcTemplate() != null;
        String queryAllUsers = "select username,password,enabled from users";
        String queryAllAuthorities = "select username,authority from authorities";
        List<UserDTO> userList = userDetailsManager.getJdbcTemplate().query(queryAllUsers, this::mapToUserDetails)
                .stream().map(this::mapUserDetailsToUserDTO).toList();
        userDetailsManager.getJdbcTemplate().query(queryAllAuthorities, (rs, rowNum) -> {
            String username = rs.getString(1);
            String authority = rs.getString(2);
            userList.stream().filter(user -> user.getUsername().equals(username))
                    .forEach(user -> user.getRoles().add(authority));
            return null;
        });
        return new PageImpl<>(userList, pageRequest, userList.size());
    }

    @Override
    public void createUser(UserDTO userDTO) {
        UserDetails userDetails = mapUserDTOToUserDetails(userDTO);
        userDetailsManager.createUser(userDetails);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        UserDetails userDetails = mapUserDTOToUserDetails(userDTO);
        userDetailsManager.updateUser(userDetails);

    }

    private UserDetails mapUserDTOToUserDetails(UserDTO userDTO) {
        String[] roles = userDTO.getRoles().stream().map(
                role -> {
                    RoleType roleType = RoleType.fromString(role);
                    return roleType.toString().toUpperCase();
                }).toArray(String[]::new);
        return User.withUsername(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
    }

    private UserDTO mapUserDetailsToUserDTO(UserDetails userDetails) {
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority().substring(5))
                .map(RoleType::fromString)
                .map(RoleType::toString)
                .collect(Collectors.toSet());
        return new UserDTO(userDetails.getUsername(), null, roles);
    }

    private UserDetails mapToUserDetails(ResultSet rs, int rowNum) throws SQLException {
        String userName = rs.getString(1);
        String password = rs.getString(2);
        boolean enabled = rs.getBoolean(3);
        boolean accLocked = false;
        boolean accExpired = false;
        boolean credsExpired = false;
        if (rs.getMetaData().getColumnCount() > 3) {
            accLocked = rs.getBoolean(4);
            accExpired = rs.getBoolean(5);
            credsExpired = rs.getBoolean(6);
        }

        return new User(userName, password, enabled, !accExpired, !credsExpired, !accLocked, AuthorityUtils.NO_AUTHORITIES);
    }

    @Override
    public void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }

    @Override
    public boolean userExists(String username) {
        return userDetailsManager.userExists(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        userDetailsManager.changePassword(oldPassword, passwordEncoder.encode(newPassword));
    }

    @Override
    public void changePasswordForUser(String username, String newPassword) {
        if (!userDetailsManager.userExists(username)) {
            throw new IllegalArgumentException("User does not exist");
        }
        String changePasswordSql = "update users set password = ? where username = ?";
        assert userDetailsManager.getJdbcTemplate() != null;
        userDetailsManager.getJdbcTemplate().update(changePasswordSql, new Object[]{passwordEncoder.encode(newPassword), username});
    }

    @Override
    public void authorizeUser(String username, RoleRequest roleRequest) {
        RoleType roleType = RoleType.fromString(roleRequest.getRole());
        String formattedRole = "ROLE_" + roleType.toString().toUpperCase();
        String createAuthoritySql = "insert into authorities (username, authority) values (?,?)";
        assert userDetailsManager.getJdbcTemplate() != null;
        userDetailsManager.getJdbcTemplate().update(createAuthoritySql, new Object[]{username, formattedRole});
    }
}
