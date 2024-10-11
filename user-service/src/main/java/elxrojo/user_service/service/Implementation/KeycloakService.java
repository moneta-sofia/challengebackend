package elxrojo.user_service.service.Implementation;

import elxrojo.user_service.model.DTO.UserDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.keycloak.admin.client.KeycloakBuilder.builder;

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


    private Keycloak createUserInstance(){
        try {
            return KeycloakBuilder.builder()
                    .serverUrl(SERVER_URL)
                    .realm(REALM)
                    .clientId(CLIENT_ID)
                    .clientSecret(CLIENT_SECRET)
                    .username(ADMIN_USERNAME)
                    .password(ADMIN_PASSWORD)
                    .build();
        } catch (Exception e){
            log.error("Error creating Keycloak instance: ", e);
            throw new RuntimeException("Failed to create Keycloak instance: " + e.getMessage());
        }
    }


    public int createUserInKeycloak(UserDTO userDTO) {
        Response response = null;
        try {

            Keycloak instance = createUserInstance();

            // Crear representación del usuario
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEnabled(true);
            userRepresentation.setUsername(userDTO.getEmail());
            userRepresentation.setEmail(userDTO.getEmail());
            userRepresentation.setFirstName(userDTO.getFirstName());
            userRepresentation.setLastName(userDTO.getLastName());

            // Crear credenciales
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(false);
            credential.setValue(userDTO.getPassword());
            credential.setType(CredentialRepresentation.PASSWORD);

            userRepresentation.setCredentials(List.of(credential));

            // Enviar solicitud a Keycloak
            response = instance.realm(REALM).users().create(userRepresentation);

            if (response.getStatus() != 201) {
                String errorMessage = response.readEntity(String.class); // Captura el mensaje de error de Keycloak
                throw new RuntimeException("Failed to create user in Keycloak: " + response.getStatus() + " - " + errorMessage);
            }
            return response.getStatus();
        } catch (Exception e) {
            log.error("Error creating user in Keycloak: ", e);
            throw new RuntimeException("Failed to create user in Keycloak: " + e.getMessage());
        }
    }



    public String getToken(String username, String password) {
        try {
            Keycloak getTokenInstance = KeycloakBuilder.builder()
                    .serverUrl(SERVER_URL)
                    .realm(REALM)
                    .clientId(CLIENT_ID)
                    .clientSecret(CLIENT_SECRET)
                    .username(username)
                    .password(password)
                    .build();

            return getTokenInstance.tokenManager().grantToken().getToken();
        } catch (Exception e) {
            log.error("Error getting token: ", e);
            throw new RuntimeException("Failed to get token: " + e.getMessage());
        }
    }

}
