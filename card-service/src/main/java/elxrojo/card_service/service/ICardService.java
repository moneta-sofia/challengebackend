package elxrojo.card_service.service;

import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;

import java.util.List;
import java.util.Optional;

public interface ICardService {
    void createCard(CardDTO card);

    Optional<CardDTO> findCardByNumber(String number);

    List<CardDTO> getAllCards();

    List<CardDTO> getAllCardsByAccount(Long accountId);
}
