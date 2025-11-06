package mss301.giaptd.gooauth2gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {
    private final ReactiveOAuth2AuthorizedClientService authorizedClientService;

    public AuthenticationFilter(ReactiveOAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("AuthenticationFilter - filter called: " + exchange.getRequest().getURI().getPath());

        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication())
                .flatMap(authentication -> {
                    System.out.println("Authentication detected: " + (authentication == null ? "null" : authentication.getPrincipal().toString()));
                    System.out.println("Authentication class: " + (authentication != null ? authentication.getClass() : "null"));

                    if (authentication != null) {
                        System.out.println("OAuth2 Authentication detected for user: " + authentication.getName());

                        return authorizedClientService.loadAuthorizedClient(
                                        ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                                        authentication.getName()
                                )
                                .map(authorizedClient -> {
                                    OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                                    System.out.println("Access Token retrieved: " + accessToken.getTokenValue());

                                    // Lấy ID Token từ Authentication
                                    DefaultOidcUser idToken = (DefaultOidcUser) authentication.getPrincipal();
                                    if (idToken.getIdToken() != null) {
                                        System.out.println("ID Token retrieved: " + idToken.getIdToken().getTokenValue());
                                    }

                                    System.out.println("Email retrieved: " + idToken.getEmail());

                                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                            .header("X-User-Email", idToken.getEmail())
                                            .header("Authorization", "Bearer " + idToken.getIdToken().getTokenValue())
                                            .build();

                                    return exchange.mutate().request(mutatedRequest).build();
                                });
                    } else {
                        System.out.println("No OAuth2 Authentication detected");
                        return Mono.just(exchange);
                    }
                })
                .flatMap(chain::filter);
    }
}