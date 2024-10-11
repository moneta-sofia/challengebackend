//package elxrojo.user_service.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Permitir CORS para todas las rutas
//                .allowedOrigins("http://localhost:3000") // Especificar los orígenes permitidos
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
//                .allowedHeaders("*") // Permitir todos los encabezados
//                .allowCredentials(true) // Permitir credenciales si es necesario
//                .maxAge(3600);
//    }
//}
