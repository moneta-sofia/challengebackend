package elxrojo.card_service.controller;


import elxrojo.card_service.model.DTO.CardDTO;
import elxrojo.card_service.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    ICardService cardService;

    @PostMapping("/")
    public ResponseEntity<?> createCard(@RequestBody CardDTO card) {
        cardService.createCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<CardDTO>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    @GetMapping("/{cardId}/account/{accountId}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long accountId,@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.findCardById(accountId, cardId));
    }

    @GetMapping("/number/{cardNumber}")
    public ResponseEntity<Optional<CardDTO>> getCardByNumber(@PathVariable String cardNumber) {
        Optional<CardDTO> card = cardService.findCardByNumber(cardNumber);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CardDTO>> getCardsByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(cardService.getAllCardsByAccount(accountId));
    }

    @DeleteMapping("/{cardId}/account/{accountId}")
    public ResponseEntity<?> deleteCard(@PathVariable Long accountId,@PathVariable Long cardId){
        cardService.deleteCard(accountId,cardId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
