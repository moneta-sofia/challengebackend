package elxrojo.user_service.repository;
import elxrojo.user_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Usuario, Long> {
//    boolean existsByCvu(int cvu);
//    boolean existsByAlias(int alias);
}
