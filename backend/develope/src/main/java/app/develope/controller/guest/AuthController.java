package app.develope.controller.guest;

import app.develope.dto.AuthRequest;
import app.develope.dto.JwtResponse;
import app.develope.dto.RefreshTokenRequest;
import app.develope.model.User;
import app.develope.service.UserService;
import app.develope.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final String TAG = "AuthController";

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000L; // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // 7 days

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    /**
     * Đăng nhập -> Trả về access token và refresh token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()));

            User user = userService.findByUsername(authRequest.getUsername());

            // Always generate new access token (short-lived)
            String accessToken = jwtUtils.generateToken(authRequest.getUsername(), ACCESS_TOKEN_EXPIRATION);

            String refreshToken;

            if (user.getRemeberToken() != null && jwtUtils.validateToken(user.getRemeberToken())) {
                // Reuse old refresh token if still valid
                refreshToken = user.getRemeberToken();
                log.info("{}: User {} logged in with existing valid refresh token.", TAG, authRequest.getUsername());
            } else {
                // Generate new refresh token
                refreshToken = jwtUtils.generateToken(authRequest.getUsername(), REFRESH_TOKEN_EXPIRATION);
                user.setRemeberToken(refreshToken);
                userService.updateUser(user);
                log.info("{}: User {} logged in with new refresh token.", TAG, authRequest.getUsername());
            }

            log.info("{}: User {} logged in successfully.", TAG, authRequest.getUsername());

            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));

        } catch (BadCredentialsException ex) {
            log.warn("{}: Login failed for user {}: {}", TAG, authRequest.getUsername(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception ex) {
            log.error("{}: Error during login for user {}: {}", TAG, authRequest.getUsername(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    /**
     * Làm mới access token từ refresh token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        try {
            if (!jwtUtils.validateToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            String username = jwtUtils.extractUsername(refreshToken);
            User user = userService.findByUsername(username);

            if (user == null || !refreshToken.equals(user.getRemeberToken())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token does not match");
            }

            String newAccessToken = jwtUtils.generateToken(username, ACCESS_TOKEN_EXPIRATION);
            return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));

        } catch (Exception ex) {
            log.error("{}: Error refreshing token for user {}: {}", TAG, request.getUsername(), ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody AuthRequest request) {
        userService.clearRefreshToken(request.getUsername());
        return ResponseEntity.ok("Logged out successfully");
    }
}
