package mss301.giaptd.gooauth2gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.*;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        System.out.println("Configuring SecurityFilterChain:");
        // Tạo handler thành công (Chuyển hướng về trang chủ)
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create("/users/public/info")); // Định nghĩa URI

        // Tạo handler để hủy session và xóa token (Được thực hiện trước khi chuyển hướng)
        ServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
                new WebSessionServerLogoutHandler(), // Hủy Session (non-blocking)
                new SecurityContextServerLogoutHandler() // Xóa SecurityContext
        );
        http.authorizeExchange(authorize -> authorize
                        .pathMatchers("/logout/**").permitAll()
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/users/public/**").permitAll()
                        .pathMatchers("/login/oauth2/code/google/**").permitAll()
                        .pathMatchers("/users/profile/**").authenticated()//hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "OAUTH2_USER")
                        .pathMatchers("/users/admin/**").authenticated()//hasAnyAuthority("ROLE_ADMIN", "OAUTH2_USER") //Role based access
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2//.jwt(Customizer.withDefaults())
                        .jwt(jwt -> jwt
                                .jwtDecoder(reactiveJwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        ))
                .csrf(csrf -> csrf.disable())
                .oauth2Login(Customizer.withDefaults())//oauth2 -> oauth2)
                .logout(logout -> logout
                        // URL để người dùng gửi request logout (mặc định là /logout)
                        .logoutUrl("/logout")
                        // URL chuyển hướng sau khi logout thành công
                        .logoutHandler(logoutHandler)
                        .logoutSuccessHandler(successHandler));
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        // Configure and return a JwtDecoder instance
        System.out.println("Configuring JwtDecoder with Google's JWK Set URI");
        return NimbusReactiveJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .build();
    }

    // Custom converter: Map scopes từ JWT claims thành Spring authorities (Google không có roles, dùng scopes)
    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Extract scopes từ claim "scope" (space-separated string)
            String scopeClaim = jwt.getClaimAsString("email");
            System.out.println("Extracted scope claim: " + scopeClaim);
            if (scopeClaim == null || scopeClaim.isEmpty()) {
                return Flux.fromIterable(List.of("ROLE_USER"))
                        .map(SimpleGrantedAuthority::new);
            } else {
                List<String> scopes = List.of(scopeClaim.split(" "));
                return Flux.fromIterable(scopes)
                        .map(scope -> scope.endsWith("@fpt.edu.vn") ? "ROLE_" + "ADMIN" : "ROLE_" + "USER")  // Prefix cho Spring
                        .map(SimpleGrantedAuthority::new);
            }
        });
        return jwtAuthenticationConverter;
    }
}