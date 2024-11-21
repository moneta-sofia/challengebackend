package elxrojo.account_service.external.feign;

import elxrojo.account_service.model.DTO.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "card-service", url = "http://card-service:8087")
public interface IFeignCardRepository {

    @PostMapping("/cards/")
    void createCard(@RequestBody CardDTO card);

    @GetMapping("cards/{cardId}/account/{accountId}")
    CardDTO getCardById(@PathVariable Long accountId,@PathVariable Long cardId);

    @GetMapping("/cards/number/{cardNumber}")
    Optional<CardDTO> getCardByNumber(@PathVariable String cardNumber);

    @GetMapping("/cards/account/{accountId}")
    List<CardDTO> getCardsByAccount(@PathVariable Long accountId);

    @DeleteMapping("/cards/{cardId}/account/{accountId}")
    void deleteCard(@PathVariable Long accountId,@PathVariable Long cardId);

}
