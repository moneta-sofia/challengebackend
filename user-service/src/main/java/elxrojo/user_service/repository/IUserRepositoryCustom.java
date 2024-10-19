package elxrojo.user_service.repository;

import elxrojo.user_service.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepositoryCustom {
    boolean existsByDni(Long dni);
    boolean existsByEmail(String email);
    boolean existsByPhone(Long phone);
    User findByEmail(String email);
    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);
}
