package org.spring.boot.jdbc.auth.controller;

import org.spring.boot.jdbc.auth.domain.ApplicationUser;
import org.spring.boot.jdbc.auth.dto.ApplicationUserDto;
import org.spring.boot.jdbc.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser applicationUser) {
        userService.save(applicationUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public List<ApplicationUserDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
