package elxrojo.user_service.service.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.user_service.exception.CustomException;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.model.UserWithTokenResponse;
import elxrojo.user_service.repository.AccountRepository;
import elxrojo.user_service.repository.IUserRepository;
import elxrojo.user_service.service.IUserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KeycloakService keycloakService;

    @Value("${keycloak.realm}")
    private String REALM;

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public UserWithTokenResponse signup(UserDTO userDTO) throws IOException {

        String token;

        try {
            //        Validations

            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new CustomException("This email already exists!", HttpStatus.BAD_REQUEST);
            }
            if (userRepository.existsByDni(userDTO.getDni())) {
                throw new CustomException("This DNI already exists!", HttpStatus.BAD_REQUEST);
            }
            if (userRepository.existsByPhone(userDTO.getPhone())) {
                throw new CustomException("This phone already exists!", HttpStatus.BAD_REQUEST);
            }

//                    Generating Alias and CVU
            String cvu = generateCVU();
//            while (userRepository.existsByCvu(cvu)) {
//                cvu = generateCVU();
//            }

            String alias = generateAlias();
//            while (userRepository.existsByAlias(alias)) {
//                alias = generateAlias();
//            }


            log.debug("Intentando crear usuario en Keycloak...");
            int UserCreatedKL = keycloakService.createUserInKeycloak(userDTO);
            log.debug("Código de estado al crear usuario en Keycloak: {}", UserCreatedKL);

            log.debug("Obteniendo token para el nuevo usuario...");
            token = keycloakService.getToken(userDTO.getEmail(), userDTO.getPassword());

            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            if (UserCreatedKL == 201) {
                log.debug("Guardando usuario en el repositorio...");

                User user = mapper.convertValue(userDTO, User.class);
                Long idGenerated = userRepository.save(user).getId();
                log.debug("Usuario guardado con ID generado: {}", idGenerated);
                Long accountId = accountRepository.createAccount(alias, cvu, idGenerated );
                user.setAccountId(accountId);
                userRepository.save(user);

            }
            return new UserWithTokenResponse(userDTO, token);
        } catch (CustomException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("An error occurred during sign up!" + e.getMessage(), e);
        }
    }

    @Override
    public String login(String email, String password) {
        try {

            String passwordFound = userRepository.findPasswordByEmail(email);
            System.out.println(passwordFound);

            if (email.isBlank() || password.isBlank()) {
                throw new CustomException("Incomplete login information", HttpStatus.BAD_REQUEST);
            } else if (!userRepository.existsByEmail(email)) {
                throw new CustomException("Non-existent user :/", HttpStatus.NOT_FOUND);
            } else if (!passwordEncoder.matches(password, passwordFound)) {
                throw new CustomException("Incorrect password :/", HttpStatus.UNAUTHORIZED);
            }

            return keycloakService.getToken(email, password);
        } catch (CustomException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed login: " + e.getMessage());
        }
    }

    public void logout(String token){
        try {
            keycloakService.logoutInKeycloak(token);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to logout: " + e.getMessage());
        }
    }


    @Override
    public UserDTO getUserById(String id) {
        try {
            Optional<UserRepresentation> user = keycloakService.findInKeycloak(id);
            if (user.isPresent()) {
                Map<String, List<String>> attributes = user.get().getAttributes();
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(user.get().getEmail());
                userDTO.setFirstName(user.get().getFirstName());
                userDTO.setLastName(user.get().getLastName());
                userDTO.setDni(Long.parseLong(attributes.get("dni").get(0)));
                userDTO.setPhone(Long.parseLong(attributes.get("phone").get(0)));

                return userDTO;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error converting user");
        }
    }

    private String generateCVU() {
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cvu.append((int) (Math.random() * 10));
        }
        return cvu.toString();
    }

    public String generateAlias() throws IOException {
        try {
            StringBuilder alias = new StringBuilder();
            Resource resource = resourceLoader.getResource("classpath:alias.txt");

            // Read the lines from the file
            List<String> words;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                words = reader.lines().toList();
            }

            for (int i = 0; i < 3; i++) {
                alias.append(words.get(new Random().nextInt(words.size())));
                if (i < 2) {
                    alias.append(".");
                }
            }
            return alias.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
