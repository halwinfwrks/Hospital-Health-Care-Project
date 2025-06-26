package app.develope.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.develope.model.User;
import app.develope.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final String TAG = "UserController";

    private final UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User entity) {
        if (userService.userExists(entity.getUsername())) {
            log.warn("{}: User already exists: {}", TAG, entity.getUsername());
            return ResponseEntity.badRequest().body("User already exists");
        }
        log.info("{}: Adding new user: {}", TAG, entity.getUsername());
        entity.setRoles("USER"); // Default role for new users
        User savedUser = userService.saveUser(entity);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (!userService.userExists(user.getUsername())) {
            log.warn("{}: User not found: {}", TAG, user.getUsername());
            return ResponseEntity.badRequest().body("User not found");
        }
        log.info("{}: Updating user: {}", TAG, user.getUsername());
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody User user) {
        if (!userService.userExists(user.getUsername())) {
            log.warn("{}: User not found: {}", TAG, user.getUsername());
            return ResponseEntity.badRequest().body("User not found");
        }
        log.info("{}: Deleting user: {}", TAG, user.getUsername());
        userService.deleteUser(user.getUsername());
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/get-user")
    public ResponseEntity<?> getUser(@RequestBody String username) {
        User foundUser = userService.findByUsername(username);
        if (foundUser == null) {
            log.warn("{}: User not found: {}", TAG, username);
            return ResponseEntity.badRequest().body("User not found");
        }
        log.info("{}: Retrieved user: {}", TAG, username);
        return ResponseEntity.ok(foundUser);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        log.info("{}: Retrieving all users", TAG);
        return ResponseEntity.ok(userService.getUsers());
    }
}
