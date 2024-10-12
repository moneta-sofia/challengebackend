package elxrojo.user_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepositoryCustom {
    boolean existsByCvu(String cvu);
    boolean existsByAlias(String alias);
    boolean existsByDni(Long dni);
    boolean existsByEmail(String email);
    boolean existsByPhone(Long phone);
    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);
}
