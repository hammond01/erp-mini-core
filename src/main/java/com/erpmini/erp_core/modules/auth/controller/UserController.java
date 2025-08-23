package com.erpmini.erp_core.modules.auth.controller;

import com.erpmini.erp_core.modules.auth.dto.UserDto;
import com.erpmini.erp_core.modules.auth.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public UserDto create(@RequestBody Map<String, Object> body) {
        return userService.createUser(
                (String) body.get("username"),
                (String) body.get("password"),
                (String) body.get("fullName"),
                (String) body.get("email"),
                new HashSet<>((List<String>) body.get("roles"))
        );
    }

    @PostMapping("/{id}/reset-password")
    public String resetPassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        userService.resetPassword(id, body.get("newPassword"));
        return "Password updated";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivate(@PathVariable Long id) {
        userService.deactivateUser(id);
        return "User deactivated";
    }

    @PostMapping("/{id}/assign-role")
    public String assignRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        userService.assignRole(id, body.get("role"));
        return "Role assigned";
    }

    @GetMapping("/me")
    public UserDto getProfile(Authentication authentication) {
        String username = authentication.getName();
        return userService.getProfile(username);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody Map<String, String> body, Authentication auth) {
        userService.changePassword(auth.getName(), body.get("oldPassword"), body.get("newPassword"));
        return "Password changed successfully";
    }


}
