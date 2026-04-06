package com.alibou.saas.controllers;

import com.alibou.saas.common.PageResponse;
import com.alibou.saas.requests.UserRequest;
import com.alibou.saas.responses.UserResponse;
import com.alibou.saas.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<Void> createUser(
            @Valid
            @RequestBody
            final UserRequest request) {
        this.userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0")
            final int page,
            @RequestParam(name = "size", defaultValue = "10")
            final int size
    ) {
        final PageResponse<UserResponse> response = this.userService.getAllUsers(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{user-id}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'ADMINISTRATOR')")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable("user-id")
            final String id) {
        final UserResponse response = this.userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{user-id}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<Void> updateUser(
            @PathVariable("user-id")
            final String id,
            @Valid
            @RequestBody
            final UserRequest request) {
        this.userService.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                             .build();
    }

    @DeleteMapping("/{user-id}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("user-id")
            final String id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent()
                             .build();
    }

    @PutMapping("/{user-id}/enable")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<Void> enableUser(
            @PathVariable("user-id")
            final String id) {
        this.userService.enableUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                             .build();
    }

    @PutMapping("/{user-id}/disable")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<Void> disableUser(
            @PathVariable("user-id")
            final String id) {
        this.userService.disableUser(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                             .build();
    }
}
