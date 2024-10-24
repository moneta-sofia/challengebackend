package elxrojo.card_service.controller;


import elxrojo.card_service.model.DTO.CardDTO;
import elxrojo.card_service.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    ICardService cardService;

    @PostMapping("/")
        public ResponseEntity<Void> createCard(@RequestBody CardDTO card) {
            cardService.createCard(card);
        return ResponseEntity.ok().build();
    }

}
