package elxrojo.user_service.service.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.user_service.model.DTO.UserDTO;
import elxrojo.user_service.repository.IUserRepository;
import elxrojo.user_service.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private IUserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public UserDTO signup(UserDTO user) throws IOException {
        log.info("CVU: " + generateCVU());
        log.info("Alias: " + generateAlias());

//        userRepository.existsByCvu(Integer.parseInt(cvu.toString()));
        return null;
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
            System.out.println(e.getMessage());
            return null;
        }
    }




}
