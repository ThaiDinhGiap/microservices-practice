package mss301.giaptd.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/public/info")
    public String publicInfo() {
        return "Public information for user.";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')") // Kiểm tra scope "user"
    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal Jwt jwt, Authentication auth) {
        String email = jwt != null ? jwt.getClaimAsString("email") : "Unknown";
        System.out.println("Email from JWT: " + email);

        if (jwt != null) {
            System.out.println("JWT Claims: " + jwt.getClaims());
            Map<String, String> jwtInfo = Map.of(
                    "userId", jwt.getClaimAsString("sub"),
                    "email", jwt.getClaimAsString("email"),
                    "name", jwt.getClaimAsString("name")
            );
        }

        List<String> authorities = null;
        if (auth != null) {
            authorities = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }

        return "Profile for user: " + email + " with authorities: " +
                (authorities == null ? "" : authorities);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Kiểm tra scope "admin"
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "Admin dashboard - Restricted access.";
    }
}
