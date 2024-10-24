package elxrojo.card_service.repository;

import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;

public interface ICardRepositoryCustom {
    Card findCardByNumber(String number);
}
