package elxrojo.user_service.service.Implementation;

import elxrojo.user_service.model.DTO.UserDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakService {

    private static final Logger log = LoggerFactory.getLogger(KeycloakService.class);
    @Value("${keycloak.realm}")
    private String REALM;

    private Keycloak keycloak;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public int createUserInKeycloak(UserDTO userDTO) {
        Response response = null;
        try {

            // Crear representación del usuario
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setEnabled(true);
            userRepresentation.setUsername(userDTO.getDni().toString());
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
            response = keycloak.realm(REALM).users().create(userRepresentation);

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

}
