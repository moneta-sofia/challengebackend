//package elxrojo.gateway.configuration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize ->
//                        authorize
//                                .requestMatchers("/users/register").permitAll()  // Permitir acceso sin autenticación
//                                .anyRequest().authenticated()                    // Todos los demás deben estar autenticados
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwkSetUri("http://localhost:8080/realms/BackendChallenge/protocol/openid-connect/certs"))
//                );
//        return http.build();
//    }
//}
