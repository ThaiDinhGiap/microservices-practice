package mss301.giaptd.gooauth2gateway.config;

import mss301.giaptd.gooauth2gateway.security.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth service (không cần filter)
                .route("auth_service", r -> r.path("/auth/**")
                        .uri("lb://user-service"))

                // User service - public API (không cần filter)
                .route("user_service_public", r -> r.path("/users/public/**")
                        .uri("lb://user-service"))

                // User service - private API (có filter xác thực)
                .route("user_service", r -> r.path("/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))

                .build();
    }
}