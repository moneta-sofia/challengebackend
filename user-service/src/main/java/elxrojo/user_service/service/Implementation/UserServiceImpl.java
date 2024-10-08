package elxrojo.user_service.service.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.user_service.exception.BadRequest;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.model.User;
import elxrojo.user_service.repository.IUserRepository;
import elxrojo.user_service.service.IUserService;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private KeycloakService keycloakService;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public UserDTO signup(UserDTO userDTO) throws IOException {
        try {
            //        Validations

            if (userRepository.existsByEmail(userDTO.getEmail())){
                throw new BadRequest("This email already exists!");
            }
            if (userRepository.existsByDni(userDTO.getDni())){
                throw new BadRequest("This DNI already exists!");
            }
            if (userRepository.existsByPhone(userDTO.getPhone())){
                throw new BadRequest("This phone already exists!");
            }

            //        Generating Alias and CVU
            String cvu = generateCVU();
            while (userRepository.existsByCvu(cvu)) {
                cvu = generateCVU();
            }

            String alias = generateAlias();
            while (userRepository.existsByAlias(alias)) {
                alias = generateAlias();
            }

            userDTO.setAlias(alias);
            userDTO.setCvu(cvu);


            int UserCreatedKL = keycloakService.createUserInKeycloak(userDTO);

            if (UserCreatedKL == 200) {
                User user = mapper.convertValue(userDTO, User.class);
                userRepository.save(user);
            }

            return userDTO;

        } catch (Exception e) {
            throw new RuntimeException("An error occurred during sign up!");
        }

    }


    @Override
    public UserDTO login(String email, String password) {
        return null;
    }


    private String generateCVU(){
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
                words = reader.lines().collect(Collectors.toList());
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
