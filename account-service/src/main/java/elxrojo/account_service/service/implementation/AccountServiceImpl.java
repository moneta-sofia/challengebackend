package elxrojo.account_service.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.account_service.exception.CustomException;
import elxrojo.account_service.external.repository.CardRepository;
import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.model.DTO.TransactionDTO;
import elxrojo.account_service.repository.IAccountMapper;
import elxrojo.account_service.repository.IAccountRepository;
import elxrojo.account_service.external.repository.TransactionRepository;
import elxrojo.account_service.service.IAccountService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private IAccountMapper accountMapper;

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public Long createAccount(String alias, String cvu, String userId, String name) {
        Account account = accountRepository.save(new Account(name, 0.0F, alias, cvu, userId));
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getId();

    }

    @Override
    public AccountDTO getAccountByUser(String userId) {
            Optional<Account> account = accountRepository.findByUserId(userId);
            if (account.isPresent()) {
                return mapper.convertValue(account.get(), AccountDTO.class);
            } else {
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
    }


    @Override
    public AccountDTO updateAccount(Long accountId, AccountDTO accountUpdated) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException("Account not found", HttpStatus.NOT_FOUND));
        if (accountUpdated.getId() != null && (!Objects.equals(account.getId(), accountUpdated.getId())) ){
            throw new CustomException("Cannot change the account id!", HttpStatus.BAD_REQUEST);
        }

        if (accountUpdated.getUserId() != null && (!Objects.equals(account.getUserId(), accountUpdated.getUserId()))) {
            throw new CustomException("Cannot change the user id!", HttpStatus.BAD_REQUEST);
        }

        accountMapper.updateAccount(accountUpdated, account);
        accountRepository.save(account);
        return mapper.convertValue(account, AccountDTO.class);
    }


    //    Transaction method

    @Override
    public List<TransactionDTO> getTransactionsById(String userID, Integer limit) {
        try {
            Optional<Account> accountFound = accountRepository.findByUserId(userID);
            if (accountFound.isEmpty()){
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
            return transactionRepository.getTransactionsByAccount(accountFound.get().getId(), limit);
        } catch (FeignException ex) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public TransactionDTO getTransactionById(String userId, Long transactionId){
        try {
            Optional<Account> accountfound = accountRepository.findByUserId(userId);
            if (accountfound.isEmpty()){
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
            return transactionRepository.getTransactionByAccount(accountfound.get().getId(), transactionId);
        } catch (FeignException.NotFound ex) {
            throw new CustomException("That activity doesn't exist!", HttpStatus.NOT_FOUND);
        } catch (FeignException.Unauthorized ex) {
            throw new CustomException("Without permission!", HttpStatus.UNAUTHORIZED);
        } catch (FeignException ex) {
            throw new CustomException("An unexpected error ocurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    //    Card methods

    @Override
    public void createAccountCard(CardDTO card, String userId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            card.setAccountId(account.get().getId());
            card.setName(account.get().getName());
            cardRepository.createCard(card);
        } catch (FeignException.BadRequest ex) {
            throw new CustomException("Card already in use", HttpStatus.NOT_FOUND);
        } catch (FeignException e) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CardDTO getCardById(String userId, Long cardId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            if (account.isEmpty()) {
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
            if (cardRepository.getCardsByAccount(account.get().getId()).isEmpty()) {
                return new CardDTO();
            } else {
                return cardRepository.getCardById(account.get().getId(), cardId);
            }
        } catch (FeignException.NotFound ex) {
            throw new CustomException("Card not found with that id", HttpStatus.NOT_FOUND);
        } catch (FeignException.Unauthorized ex) {
            throw new CustomException("Without permission to get this card!", HttpStatus.UNAUTHORIZED);
        } catch (FeignException ex) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CardDTO> getCardsByAccount(String userId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            return cardRepository.getCardsByAccount(account.get().getId());
        } catch (FeignException ex) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteCard(String userId, Long cardId) {
        try {
            Optional<Account> account = accountRepository.findByUserId(userId);
            if (account.isEmpty()) {
                throw new CustomException("Account not found", HttpStatus.NOT_FOUND);
            }
            cardRepository.deleteCard(account.get().getId(), cardId);
        } catch (FeignException.NotFound ex) {
            throw new CustomException("Card not found with that id", HttpStatus.NOT_FOUND);
        } catch (FeignException.Unauthorized ex) {
            throw new CustomException("Without permission to delete this card!", HttpStatus.UNAUTHORIZED);
        } catch (FeignException ex) {
            throw new CustomException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

