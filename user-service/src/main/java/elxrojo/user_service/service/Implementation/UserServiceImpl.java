package elxrojo.user_service.service.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.user_service.exception.CustomException;
import elxrojo.user_service.model.DTO.AccountDTO;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.model.UserWithTokenResponse;
import elxrojo.user_service.external.repository.AccountRepository;
import elxrojo.user_service.repository.IUserMapper;
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
    private IUserMapper userMapper;

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

        AccountDTO newAccount = new AccountDTO(null, alias, cvu, null);

        String userCreatedId = keycloakService.createUserInKeycloak(userDTO);
        token = keycloakService.getToken(userDTO.getEmail(), userDTO.getPassword());

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        if (userCreatedId != null) {

            userDTO.setId(userCreatedId);
            User user = mapper.convertValue(userDTO, User.class);
            String idGenerated = userRepository.save(user).getId();

            newAccount.setName(user.getFirstName() + " " + user.getLastName());
            newAccount.setUserId(idGenerated);
            Long accountId = accountRepository.createAccount(newAccount);

            user.setAccountId(accountId);
            userRepository.save(user);

        }

        return new UserWithTokenResponse(userDTO, token);
    }

    @Override
    public String login(String email, String password) {
        String passwordFound = userRepository.findPasswordByEmail(email);
        System.out.println(passwordFound);

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new CustomException("Incomplete login information", HttpStatus.BAD_REQUEST);
        } else if (!userRepository.existsByEmail(email)) {
            throw new CustomException("Non-existent user :/", HttpStatus.NOT_FOUND);
        } else if (!passwordEncoder.matches(password, passwordFound)) {
            throw new CustomException("Incorrect password :/", HttpStatus.UNAUTHORIZED);
        }

        return keycloakService.getToken(email, password);
    }

    @Override
    public void logout(String token) {
        keycloakService.logoutInKeycloak(token);
    }

    @Override
    public UserWithTokenResponse getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO(
                    user.get().getId(),
                    user.get().getFirstName(),
                    user.get().getLastName(),
                    user.get().getDni(),
                    user.get().getEmail(),
                    user.get().getPhone()
            );

            String token = keycloakService.getToken(user.get().getEmail(), user.get().getPassword());
            return new UserWithTokenResponse(userDTO,);
        }
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userUpdated, String id) {
        if (userUpdated.getPassword() != null) {
            throw new CustomException("You cannot update the password here ", HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findById(id);
        keycloakService.updateUser(userUpdated, id);
        userMapper.updateUser(userUpdated, user.get());

        accountRepository.updateAccount(
                user.get().getAccountId(),
                new AccountDTO(user.get().getAccountId(),user.get().getFirstName()  + " " + user.get().getLastName(), user.get().getId()));

        userRepository.save(user.get());
        return mapper.convertValue(user.get(), UserDTO.class);
    }


//    Other functions

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
