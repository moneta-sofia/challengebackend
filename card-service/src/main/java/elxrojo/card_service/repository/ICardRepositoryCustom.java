package elxrojo.card_service.repository;

import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;

import java.util.Optional;

public interface ICardRepositoryCustom {
    Optional<Card> findCardByNumber(String number);
}
