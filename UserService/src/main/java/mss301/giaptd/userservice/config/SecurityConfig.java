package mss301.giaptd.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth2 -> auth2
                        .requestMatchers("/users/public/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/profile/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/users/admin/**").hasAuthority("ROLE_ADMIN")  // Map từ scope "admin"
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2//.jwt(Customizer.withDefaults())
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())  // Custom extractor từ scopes
                        )
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
    }
    // Custom converter: Map scopes từ JWT claims thành Spring authorities (Google không có roles, dùng scopes)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract scopes từ claim "scope" (space-separated string)
            String scopeClaim = jwt.getClaimAsString("email");
            System.out.println("Extracted scope claim: " + scopeClaim);
            if (scopeClaim != null) {
                if(scopeClaim.endsWith("@fpt.edu.vn")) {
                    return Arrays.stream(scopeClaim.split(" "))
                            .filter(scope -> !scope.isEmpty())
                            .map(scope -> new SimpleGrantedAuthority("ROLE_ADMIN"))
                            .collect(Collectors.toList());
                } else {
                    return Arrays.stream(scopeClaim.split(" "))
                            .filter(scope -> !scope.isEmpty())
                            .map(scope -> new SimpleGrantedAuthority("ROLE_USER"))
                            .collect(Collectors.toList());
                }
            }
            // Fallback: Default authorities từ email/profile
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));  // Giả sử tất cả user là USER
        });
        return converter;
    }
}