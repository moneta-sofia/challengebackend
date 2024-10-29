package elxrojo.user_service.service.Implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import elxrojo.user_service.exception.CustomException;
import elxrojo.user_service.model.DTO.UserDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeycloakService {

    @Value("${keycloak.server-url}")
    private String SERVER_URL;

    @Value("${keycloak.realm}")
    private String REALM;

    @Value("${keycloak.client-id}")
    private String CLIENT_ID;

    @Value("${keycloak.client-secret}")
    private String CLIENT_SECRET;

    @Value("${keycloak.admin-username}")
    private String ADMIN_USERNAME;

    @Value("${keycloak.admin-password}")
    private String ADMIN_PASSWORD;

    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);


    private Keycloak UserInstance(String user, String password) {
        try {
            return KeycloakBuilder.builder()
                    .serverUrl(SERVER_URL)
                    .realm(REALM)
                    .clientId(CLIENT_ID)
                    .clientSecret(CLIENT_SECRET)
                    .username((user != null) ? user : ADMIN_USERNAME)
                    .password((password != null) ? password : ADMIN_PASSWORD)
                    .build();
        } catch (Exception e) {
            log.error("Error creating Keycloak instance: ", e);
            throw new RuntimeException("Failed to create Keycloak instance: " + e.getMessage());
        }
    }


    public String createUserInKeycloak(UserDTO userDTO) {
        String userId = null;
        try {

            Keycloak instance = UserInstance(null, null);

            // Crear representación del usuario
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEnabled(true);
            userRepresentation.setUsername(userDTO.getEmail());
            userRepresentation.setEmail(userDTO.getEmail());
            userRepresentation.setFirstName(userDTO.getFirstName());
            userRepresentation.setLastName(userDTO.getLastName());

            // Agregar atributos personalizados
            Map<String, List<String>> attributes = new HashMap<>();
            attributes.put("phone", Collections.singletonList(userDTO.getPhone().toString()));
            attributes.put("dni", Collections.singletonList(userDTO.getDni().toString()));

            userRepresentation.setAttributes(attributes);

            // Crear credenciales
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(false);
            credential.setValue(userDTO.getPassword());
            credential.setType(CredentialRepresentation.PASSWORD);

            userRepresentation.setCredentials(List.of(credential));

            // Enviar solicitud a Keycloak
            Response response = instance.realm(REALM).users().create(userRepresentation);

            if (response.getStatus() != 201) {
                String errorMessage = response.readEntity(String.class); // Captura el mensaje de error de Keycloak
                throw new RuntimeException("Failed to create user in Keycloak: " + response.getStatus() + " - " + errorMessage);
            } else {
                // Obtener el `sub` del usuario creado
                String location = response.getHeaderString("Location");
                if (location == null) {
                    throw new RuntimeException("Failed to retrieve user ID from Keycloak");
                }

                // Extraer el ID de la URL de ubicación
                userId = location.substring(location.lastIndexOf('/') + 1);
            }

            instance.close();
            return userId;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to create user in Keycloak");
        }
    }


    public Optional<UserRepresentation> findInKeycloak(String sub) {
        try {
            Keycloak instance = UserInstance(null, null);
            UserRepresentation userFound = instance.realm(REALM).users().get(sub).toRepresentation();
            instance.close();
            return Optional.ofNullable(userFound);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to find user in Keycloak");
        }
    }


    public void updateUser(UserDTO userDTO, String sub) {
        Keycloak instance = UserInstance(null, null);
        UserRepresentation existingUser = instance.realm(REALM).users().get(sub).toRepresentation();

        if (existingUser == null) {
            throw new CustomException("User not found", HttpStatus.NOT_FOUND);
        }

        if (userDTO.getFirstName() != null) {
            existingUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            existingUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setUsername(userDTO.getEmail());
        }

        Map<String, List<String>> attributes = existingUser.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        if (userDTO.getPhone() != null) {
            attributes.put("phone", Collections.singletonList(userDTO.getPhone().toString()));
        }
        if (userDTO.getDni() != null) {
            attributes.put("dni", Collections.singletonList(userDTO.getDni().toString()));
        }

        existingUser.setAttributes(attributes);
        // Enviar la actualización a Keycloak

        instance.realm(REALM).users().get(sub).update(existingUser);

        instance.close();

    }


    public String getToken(String username, String password) {
        try {
            Keycloak instance = UserInstance(username, password);
            String token = instance.tokenManager().grantToken().getToken();
            instance.close();
            return token;
        } catch (Exception e) {
            log.error("Error getting token: ", e);
            throw new RuntimeException("Failed to get token");
        }
    }


    public void logoutInKeycloak(String token) {
        try {
            Keycloak instance = UserInstance(null, null);
            DecodedJWT jwt = JWT.decode(token);
            String sub = jwt.getSubject();
            List<UserSessionRepresentation> sessionRepresentations = instance.realm(REALM).users().get(sub).getUserSessions();
            if (sessionRepresentations.isEmpty()) {
                throw new CustomException("No sessions to close", HttpStatus.BAD_REQUEST);
            }
            instance.realm(REALM).users().get(sub).logout();
            instance.close();
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed logout");
        }
    }

}
