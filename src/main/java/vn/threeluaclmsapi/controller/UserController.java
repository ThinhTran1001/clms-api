package vn.threeluaclmsapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import vn.threeluaclmsapi.dto.request.CreateUserRequest;
import vn.threeluaclmsapi.dto.response.UserResponse;
import vn.threeluaclmsapi.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse newUser = userService.createUser(request);
        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody CreateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable String id) {
        UserResponse activatedUser = userService.activateUser(id);
        return ResponseEntity.ok(activatedUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<UserResponse> inactivateUser(@PathVariable String id) {
        UserResponse inactivatedUser = userService.inactivateUser(id);
        return ResponseEntity.ok(inactivatedUser);
    }

}
