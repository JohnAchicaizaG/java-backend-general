package com.test.general_backend_java.controller;

import com.test.general_backend_java.dto.ErrorResponseDto;
import com.test.general_backend_java.dto.UserDto;
import com.test.general_backend_java.dto.UserRequestDto;
import com.test.general_backend_java.model.User;
import com.test.general_backend_java.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/validate")
    public ResponseEntity<ErrorResponseDto> validateUser(@RequestBody @Valid UserRequestDto userRequest) {
        Optional<User> user = userService.findByEmail(userRequest.getUsername());
        if (user.isPresent() && userService.checkPassword(userRequest.getPassword(), user.get().getPassword())) {
            ErrorResponseDto successResponse = ErrorResponseDto.builder()
                    .message("User exists and password matches.")
                    .statusCode(200)
                    .build();
            return ResponseEntity.ok(successResponse);
        } else {
            ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                    .message("Invalid credentials.")
                    .statusCode(401)
                    .build();
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDto userDto) {
        if (userService.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("User already exists.");
        }
        userService.registerUser(userDto);
        return ResponseEntity.status(201).body("User registered successfully.");
    }
}