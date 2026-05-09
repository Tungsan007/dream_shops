package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.UserDto;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourcesNotFoundException;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.requests.CreateUserRequest;
import com.dailycodework.dream_shops.requests.UpdateUserRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Found!", userDto));
        } catch (ResourcesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found!", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("The user info already exist", e.getMessage()));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update Success!", userDto));
        } catch (ResourcesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", e.getMessage()));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
        } catch (ResourcesNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found", e.getMessage()));
        }
    }
}
