package BitlabAcademycom.example.bitIntern.controller;



import BitlabAcademycom.example.bitIntern.dto.*;
import BitlabAcademycom.example.bitIntern.service.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        JwtResponse token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest refreshRequest) {
        try {
            JwtResponse jwtResponse = authService.refreshToken(refreshRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest registerRequest) {
        try {
            authService.registerUser(registerRequest);
            return ResponseEntity.ok("Пользователь успешно зарегистрирован администратором!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UserUpdateRequest updateRequest) {

        String userId = jwt.getSubject();

        java.util.Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        boolean isAdmin = false;
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            java.util.List<String> roles = (java.util.List<String>) realmAccess.get("roles");
            isAdmin = roles.contains("ROLE_ADMIN");
        }

        authService.updateUser(userId, updateRequest, isAdmin);
        return ResponseEntity.ok("Данные успешно обновлены!");
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> adminUpdateUser(
            @PathVariable String userId,
            @RequestBody UserUpdateRequest updateRequest) {

        authService.updateUser(userId, updateRequest, true);

        return ResponseEntity.ok("Данные пользователя с ID " + userId + " успешно обновлены админом!");
    }
}