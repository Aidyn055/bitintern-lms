package BitlabAcademycom.example.bitIntern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getSecureData() {
        return ResponseEntity.ok("Секретные данные для админа доступны!");
    }

    @GetMapping("/me")
    public String getMyProfile(Authentication authentication) {
        return "Привет, " + authentication.getName() + "! Твои роли: " + authentication.getAuthorities();
    }
}