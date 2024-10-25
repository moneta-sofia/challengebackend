package elxrojo.account_service.external.repository;

import elxrojo.account_service.model.DTO.CardDTO;
import elxrojo.account_service.external.feign.IFeignCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public class CardRepository {
    private final IFeignCardRepository cardRepository;

    public CardRepository(IFeignCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void createCard(@RequestBody CardDTO card) {
        cardRepository.createCard(card);
    }

    public CardDTO getCardById(@PathVariable Long accountId, @PathVariable Long cardId) {
        return cardRepository.getCardById(accountId, cardId);
    }

    public Optional<CardDTO> getCardByNumber(@PathVariable String cardNumber) {
        return cardRepository.getCardByNumber(cardNumber);
    }

    public List<CardDTO> getCardsByAccount(@PathVariable Long accountId) {
        return cardRepository.getCardsByAccount(accountId);
    }

    public void deleteCard(@PathVariable Long accountId, @PathVariable Long cardId) {
        cardRepository.deleteCard(accountId, cardId);
    }

}
