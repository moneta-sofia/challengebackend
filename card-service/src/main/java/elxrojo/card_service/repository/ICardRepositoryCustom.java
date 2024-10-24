package elxrojo.card_service.repository;

import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;

import java.util.List;
import java.util.Optional;

public interface ICardRepositoryCustom {
    Optional<Card> findCardByNumber(String number);
    List<Card> findCardByAccountId(Long accountId);
}
