package elxrojo.card_service.service;

import elxrojo.card_service.model.DTO.CardDTO;

public interface ICardService {
    void createCard(CardDTO card);
    CardDTO findCardByNumber(String number);
}
