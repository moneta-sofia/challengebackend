    package elxrojo.gateway.configuration;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.web.server.ServerHttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.server.SecurityWebFilterChain;

    @Configuration
    public class SecurityConfigGateway {

        @Bean
        public SecurityWebFilterChain securityFilterChainGateway(ServerHttpSecurity http) throws Exception {
            http
                    .authorizeExchange(exchanges -> exchanges
                            .pathMatchers("/users/register").permitAll()  // Rutas públicas
                            .anyExchange().authenticated()                // Otras rutas deben estar autenticadas
                    )
                    .csrf(csrf -> csrf.disable())                    // Deshabilitar CSRF si no lo necesitas
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.jwkSetUri("http://localhost:8080/realms/BackendChallenge/protocol/openid-connect/certs"))
                    );
            return http.build();
        }
    }