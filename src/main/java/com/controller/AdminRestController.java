package com.controller;

import com.model.User;
import com.service.RoleService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("api")
public class AdminRestController {

    private UserService userService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
    }

    @GetMapping("/users/name")
    public ResponseEntity<User> getAdminByName(Principal principal) {
        return ResponseEntity.ok(userService.getUser(principal.getName()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        User newUser = user;
        userService.addUser(newUser);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable("id") long id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.removeUserById(id);
    }


}
