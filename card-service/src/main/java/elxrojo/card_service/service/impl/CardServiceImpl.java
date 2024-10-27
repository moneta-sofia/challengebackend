package elxrojo.card_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.card_service.exception.CustomException;
import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;
import elxrojo.card_service.repository.ICardRepository;
import elxrojo.card_service.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public CardDTO findCardById(Long accountId, Long cardId) {
        try {
            Optional<Card> cardFound = cardRepository.findById(cardId);

            if (cardFound.isEmpty()) {
                throw new CustomException("Card not found!", HttpStatus.NOT_FOUND);
            }

            if (!Objects.equals(cardFound.get().getAccountId(), accountId)) {
                throw new CustomException("Without permission to view this card!", HttpStatus.UNAUTHORIZED);
            }
            return mapper.convertValue(cardFound, CardDTO.class);
        } catch (CustomException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new CustomException("Database error occurred while trying to find the card! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException("An unexpected error occurred!", HttpStatus.INTERNAL_SERVER_ERROR);
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
                    .map(card -> mapper.convertValue(card, CardDTO.class))
                    .toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot find the cards now!");
        }
    }

    @Override
    public void deleteCard(Long accountId, Long cardId) {
        Optional<Card> cardFound = cardRepository.findById(cardId);

        if (cardFound.isEmpty()) {
            throw new CustomException("Card not found!", HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(cardFound.get().getAccountId(), accountId)) {
            throw new CustomException("Without permission to delete this card!", HttpStatus.UNAUTHORIZED);
        }
        cardRepository.deleteById(cardId);
    }

}
