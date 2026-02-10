package in.rajat.blooms.controller;

import in.rajat.blooms.dto.UserRequest;
import in.rajat.blooms.dto.UserResponse;
import in.rajat.blooms.models.User;
import in.rajat.blooms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequest request) {
        User newUser = new User();
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setPassword(request.getPassword());
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setProfileUrl(request.getProfileUrl());

        return userService.registerUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        UserResponse response = userService.loginUser(
                request.getPhoneNumber(),
                request.getPassword());

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("❌ Invalid Phone Number or Password", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
