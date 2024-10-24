package elxrojo.card_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.card_service.exception.CustomException;
import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;
import elxrojo.card_service.repository.ICardRepository;
import elxrojo.card_service.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    ICardRepository cardRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createCard(CardDTO card) {
        try {
            cardRepository.save(mapper.convertValue(card, Card.class));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create card: " + e.getMessage());
        }
    }

    @Override
    public Optional<CardDTO> findCardByNumber(String number) {
        try {
            Optional<Card> card = cardRepository.findCardByNumber(number);
            if (card.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(mapper.convertValue(card, CardDTO.class));
            }
        } catch (CustomException e) {
            throw e;
        }
    }

    @Override
    public List<CardDTO> getAllCards() {
        try {
            List<Card> cards = cardRepository.findAll();
            return cards.stream()
                    .map(card -> mapper.convertValue(card, CardDTO.class))
                    .toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot get all the cards :(");
        }
    }

    @Override
    public List<CardDTO> getAllCardsByAccount(Long accountId) {
        try {
            List<Card> cards = cardRepository.findCardByAccountId(accountId);
            return cards.stream()
                    .map( card -> mapper.convertValue(card, CardDTO.class))
                    .toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot find the cards now!");
        }
    }


}
