package elxrojo.account_service.service;

public interface IAccountService {
    Long createAccount(String alias, String cvu, Long userId);
    Float getBalance(Long userId);
}
