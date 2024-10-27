package elxrojo.card_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import elxrojo.card_service.exception.CustomException;
import elxrojo.card_service.model.Card;
import elxrojo.card_service.model.DTO.CardDTO;
import elxrojo.card_service.repository.ICardRepository;
import elxrojo.card_service.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    ICardRepository cardRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createCard(CardDTO card) {
        if (cardRepository.findCardByNumber(card.getNumber()).isPresent()) {
            throw new CustomException("Card already in use", HttpStatus.BAD_REQUEST);
        }
        cardRepository.save(mapper.convertValue(card, Card.class));
    }

    @Override
    public CardDTO findCardById(Long accountId, Long cardId) {
        Optional<Card> cardFound = cardRepository.findById(cardId);
        if (cardFound.isEmpty()) {
            throw new CustomException("Card not found!", HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(cardFound.get().getAccountId(), accountId)) {
            throw new CustomException("Without permission to view this card!", HttpStatus.UNAUTHORIZED);
        }
        return mapper.convertValue(cardFound, CardDTO.class);

    }

    @Override
    public Optional<CardDTO> findCardByNumber(String number) {
        Optional<Card> card = cardRepository.findCardByNumber(number);
        if (card.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(mapper.convertValue(card, CardDTO.class));
        }
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map(card -> mapper.convertValue(card, CardDTO.class))
                .toList();
    }

    @Override
    public List<CardDTO> getAllCardsByAccount(Long accountId) {
        List<Card> cards = cardRepository.findCardByAccountId(accountId);
        return cards.stream()
                .map(card -> mapper.convertValue(card, CardDTO.class))
                .toList();
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
