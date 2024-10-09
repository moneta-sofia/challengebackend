//package elxrojo.user_service.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
////@EnableWebSecurity
//public class SecurityConfigUser {
//
//    @Bean
//    public SecurityWebFilterChain securityFilterChainUser(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange(auth -> auth
//                        .pathMatchers("/users/register").permitAll()
//                        .pathMatchers("/actuator/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//}
