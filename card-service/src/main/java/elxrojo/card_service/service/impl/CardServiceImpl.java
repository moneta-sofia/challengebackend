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
    public CardDTO findCardByNumber(String number) {
        try {
            Card card = cardRepository.findCardByNumber(number);
            if (card == null) {
                throw new CustomException("Card not found", HttpStatus.NOT_FOUND);
            } else {
                return mapper.convertValue(card, CardDTO.class);
            }
        }catch (CustomException e) {
            throw e;
        }
    }


}
