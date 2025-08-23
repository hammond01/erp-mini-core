package com.erpmini.erp_core.modules.auth.service;

import com.erpmini.erp_core.modules.auth.dto.UserDto;
import com.erpmini.erp_core.modules.auth.entity.*;
import com.erpmini.erp_core.modules.auth.mapper.UserMapper;
import com.erpmini.erp_core.modules.auth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto> getAll() {
        return userRepo.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserDto createUser(String username, String password, String fullName, String email, Set<String> roleNames) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setActive(true);

        Set<Role> roles = roleNames.stream()
                .map(r -> roleRepo.findByName(r).orElseThrow(() -> new RuntimeException("Role not found: " + r)))
                .collect(Collectors.toSet());
        user.setRoles(roles);

        return userMapper.toDto(userRepo.save(user));
    }

    public void deactivateUser(Long id) {
        User user = userRepo.findById(id).orElseThrow();
        user.setActive(false);
        userRepo.save(user);
    }

    public void resetPassword(Long id, String newPassword) {
        User user = userRepo.findById(id).orElseThrow();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    public void assignRole(Long userId, String roleName) {
        User user = userRepo.findById(userId).orElseThrow();
        Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        userRepo.save(user);
    }
    
    public UserDto getProfile(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return userMapper.toDto(user);
    }

    public void changePassword(String username, String oldPass, String newPass) {
        User user = userRepo.findByUsername(username).orElseThrow();

        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPass));
        userRepo.save(user);
    }
}
