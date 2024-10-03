package elxrojo.user_service.repository;
import elxrojo.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>, IUserRepositoryCustom {

}

