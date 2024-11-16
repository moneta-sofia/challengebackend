    package elxrojo.gateway.configuration;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import org.springframework.http.HttpMethod;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.web.server.ServerHttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.server.SecurityWebFilterChain;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.reactive.CorsConfigurationSource;
    import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

    @Configuration
    public class SecurityConfigGateway {

        @Bean
        public SecurityWebFilterChain securityFilterChainGateway(ServerHttpSecurity http) throws Exception {
            http
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/users/login").permitAll()
                            .pathMatchers("/users/").permitAll()
                            .pathMatchers("/accounts").permitAll()
                            .pathMatchers( HttpMethod.GET, "/users/{id}").permitAll()
                            .anyExchange().authenticated()
                    )
                    .csrf(csrf -> csrf.disable())
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.jwkSetUri("http://localhost:8080/realms/BackendChallenge/protocol/openid-connect/certs"))
                    );
            return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("http://localhost:3000");
            configuration.addAllowedMethod("*");
            configuration.addAllowedHeader("*");
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
    }