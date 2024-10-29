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

    //Add camps validations to all post requests
    //Add validation comparison  with the ids and the token's ids


//    User functions

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

    public void logout(String token) {
        keycloakService.logoutInKeycloak(token);
    }

    @Override
    public UserDTO getUserById(String id) {
        Optional<UserRepresentation> user = keycloakService.findInKeycloak(id);
        if (user.isPresent()) {
            Map<String, List<String>> attributes = user.get().getAttributes();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.get().getEmail());
            userDTO.setFirstName(user.get().getFirstName());
            userDTO.setLastName(user.get().getLastName());
            userDTO.setDni(Long.parseLong(attributes.get("dni").get(0)));
            userDTO.setPhone(Long.parseLong(attributes.get("phone").get(0)));
            String idKl = user.get().getId();

            System.out.println(idKl);
            System.out.println(idKl);
            System.out.println(idKl);
            System.out.println(idKl);
            System.out.println(idKl);
            userDTO.setId(idKl);

            return userDTO;
        }
        return null;
    }

    @Override
    public UserDTO updateUser(UserDTO userUpdated, String sub) {
        if (userUpdated.getPassword() != null) {
            throw new CustomException("You cannot update the password here ", HttpStatus.BAD_REQUEST);
        }
        User user = getUserBySub(sub);
        keycloakService.updateUser(userUpdated, sub);
        userMapper.updateUser(userUpdated, user);
        userRepository.save(user);
        return mapper.convertValue(user, UserDTO.class);
    }


//    Account functions

//    @Override
//    public AccountDTO getAccountByUser(String userSub) {
//        try {
//            Long userId = getUserBySub(userSub).getId();
//            return accountRepository.getAccountByUser(userId);
//        } catch (FeignException.NotFound ex) {
//            throw new CustomException("Account not found", HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (FeignException ex) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Override
//    public AccountDTO updateAccount(String userSub, AccountDTO account) {
//        try {
//            return accountRepository.updateAccount(getUserBySub(userSub).getAccountId(), account);
//        } catch (FeignException.NotFound ex) {
//            throw new CustomException("Account not found with that id", HttpStatus.NOT_FOUND);
//        } catch (FeignException.BadRequest ex) {
//            throw new CustomException("Cannot change IDs", HttpStatus.BAD_REQUEST);
//        } catch (FeignException ex) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//

//    Transaction function

//    @Override
//    public List<TransactionDTO> getTransactionsByAccount(String sub, Integer limit) {
//        try {
//            return accountRepository.getTransactionsByAccount(getUserBySub(sub).getAccountId(), limit);
//        } catch (FeignException ex) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    Card function

//    @Override
//    public void createAccountCard(CardDTO cardDTO, String userSub) {
//        try {
//            accountRepository.createAccountCard(cardDTO, getUserBySub(userSub).getAccountId());
//        } catch (FeignException.BadRequest ex) {
//            throw new CustomException("Card already in use", HttpStatus.BAD_REQUEST);
//        } catch (FeignException e) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Override
//    public CardDTO getCardById(String userSub, Long cardId) {
//        try {
//            return accountRepository.getCardById(getUserBySub(userSub).getAccountId(), cardId);
//        } catch (FeignException.NotFound ex) {
//            throw new CustomException("Card not found with that id", HttpStatus.NOT_FOUND);
//        } catch (FeignException.Unauthorized ex) {
//            throw new CustomException("Without permission to get this card!", HttpStatus.UNAUTHORIZED);
//        } catch (FeignException ex) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Override
//    public List<CardDTO> getCardsByAccount(String userSub) {
//        try {
//            return accountRepository.getCardsByAccount(getUserBySub(userSub).getAccountId());
//        } catch (FeignException ex) {
//            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Override
//    public void deleteCard(String userSub, Long cardId) {
//        try {
//            accountRepository.deleteCard(getUserBySub(userSub).getAccountId(), cardId);
//        } catch (FeignException.NotFound ex) {
//            throw new CustomException("Card not found with that id", HttpStatus.NOT_FOUND);
//        } catch (FeignException.Unauthorized ex) {
//            throw new CustomException("Without permission to delete this card!", HttpStatus.UNAUTHORIZED);
//        } catch (FeignException ex) {
//            throw new CustomException("Error calling card-service", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    Other functions

    private User getUserBySub(String sub) {
        Optional<UserRepresentation> userKl = keycloakService.findInKeycloak(sub);
        if (userKl.isEmpty()) {
            throw new CustomException("User not found ", HttpStatus.NOT_FOUND);
        }
        return userRepository.findByEmail(userKl.get().getEmail());
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
