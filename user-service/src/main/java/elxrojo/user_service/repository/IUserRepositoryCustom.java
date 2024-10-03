package elxrojo.user_service.repository;

public interface IUserRepositoryCustom {
    boolean existsByCvu(String cvu);
    boolean existsByAlias(String alias);
    boolean existsByDni(Long dni);
    boolean existsByEmail(String email);
    boolean existsByPhone(Long phone);

}
